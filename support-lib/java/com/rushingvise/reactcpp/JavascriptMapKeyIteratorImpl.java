package com.rushingvise.reactcpp;

import com.facebook.react.bridge.ReadableMapKeySetIterator;

class JavascriptMapKeyIteratorImpl extends JavascriptMapKeyIterator {
    private final ReadableMapKeySetIterator mReadableMapKeySetIterator;

    public JavascriptMapKeyIteratorImpl(ReadableMapKeySetIterator readableMapKeySetIterator) {
        mReadableMapKeySetIterator = readableMapKeySetIterator;
    }

    @Override
    public boolean hasNextKey() {
        return mReadableMapKeySetIterator.hasNextKey();
    }

    @Override
    public String nextKey() {
        return mReadableMapKeySetIterator.nextKey();
    }
}
