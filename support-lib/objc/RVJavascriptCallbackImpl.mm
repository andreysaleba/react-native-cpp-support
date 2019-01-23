#import "RVJavascriptCallbackImpl.h"
#import "RVReactDjinni.h"
#import "RVJavascriptObject.h"

@interface RVJavascriptCallbackImpl ()

@property (readonly) RCTResponseSenderBlock callback;

@end

@implementation RVJavascriptCallbackImpl

- (instancetype)initWithCallback:(RCTResponseSenderBlock)callback {
  if (self = [super init]) {
    _callback = callback;
  }
  return self;
}

- (void)invoke:(nonnull NSArray<RVJavascriptObject *> *)args {
  NSMutableArray *objcArgs = [[NSMutableArray alloc] initWithCapacity:args.count];
  for (RVJavascriptObject *o in args) {
    switch ([o getType]) {
      case RVJavascriptTypeARRAY:
        [objcArgs addObject:[RVReactDjinni unwrapArray:[o asArray]]];
        break;
      case RVJavascriptTypeBOOLEAN:
        [objcArgs addObject:[NSNumber numberWithBool:[o asBoolean]]];
        break;
      case RVJavascriptTypeMAP:
        [objcArgs addObject:[RVReactDjinni unwrapMap:[o asMap]]];
        break;
      case RVJavascriptTypeNUMBER:
        [objcArgs addObject:[NSNumber numberWithDouble:[o asDouble]]];
        break;
      case RVJavascriptTypeSTRING:
        [objcArgs addObject:[o asString]];
        break;
      default:
      case RVJavascriptTypeNIL:
        [objcArgs addObject:[NSNull null]];
        break;
    }
  }
  self.callback(objcArgs);
}

- (void)invokeSingleArg:(nullable RVJavascriptObject *)arg {
  [self invoke:@[arg]];
}

@end
