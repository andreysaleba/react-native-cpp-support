#import <Foundation/Foundation.h>
#import "RVReactBridge.h"

#import <React/RCTBridge.h>

@interface RVReactBridgeImpl : NSObject<RVReactBridge>

- (instancetype)initWithBridge:(RCTBridge *)bridge;

@end
