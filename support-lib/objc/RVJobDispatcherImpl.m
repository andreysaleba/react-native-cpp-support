#import "RVJobDispatcherImpl.h"
#import "RVJobQueue.h"
#import "RVJob.h"

@interface RVJobDispatcherImpl ()

@property (atomic) BOOL destroyed;
@property (nonatomic, readonly) RVJobQueue *queue;
@property (nonatomic, readonly) NSThread *thread;

- (void)loop;

@end

@implementation RVJobDispatcherImpl

- (id)initWithQueue:(RVJobQueue *)queue {
  if (self = [super init]) {
    _queue = queue;
    _destroyed = NO;
    _thread = [[NSThread alloc] initWithTarget:self selector:@selector(loop) object:nil];
  }
  return self;
}

- (void)start {
  @synchronized(self) {
    if (!self.destroyed) {
      [self.thread start];
    }
  }
}

- (void)quit {
  @synchronized(self) {
    self.destroyed = YES;
    [self.queue interruptPoll];
  }
}

- (void)loop {
  while (!self.destroyed) {
    RVJob *job = nil;
    while ((job = [self.queue poll]) != nil) {
      [job run];
    }
  }
}

@end
