#import <Foundation/Foundation.h>
#import "RVJavascriptPromise.h"
#import <React/RCTBridgeModule.h>

@interface RVJavascriptPromiseImpl : NSObject<RVJavascriptPromise>

- (id<RVJavascriptPromise>)initWithResolver:(nonnull RCTPromiseResolveBlock)resolve rejecter:(nonnull RCTPromiseRejectBlock)reject;

@end
