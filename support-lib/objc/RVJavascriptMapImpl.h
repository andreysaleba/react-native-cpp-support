#import <Foundation/Foundation.h>
#import "RVJavascriptMap.h"

@interface RVJavascriptMapImpl : NSObject<RVJavascriptMap>

@property (readonly) NSMutableDictionary * _Nonnull dictionary;

- (instancetype)init;

- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

@end
