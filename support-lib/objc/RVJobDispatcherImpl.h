#import <Foundation/Foundation.h>
#import "RVJobDispatcher.h"

@class RVJobQueue;

@interface RVJobDispatcherImpl : NSObject<RVJobDispatcher>

- (id)initWithQueue:(RVJobQueue *)queue;

@end
