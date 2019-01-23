package com.rushingvise.reactcpp;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;

class JavascriptArrayImpl extends JavascriptArray {
    private final ReadableArray mReadableArray;
    private final WritableArray mWritableArray;

    public JavascriptArrayImpl() {
        mWritableArray = Arguments.createArray();
        mReadableArray = mWritableArray;
    }

    public JavascriptArrayImpl(ReadableArray array) {
        mReadableArray = array;
        mWritableArray = null;
    }

    @Override
    public int size() {
        return mReadableArray.size();
    }

    @Override
    public boolean isNull(int index) {
        return mReadableArray.isNull(index);
    }

    @Override
    public boolean getBoolean(int index) {
        return mReadableArray.getBoolean(index);
    }

    @Override
    public double getDouble(int index) {
        return mReadableArray.getDouble(index);
    }

    @Override
    public int getInt(int index) {
        return mReadableArray.getInt(index);
    }

    @Override
    public String getString(int index) {
        return mReadableArray.getString(index);
    }

    @Override
    public JavascriptArray getArray(int index) {
        return ReactDjinni.wrap(mReadableArray.getArray(index));
    }

    @Override
    public JavascriptMap getMap(int index) {
        return ReactDjinni.wrap(mReadableArray.getMap(index));
    }

    @Override
    public JavascriptObject getObject(int index) {
        return ReactDjinni.wrap(mReadableArray.getDynamic(index));
    }

    @Override
    public JavascriptType getType(int index) {
        return ReactDjinni.wrap(mReadableArray.getType(index));
    }

    @Override
    public void pushNull() {
        mWritableArray.pushNull();
    }

    @Override
    public void pushBoolean(boolean value) {
        mWritableArray.pushBoolean(value);
    }

    @Override
    public void pushDouble(double value) {
        mWritableArray.pushDouble(value);
    }

    @Override
    public void pushInt(int value) {
        mWritableArray.pushInt(value);
    }

    @Override
    public void pushString(String value) {
        mWritableArray.pushString(value);
    }

    @Override
    public void pushArray(JavascriptArray array) {
        mWritableArray.pushArray(ReactDjinni.unwrap(array));
    }

    @Override
    public void pushMap(JavascriptMap map) {
        mWritableArray.pushMap(ReactDjinni.unwrap(map));
    }

    @Override
    public void pushObject(JavascriptObject value) {
        switch (value.getType()) {
            case ARRAY:
                mWritableArray.pushArray(ReactDjinni.unwrap(value.asArray()));
                break;
            case BOOLEAN:
                mWritableArray.pushBoolean(value.asBoolean());
                break;
            case MAP:
                mWritableArray.pushMap(ReactDjinni.unwrap(value.asMap()));
                break;
            case NUMBER:
                mWritableArray.pushDouble(value.asDouble());
                break;
            case STRING:
                mWritableArray.pushString(value.asString());
                break;
            default:
            case NIL:
                mWritableArray.pushNull();
                break;
        }
    }

    @Override
    public void append(JavascriptArray source) {
        if (mWritableArray != null) {
            ReactDjinni.copyReactArray(((JavascriptArrayImpl) source).getReadableArray(), mWritableArray);
        }
    }

    public ReadableArray getReadableArray() {
        return mReadableArray;
    }

    public WritableArray getWritableArray() {
        return mWritableArray;
    }
}
