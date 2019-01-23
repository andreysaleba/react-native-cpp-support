#import "RVReactDjinni.h"
#import "RVReactBridgeImpl.h"
#import "RVJavascriptArrayImpl.h"
#import "RVJavascriptMapImpl.h"
#import "RVJavascriptMapKeyIteratorImpl.h"
#import "RVJavascriptCallbackImpl.h"
#import "RVJavascriptPromiseImpl.h"
#import "RVJavascriptObject.h"

@implementation RVReactDjinni

+ (id<RVReactBridge>)createReactBridge:(RCTBridge *)bridge {
  return [[RVReactBridgeImpl alloc] initWithBridge:bridge];
}

+ (id<RVJavascriptArray>)createArray {
  return [[RVJavascriptArrayImpl alloc] init];
}

+ (id<RVJavascriptArray>)createArrayFrom:(id<RVJavascriptArray>)array {
  return [[RVJavascriptArrayImpl alloc] initWithArray:[self unwrapArray:array]];
}

+ (id<RVJavascriptMap>)createMap {
  return [[RVJavascriptMapImpl alloc] init];
}

+ (id<RVJavascriptMap>)createMapFrom:(id<RVJavascriptMap>)map {
  return [[RVJavascriptMapImpl alloc] initWithDictionary:[self unwrapMap:map]];
}

+ (id<RVJavascriptArray>)wrapArray:(NSArray *)array {
  return [[RVJavascriptArrayImpl alloc] initWithArray:array];
}

+ (id<RVJavascriptMap>)wrapMap:(NSDictionary *)map {
  return [[RVJavascriptMapImpl alloc] initWithDictionary:map];
}

+ (RVJavascriptObject *)wrapObject:(id)object {
  RVJavascriptType type = [self getType:object];
  switch (type) {
    case RVJavascriptTypeMAP:
      return [RVJavascriptObject fromMap:[RVReactDjinni wrapMap:object]];
    case RVJavascriptTypeARRAY:
      return [RVJavascriptObject fromArray:[RVReactDjinni wrapArray:object]];
    case RVJavascriptTypeBOOLEAN:
      return [RVJavascriptObject fromBoolean:[object boolValue]];
    case RVJavascriptTypeNUMBER:
      return [RVJavascriptObject fromDouble:[object doubleValue]];
    case RVJavascriptTypeSTRING:
      return [RVJavascriptObject fromString:object];
    case RVJavascriptTypeNIL:
    default:
      return [RVJavascriptObject fromNull];
  }
}

+ (id<RVJavascriptMapKeyIterator>)wrapMapKeyIterator:(NSEnumerator *)keyEnumerator {
  return [[RVJavascriptMapKeyIteratorImpl alloc] initWithEnumerator:keyEnumerator];
}

+ (id<RVJavascriptCallback>)wrapCallback:(RCTResponseSenderBlock)callback {
  return [[RVJavascriptCallbackImpl alloc] initWithCallback:callback];
}

+ (id<RVJavascriptPromise>)wrapPromiseWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
  return [[RVJavascriptPromiseImpl alloc] initWithResolver:resolve rejecter:reject];
}

+ (NSArray *)unwrapArray:(id<RVJavascriptArray>)array {
  return ((RVJavascriptArrayImpl *)array).array;
}

+ (NSDictionary *)unwrapMap:(id<RVJavascriptMap>)map {
  return ((RVJavascriptMapImpl *)map).dictionary;
}

+ (id)unwrapObject:(RVJavascriptObject *)object {
  switch ([object getType]) {
    case RVJavascriptTypeMAP:
      return [RVReactDjinni unwrapMap:[object asMap]];
    case RVJavascriptTypeARRAY:
      return [RVReactDjinni unwrapArray:[object asArray]];
    case RVJavascriptTypeBOOLEAN:
      return [NSNumber numberWithBool:[object asBoolean]];
    case RVJavascriptTypeNUMBER:
      return [NSNumber numberWithDouble:[object asDouble]];
    case RVJavascriptTypeSTRING:
      return [object asString];
    case RVJavascriptTypeNIL:
    default:
      return [NSNull null];
  }
}

+ (RVJavascriptType)getType:(id)object {
  if ([object isKindOfClass:[NSNull class]]) {
    return RVJavascriptTypeNIL;
  } if ([object isKindOfClass:[NSNumber class]]) {
    return RVJavascriptTypeNUMBER;
  } else if ([object isKindOfClass:[NSString class]]) {
    return RVJavascriptTypeSTRING;
  } else if ([object isKindOfClass:[NSArray class]]) {
    return RVJavascriptTypeARRAY;
  } else if ([object isKindOfClass:[NSDictionary class]]) {
    return RVJavascriptTypeMAP;
  }
  return RVJavascriptTypeNIL;
}

@end
