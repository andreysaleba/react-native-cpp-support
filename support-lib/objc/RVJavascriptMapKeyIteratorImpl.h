#import <Foundation/Foundation.h>
#import "RVJavascriptMapKeyIterator.h"

@interface RVJavascriptMapKeyIteratorImpl : NSObject<RVJavascriptMapKeyIterator>

- (instancetype)initWithEnumerator:(NSEnumerator *)keyEnumerator;

- (BOOL)hasNextKey;

- (nonnull NSString *)nextKey;

@end
