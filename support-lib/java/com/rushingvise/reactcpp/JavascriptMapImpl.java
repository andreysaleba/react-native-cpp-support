package com.rushingvise.reactcpp;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

class JavascriptMapImpl extends JavascriptMap {
    private final ReadableMap mReadableMap;
    private final WritableMap mWritableMap;

    public JavascriptMapImpl() {
        mWritableMap = Arguments.createMap();
        mReadableMap = mWritableMap;
    }

    public JavascriptMapImpl(ReadableMap map) {
        mReadableMap = map;
        mWritableMap = null;
    }

    @Override
    public boolean hasKey(String name) {
        return mReadableMap.hasKey(name);
    }

    @Override
    public boolean isNull(String name) {
        return mReadableMap.isNull(name);
    }

    @Override
    public boolean getBoolean(String name) {
        return mReadableMap.getBoolean(name);
    }

    @Override
    public double getDouble(String name) {
        return mReadableMap.getDouble(name);
    }

    @Override
    public int getInt(String name) {
        return mReadableMap.getInt(name);
    }

    @Override
    public String getString(String name) {
        return mReadableMap.getString(name);
    }

    @Override
    public JavascriptArray getArray(String name) {
        return ReactDjinni.wrap(mReadableMap.getArray(name));
    }

    @Override
    public JavascriptMap getMap(String name) {
        return ReactDjinni.wrap(mReadableMap.getMap(name));
    }

    @Override
    public JavascriptObject getObject(String name) {
        return ReactDjinni.wrap(mReadableMap.getDynamic(name));
    }

    @Override
    public JavascriptType getType(String name) {
        return ReactDjinni.wrap(mReadableMap.getType(name));
    }

    @Override
    public JavascriptMapKeyIterator keySetIterator() {
        return ReactDjinni.wrap(mReadableMap.keySetIterator());
    }

    @Override
    public void putNull(String key) {
        mWritableMap.putNull(key);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        mWritableMap.putBoolean(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        mWritableMap.putDouble(key, value);
    }

    @Override
    public void putInt(String key, int value) {
        mWritableMap.putInt(key, value);
    }

    @Override
    public void putString(String key, String value) {
        mWritableMap.putString(key, value);
    }

    @Override
    public void putArray(String key, JavascriptArray value) {
        mWritableMap.putArray(key, ReactDjinni.unwrap(value));
    }

    @Override
    public void putMap(String key, JavascriptMap value) {
        mWritableMap.putMap(key, ReactDjinni.unwrap(value));
    }

    @Override
    public void putObject(String key, JavascriptObject value) {
        switch (value.getType()) {
            case ARRAY:
                mWritableMap.putArray(key, ReactDjinni.unwrap(value.asArray()));
                break;
            case BOOLEAN:
                mWritableMap.putBoolean(key, value.asBoolean());
                break;
            case MAP:
                mWritableMap.putMap(key, ReactDjinni.unwrap(value.asMap()));
                break;
            case NUMBER:
                mWritableMap.putDouble(key, value.asDouble());
                break;
            case STRING:
                mWritableMap.putString(key, value.asString());
                break;
            default:
            case NIL:
                mWritableMap.putNull(key);
                break;
        }
    }

    @Override
    public void merge(JavascriptMap source) {
        mWritableMap.merge(((JavascriptMapImpl) source).getReadableMap());
    }

    public ReadableMap getReadableMap() {
        return mReadableMap;
    }

    public WritableMap getWritableMap() {
        return mWritableMap;
    }
}
