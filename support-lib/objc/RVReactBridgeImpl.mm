#import "RVReactBridgeImpl.h"
#import "RVReactDjinni.h"
#import "RVJobDispatcherImpl.h"

#import <React/RCTEventDispatcher.h>

@interface RVReactBridgeImpl ()

@property (readonly) RCTBridge *bridge;

@end

@implementation RVReactBridgeImpl

- (instancetype)initWithBridge:(RCTBridge *)bridge {
  if (self = [super init]) {
    _bridge = bridge;
  }
  return self;
}

- (nullable id<RVJavascriptArray>)copyArray:(nullable id<RVJavascriptArray>)array {
  return [RVReactDjinni createArrayFrom:array];
}

- (nullable id<RVJavascriptMap>)copyMap:(nullable id<RVJavascriptMap>)map {
  return [RVReactDjinni createMapFrom:map];
}

- (nullable id<RVJavascriptArray>)createArray {
  return [RVReactDjinni createArray];
}

- (nullable id<RVJavascriptMap>)createMap {
  return [RVReactDjinni createMap];
}

- (void)emitEventWithArray:(nonnull NSString *)name params:(nullable id<RVJavascriptArray>)params {
  [self.bridge.eventDispatcher sendAppEventWithName:name body:[RVReactDjinni unwrapArray:params]];
}

- (void)emitEventWithMap:(nonnull NSString *)name params:(nullable id<RVJavascriptMap>)params {
  [self.bridge.eventDispatcher sendAppEventWithName:name body:[RVReactDjinni unwrapMap:params]];
}

- (id<RVJobDispatcher>)createJobDispatcher:(RVJobQueue *)queue {
  return [[RVJobDispatcherImpl alloc] initWithQueue:queue];
}

@end
