package com.rushingvise.reactcpp;

import com.facebook.react.bridge.Promise;

class JavascriptPromiseImpl extends JavascriptPromise {
    private final Promise mPromise;

    public JavascriptPromiseImpl(Promise promise) {
        mPromise = promise;
    }

    @Override
    public void resolveMap(JavascriptMap value) {
        mPromise.resolve(ReactDjinni.unwrap(value));
    }

    @Override
    public void resolveObject(JavascriptObject value) {
        mPromise.resolve(ReactDjinni.unwrap(value));
    }

    @Override
    public void resolveArray(JavascriptArray value) {
        mPromise.resolve(ReactDjinni.unwrap(value));
    }

    @Override
    public void resolveDouble(double value) {
        mPromise.resolve(value);
    }

    @Override
    public void resolveInt(int value) {
        mPromise.resolve(value);
    }

    @Override
    public void resolveString(String value) {
        mPromise.resolve(value);
    }

    @Override
    public void resolveNull() {
        mPromise.resolve(null);
    }

    @Override
    public void resolveBoolean(boolean value) {
        mPromise.resolve(value);
    }

    @Override
    public void reject(String code, String message) {
        mPromise.reject(code, message);
    }
}
