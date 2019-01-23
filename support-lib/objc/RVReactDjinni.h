#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

#import "RVJavascriptType.h"

@protocol RVReactBridge;
@protocol RVJavascriptArray;
@protocol RVJavascriptMap;
@protocol RVJavascriptMapKeyIterator;
@protocol RVJavascriptCallback;
@protocol RVJavascriptPromise;
@class RVJavascriptObject;

@interface RVReactDjinni : NSObject

+ (id<RVReactBridge>)createReactBridge:(RCTBridge *)bridge;

+ (id<RVJavascriptArray>)createArray;
+ (id<RVJavascriptArray>)createArrayFrom:(id<RVJavascriptArray>)array;

+ (id<RVJavascriptMap>)createMap;
+ (id<RVJavascriptMap>)createMapFrom:(id<RVJavascriptMap>)map;

+ (id<RVJavascriptArray>)wrapArray:(NSArray *)array;
+ (id<RVJavascriptMap>)wrapMap:(NSDictionary *)map;
+ (RVJavascriptObject *)wrapObject:(nonnull id)object;
+ (id<RVJavascriptMapKeyIterator>)wrapMapKeyIterator:(NSEnumerator *)keyEnumerator;
+ (id<RVJavascriptCallback>)wrapCallback:(RCTResponseSenderBlock)callback;
+ (id<RVJavascriptPromise>)wrapPromiseWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject;

+ (NSArray *)unwrapArray:(id<RVJavascriptArray>)array;
+ (NSDictionary *)unwrapMap:(id<RVJavascriptMap>)map;
+ (id)unwrapObject:(RVJavascriptObject *)object;

+ (RVJavascriptType)getType:(nonnull id)object;

@end
