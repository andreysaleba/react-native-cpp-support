#import <Foundation/Foundation.h>
#import "RVJavascriptCallback.h"
#import <React/RCTBridgeModule.h>

@interface RVJavascriptCallbackImpl : NSObject<RVJavascriptCallback>

- (instancetype _Nonnull)initWithCallback:(nonnull RCTResponseSenderBlock)callback;

@end
