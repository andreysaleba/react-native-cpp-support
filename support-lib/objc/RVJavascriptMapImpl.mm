#import "RVJavascriptMapImpl.h"
#import "RVReactDjinni.h"

@implementation RVJavascriptMapImpl

- (instancetype)init {
  if (self = [super init]) {
    _dictionary = [NSMutableDictionary dictionary];
  }
  return self;
}

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
  if (self = [self init]) {
    [self.dictionary addEntriesFromDictionary:dictionary];
  }
  return self;
}

- (nullable id<RVJavascriptArray>)getArray:(nonnull NSString *)name {
  return [RVReactDjinni wrapArray:self.dictionary[name]];
}

- (BOOL)getBoolean:(nonnull NSString *)name {
  return [((NSNumber *)self.dictionary[name]) boolValue];
}

- (double)getDouble:(nonnull NSString *)name {
  return [((NSNumber *)self.dictionary[name]) doubleValue];
}

- (int32_t)getInt:(nonnull NSString *)name {
  return [((NSNumber *)self.dictionary[name]) intValue];
}

- (nullable id<RVJavascriptMap>)getMap:(nonnull NSString *)name {
  return [RVReactDjinni wrapMap:self.dictionary[name]];
}

- (nonnull NSString *)getString:(nonnull NSString *)name {
  return self.dictionary[name];
}

- (RVJavascriptObject *)getObject:(NSString *)name {
  return [RVReactDjinni wrapObject:self.dictionary[name]];
}

- (RVJavascriptType)getType:(nonnull NSString *)name {
  return [RVReactDjinni getType:self.dictionary[name]];
}

- (BOOL)hasKey:(nonnull NSString *)name {
  return [self.dictionary objectForKey:name] != nil;
}

- (BOOL)isNull:(nonnull NSString *)name {
  return [self.dictionary[name] isKindOfClass:[NSNull class]];
}

- (nullable id<RVJavascriptMapKeyIterator>)keySetIterator {
  return [RVReactDjinni wrapMapKeyIterator:[self.dictionary keyEnumerator]];
}

- (void)merge:(nullable id<RVJavascriptMap>)source {
  [self.dictionary addEntriesFromDictionary:[RVReactDjinni unwrapMap:source]];
}

- (void)putArray:(nonnull NSString *)key value:(nullable id<RVJavascriptArray>)value {
  self.dictionary[key] = [RVReactDjinni unwrapArray:value];
}

- (void)putBoolean:(nonnull NSString *)key value:(BOOL)value {
  self.dictionary[key] = [NSNumber numberWithBool:value];
}

- (void)putDouble:(nonnull NSString *)key value:(double)value {
  self.dictionary[key] = [NSNumber numberWithDouble:value];
}

- (void)putInt:(nonnull NSString *)key value:(int32_t)value {
  self.dictionary[key] = [NSNumber numberWithInt:value];
}

- (void)putMap:(nonnull NSString *)key value:(nullable id<RVJavascriptMap>)value {
  self.dictionary[key] = [RVReactDjinni unwrapMap:value];
}

- (void)putNull:(nonnull NSString *)key {
  self.dictionary[key] = [NSNull null];
}

- (void)putString:(nonnull NSString *)key value:(nonnull NSString *)value {
  self.dictionary[key] = value;
}

- (void)putObject:(NSString *)key value:(RVJavascriptObject *)value {
  self.dictionary[key] = [RVReactDjinni unwrapObject:value];
}

@end
