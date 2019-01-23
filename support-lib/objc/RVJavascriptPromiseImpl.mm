#import "RVJavascriptPromiseImpl.h"
#import "RVReactDjinni.h"

@interface RVJavascriptPromiseImpl ()

@property (readonly) RCTPromiseResolveBlock resolver;
@property (readonly) RCTPromiseRejectBlock rejecter;

@end

@implementation RVJavascriptPromiseImpl

- (id<RVJavascriptPromise>)initWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
  if (self = [super init]) {
    _resolver = resolve;
    _rejecter = reject;
  }
  return self;
}

- (void)reject:(nonnull NSString *)code message:(nonnull NSString *)message {
  self.rejecter(code, message, nil);
}

- (void)resolveArray:(nullable id<RVJavascriptArray>)value {
  self.resolver([RVReactDjinni unwrapArray:value]);
}

- (void)resolveBoolean:(BOOL)value {
  self.resolver([NSNumber numberWithBool:value]);
}

- (void)resolveDouble:(double)value {
  self.resolver([NSNumber numberWithDouble:value]);
}

- (void)resolveInt:(int32_t)value {
  self.resolver([NSNumber numberWithInt:value]);
}

- (void)resolveMap:(nullable id<RVJavascriptMap>)value {
  self.resolver([RVReactDjinni unwrapMap:value]);
}

- (void)resolveNull {
  self.resolver([NSNull null]);
}

- (void)resolveString:(nonnull NSString *)value {
  self.resolver(value);
}

- (void)resolveObject:(RVJavascriptObject *)value {
  self.resolver([RVReactDjinni unwrapObject:value]);
}

@end
