#import "RVJavascriptArrayImpl.h"
#import "RVReactDjinni.h"

@implementation RVJavascriptArrayImpl

- (instancetype)init {
  if (self = [super init]) {
    _array = [NSMutableArray array];
  }
  return self;
}

- (instancetype)initWithArray:(NSArray *)array {
  if (self = [self init]) {
    [self.array addObjectsFromArray:array];
  }
  return self;
}

- (void)append:(nullable id<RVJavascriptArray>)source {
  [self.array addObjectsFromArray:[RVReactDjinni unwrapArray:source]];
}

- (nullable id<RVJavascriptArray>)getArray:(int32_t)index {
  return [RVReactDjinni wrapArray:self.array[index]];
}

- (BOOL)getBoolean:(int32_t)index {
  return [((NSNumber *)self.array[index]) boolValue];
}

- (double)getDouble:(int32_t)index {
  return [((NSNumber *)self.array[index]) doubleValue];
}

- (int32_t)getInt:(int32_t)index {
  return [((NSNumber *)self.array[index]) intValue];
}

- (nullable id<RVJavascriptMap>)getMap:(int32_t)index {
  return [RVReactDjinni wrapMap:self.array[index]];
}

- (nonnull NSString *)getString:(int32_t)index {
  return self.array[index];
}

- (RVJavascriptObject *)getObject:(int32_t)index {
  return [RVReactDjinni wrapObject:self.array[index]];
}

- (RVJavascriptType)getType:(int32_t)index {
  return [RVReactDjinni getType:self.array[index]];
}

- (BOOL)isNull:(int32_t)index {
  return [self.array[index] isKindOfClass:[NSNull class]];
}

- (void)pushArray:(nullable id<RVJavascriptArray>)array {
  [self.array addObject:[NSArray arrayWithArray:[RVReactDjinni unwrapArray:array]]];
}

- (void)pushBoolean:(BOOL)value {
  [self.array addObject:[NSNumber numberWithBool:value]];
}

- (void)pushDouble:(double)value {
  [self.array addObject:[NSNumber numberWithDouble:value]];
}

- (void)pushInt:(int32_t)value {
  [self.array addObject:[NSNumber numberWithInt:value]];
}

- (void)pushMap:(nullable id<RVJavascriptMap>)map {
  [self.array addObject:[NSDictionary dictionaryWithDictionary:[RVReactDjinni unwrapMap:map]]];
}

- (void)pushNull {
  [self.array addObject:[NSNull null]];
}

- (void)pushString:(nonnull NSString *)value {
  [self.array addObject:value];
}

- (void)pushObject:(RVJavascriptObject *)object {
  [self.array addObject:[RVReactDjinni unwrapObject:object]];
}

- (int32_t)size {
  return (int32_t)self.array.count;
}

@end
