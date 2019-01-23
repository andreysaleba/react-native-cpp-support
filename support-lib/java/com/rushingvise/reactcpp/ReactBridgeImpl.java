package com.rushingvise.reactcpp;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

class ReactBridgeImpl extends ReactBridge {
    private final ReactApplicationContext mReactContext;

    public ReactBridgeImpl(ReactApplicationContext reactContext) {
        mReactContext = reactContext;
    }

    @Override
    public JavascriptMap createMap() {
        return ReactDjinni.createMap();
    }

    @Override
    public JavascriptArray createArray() {
        return ReactDjinni.createArray();
    }

    @Override
    public JavascriptMap copyMap(JavascriptMap map) {
        return ReactDjinni.createMap(map);
    }

    @Override
    public JavascriptArray copyArray(JavascriptArray array) {
        return ReactDjinni.createArray(array);
    }

    @Override
    public void emitEventWithMap(String name, JavascriptMap params) {
        mReactContext.getJSModule(RCTNativeAppEventEmitter.class).emit(name, ReactDjinni.unwrap(params));
    }

    @Override
    public void emitEventWithArray(String name, JavascriptArray params) {
        mReactContext.getJSModule(RCTNativeAppEventEmitter.class).emit(name, ReactDjinni.unwrap(params));
    }

    @Override
    public JobDispatcher createJobDispatcher(JobQueue queue) {
        return new JobDispatcherImpl(queue);
    }
}
