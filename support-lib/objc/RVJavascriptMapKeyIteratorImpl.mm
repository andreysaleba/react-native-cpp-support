#import "RVJavascriptMapKeyIteratorImpl.h"

@interface RVJavascriptMapKeyIteratorImpl ()

@property (readonly) NSEnumerator *keyEnumerator;
@property NSString *nextKey;

@end

@implementation RVJavascriptMapKeyIteratorImpl

- (instancetype)initWithEnumerator:(NSEnumerator *)keyEnumerator {
  if (self = [super init]) {
    _keyEnumerator = keyEnumerator;
    _nextKey = [self.keyEnumerator nextObject];
  }
  return self;
}

- (BOOL)hasNextKey {
  return self.nextKey != nil;
}

- (nonnull NSString *)nextKey {
  NSString *ret = self.nextKey;
  self.nextKey = [self.keyEnumerator nextObject];
  return ret;
}

@end
