#include "JavascriptObject.hpp"
#include "JavascriptType.hpp"

class JavascriptObjectImpl : public JavascriptObject {
public:
    JavascriptObjectImpl(const JavascriptObjectImpl& other);

    JavascriptObjectImpl(std::nullptr_t);
    JavascriptObjectImpl(bool value);
    JavascriptObjectImpl(double value);
    JavascriptObjectImpl(int32_t value);
    JavascriptObjectImpl(const std::string& value);
    JavascriptObjectImpl(const std::shared_ptr<JavascriptArray>& value);
    JavascriptObjectImpl(const std::shared_ptr<JavascriptMap>& value);

    ~JavascriptObjectImpl();

    JavascriptType getType() override;

    bool isNull() override;

    bool asBoolean() override;

    double asDouble() override;

    int32_t asInt() override;

    std::string asString() override;

    std::shared_ptr<JavascriptArray> asArray() override;

    std::shared_ptr<JavascriptMap> asMap() override;

private:
    union U {
        U() {}
        ~U() {}
        bool mBool;
        double mDouble;
        std::string mString;
        std::shared_ptr<JavascriptArray> mArray;
        std::shared_ptr<JavascriptMap> mMap;
    } mUnion;
    JavascriptType mType;
};
