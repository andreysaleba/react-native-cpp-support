// AUTOGENERATED FILE - DO NOT MODIFY!
// This file generated by Djinni from react.djinni

#import "RVJob+Private.h"
#import "RVJob.h"
#import "DJICppWrapperCache+Private.h"
#import "DJIError.h"
#include <exception>
#include <stdexcept>
#include <utility>

static_assert(__has_feature(objc_arc), "Djinni requires ARC to be enabled for this file");

@interface RVJob ()

- (id)initWithCpp:(const std::shared_ptr<::Job>&)cppRef;

@end

@implementation RVJob {
    ::djinni::CppProxyCache::Handle<std::shared_ptr<::Job>> _cppRefHandle;
}

- (id)initWithCpp:(const std::shared_ptr<::Job>&)cppRef
{
    if (self = [super init]) {
        _cppRefHandle.assign(cppRef);
    }
    return self;
}

- (void)run {
    try {
        _cppRefHandle.get()->run();
    } DJINNI_TRANSLATE_EXCEPTIONS()
}

namespace djinni_generated {

auto Job::toCpp(ObjcType objc) -> CppType
{
    if (!objc) {
        return nullptr;
    }
    return objc->_cppRefHandle.get();
}

auto Job::fromCppOpt(const CppOptType& cpp) -> ObjcType
{
    if (!cpp) {
        return nil;
    }
    return ::djinni::get_cpp_proxy<RVJob>(cpp);
}

}  // namespace djinni_generated

@end