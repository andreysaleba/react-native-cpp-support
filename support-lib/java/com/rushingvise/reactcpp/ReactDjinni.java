package com.rushingvise.reactcpp;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public abstract class ReactDjinni {
    private ReactDjinni() {
    }

    public static ReactBridge createReactBridge(ReactApplicationContext reactContext) {
        return new ReactBridgeImpl(reactContext);
    }

    public static JavascriptMap createMap() {
        return new JavascriptMapImpl();
    }

    public static JavascriptArray createArray() {
        return new JavascriptArrayImpl();
    }

    public static JavascriptMap createMap(JavascriptMap map) {
        JavascriptMap ret = createMap();
        ret.merge(map);
        return ret;
    }

    public static JavascriptArray createArray(JavascriptArray array) {
        JavascriptArray ret = createArray();
        ret.append(array);
        return ret;
    }

    public static JavascriptMap wrap(ReadableMap map) {
        return new JavascriptMapImpl(map);
    }

    public static JavascriptArray wrap(ReadableArray array) {
        return new JavascriptArrayImpl(array);
    }

    public static JavascriptPromise wrap(Promise promise) {
        return new JavascriptPromiseImpl(promise);
    }

    public static JavascriptCallback wrap(Callback callback) {
        return new JavascriptCallbackImpl(callback);
    }

    public static WritableMap unwrap(JavascriptMap map) {
        return ((JavascriptMapImpl) map).getWritableMap();
    }

    public static WritableArray unwrap(JavascriptArray array) {
        return ((JavascriptArrayImpl) array).getWritableArray();
    }

    public static JavascriptObject wrap(Dynamic dynamic) {
        switch (dynamic.getType()) {
            case Array:
                return JavascriptObject.fromArray(ReactDjinni.wrap(dynamic.asArray()));
            case Boolean:
                return JavascriptObject.fromBoolean(dynamic.asBoolean());
            case Map:
                return JavascriptObject.fromMap(ReactDjinni.wrap(dynamic.asMap()));
            case Number:
                return JavascriptObject.fromDouble(dynamic.asDouble());
            case String:
                return JavascriptObject.fromString(dynamic.asString());
            case Null:
            default:
                return JavascriptObject.fromNull();
        }
    }

    public static Object unwrap(JavascriptObject value) {
        switch (value.getType()) {
            case ARRAY:
                return ReactDjinni.unwrap(value.asArray());
            case BOOLEAN:
                return value.asBoolean();
            case MAP:
                return ReactDjinni.unwrap(value.asMap());
            case NUMBER:
                return value.asDouble();
            case STRING:
                return value.asString();
            default:
            case NIL:
                return null;
        }
    }

    public static JavascriptType wrap(ReadableType type) {
        switch (type) {
            case Array:
                return JavascriptType.ARRAY;
            case Boolean:
                return JavascriptType.BOOLEAN;
            case Map:
                return JavascriptType.MAP;
            case Number:
                return JavascriptType.NUMBER;
            case String:
                return JavascriptType.STRING;
            case Null:
            default:
                return JavascriptType.NIL;
        }
    }

    public static JavascriptMapKeyIterator wrap(ReadableMapKeySetIterator readableMapKeySetIterator) {
        return new JavascriptMapKeyIteratorImpl(readableMapKeySetIterator);
    }

    public static final void copyReactArray(ReadableArray source, WritableArray dest) {
        for (int i = 0; i < source.size(); ++i) {
            switch (source.getType(i)) {
                case Null:
                    dest.pushNull();
                    break;
                case Boolean:
                    dest.pushBoolean(source.getBoolean(i));
                    break;
                case Number:
                    dest.pushDouble(source.getDouble(i));
                    break;
                case String:
                    dest.pushString(source.getString(i));
                    break;
                case Array:
                    WritableArray arrayCopy = Arguments.createArray();
                    copyReactArray(source.getArray(i), arrayCopy);
                    dest.pushArray(arrayCopy);
                    break;
                case Map:
                    WritableMap mapCopy = Arguments.createMap();
                    copyReactMap(source.getMap(i), mapCopy);
                    dest.pushMap(mapCopy);
                    break;
            }
        }
    }

    public static final void copyReactMap(ReadableMap source, WritableMap dest) {
        dest.merge(source);
    }
}
