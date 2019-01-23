#include "JavascriptObjectImpl.hpp"

std::shared_ptr<JavascriptObject> JavascriptObject::fromNull() {
    return std::make_shared<JavascriptObjectImpl>(nullptr);
}

std::shared_ptr<JavascriptObject> JavascriptObject::fromBoolean(bool value) {
    return std::make_shared<JavascriptObjectImpl>(value);
}

std::shared_ptr<JavascriptObject> JavascriptObject::fromDouble(double value) {
    return std::make_shared<JavascriptObjectImpl>(value);
}

std::shared_ptr<JavascriptObject> JavascriptObject::fromInt(int32_t value) {
    return std::make_shared<JavascriptObjectImpl>(value);
}

std::shared_ptr<JavascriptObject> JavascriptObject::fromString(const std::string &value) {
    return std::make_shared<JavascriptObjectImpl>(value);
}

std::shared_ptr<JavascriptObject> JavascriptObject::fromArray(const std::shared_ptr<JavascriptArray> &value) {
    return std::make_shared<JavascriptObjectImpl>(value);
}

std::shared_ptr<JavascriptObject> JavascriptObject::fromMap(const std::shared_ptr<JavascriptMap> &value) {
    return std::make_shared<JavascriptObjectImpl>(value);
}

JavascriptObjectImpl::JavascriptObjectImpl(const JavascriptObjectImpl &other) : mType(other.mType) {
    switch (mType) {
        case JavascriptType::ARRAY:
            new (&mUnion.mArray) std::shared_ptr<JavascriptArray>(other.mUnion.mArray);
            break;
        case JavascriptType::MAP:
            new (&mUnion.mMap) std::shared_ptr<JavascriptMap>(other.mUnion.mMap);
            break;
        case JavascriptType::BOOLEAN:
            mUnion.mBool = other.mUnion.mBool;
            break;
        case JavascriptType::NUMBER:
            mUnion.mDouble = other.mUnion.mDouble;
            break;
        case JavascriptType::STRING:
            new (&mUnion.mString) std::string(other.mUnion.mString);
            break;
        case JavascriptType::NIL:
        default:
            break;
    }
}

JavascriptObjectImpl::JavascriptObjectImpl(std::nullptr_t) : mType(JavascriptType::NIL) {
}

JavascriptObjectImpl::JavascriptObjectImpl(bool value) : mType(JavascriptType::BOOLEAN) {
    mUnion.mBool = value;
}

JavascriptObjectImpl::JavascriptObjectImpl(double value) : mType(JavascriptType::NUMBER) {
    mUnion.mDouble = value;
}

JavascriptObjectImpl::JavascriptObjectImpl(int32_t value) : mType(JavascriptType::NUMBER) {
    mUnion.mDouble = value;
}

JavascriptObjectImpl::JavascriptObjectImpl(const std::string &value) : mType(JavascriptType::STRING) {
    new (&mUnion.mString) std::string(value);
}

JavascriptObjectImpl::JavascriptObjectImpl(const std::shared_ptr<JavascriptArray> &value) : mType(JavascriptType::ARRAY) {
    new (&mUnion.mArray) std::shared_ptr<JavascriptArray>(value);
}

JavascriptObjectImpl::JavascriptObjectImpl(const std::shared_ptr<JavascriptMap> &value) : mType(JavascriptType::MAP) {
    new (&mUnion.mMap) std::shared_ptr<JavascriptMap>(value);
}

JavascriptObjectImpl::~JavascriptObjectImpl() {
    switch (mType) {
        case JavascriptType::ARRAY:
            mUnion.mArray.~shared_ptr<JavascriptArray>();
            break;
        case JavascriptType::MAP:
            mUnion.mMap.~shared_ptr<JavascriptMap>();
            break;
        case JavascriptType::STRING:
            mUnion.mString.~basic_string();
            break;
        default:
            break;
    }
}

JavascriptType JavascriptObjectImpl::getType() {
    return mType;
}

bool JavascriptObjectImpl::isNull() {
    return mType == JavascriptType::NIL;
}

bool JavascriptObjectImpl::asBoolean() {
    if (mType == JavascriptType::BOOLEAN) {
        return mUnion.mBool;
    }
    return false;
}

double JavascriptObjectImpl::asDouble() {
    if (mType == JavascriptType::NUMBER) {
        return mUnion.mDouble;
    }
    return 0;
}

int32_t JavascriptObjectImpl::asInt() {
    if (mType == JavascriptType::NUMBER) {
        return (int32_t) mUnion.mDouble;
    }
    return 0;
}

std::string JavascriptObjectImpl::asString() {
    if (mType == JavascriptType::STRING) {
        return mUnion.mString;
    }
    return nullptr;
}

std::shared_ptr<JavascriptArray> JavascriptObjectImpl::asArray() {
    if (mType == JavascriptType::ARRAY) {
        return mUnion.mArray;
    }
    return nullptr;
}

std::shared_ptr<JavascriptMap> JavascriptObjectImpl::asMap() {
    if (mType == JavascriptType::MAP) {
        return mUnion.mMap;
    }
    return nullptr;
}
