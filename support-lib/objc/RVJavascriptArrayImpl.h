#import <Foundation/Foundation.h>

#import "RVJavascriptArray.h"

@interface RVJavascriptArrayImpl : NSObject<RVJavascriptArray>

@property (readonly) NSMutableArray * _Nonnull array;

- (instancetype _Nonnull)init;

- (instancetype _Nonnull)initWithArray:(NSArray *_Nonnull)array;

@end
