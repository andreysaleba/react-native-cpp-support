package com.rushingvise.reactcpp;

import com.facebook.react.bridge.Callback;

import java.util.ArrayList;
import java.util.Arrays;

class JavascriptCallbackImpl extends JavascriptCallback {
    private final Callback mCallback;

    public JavascriptCallbackImpl(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void invoke(ArrayList<JavascriptObject> args) {
        Object[] javaArgs = new Object[args.size()];
        int i = 0;
        for (JavascriptObject o : args) {
            javaArgs[i] = ReactDjinni.unwrap(o);
            i++;
        }
        mCallback.invoke(javaArgs);
    }

    @Override
    public void invokeSingleArg(JavascriptObject o) {
        ArrayList<JavascriptObject> args = new ArrayList<>();
        args.add(o);
        invoke(args);
    }
}
