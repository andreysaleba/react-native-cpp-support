package com.rushingvise.reactcpp;

public class JobDispatcherImpl extends JobDispatcher {
    private final Thread mThread;
    private boolean mDestroyed;
    private final JobQueue mQueue;

    public JobDispatcherImpl(JobQueue queue) {
        mThread = new Thread(mRunnable);
        mQueue = queue;
    }

    @Override
    public synchronized void start() {
        if (!mDestroyed) {
            mThread.start();
        }
    }

    @Override
    public synchronized void quit() {
        mDestroyed = true;
        mQueue.interruptPoll();
    }

    private synchronized boolean isDestroyed() {
        return mDestroyed;
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (!isDestroyed()) {
                Job job;
                while ((job = mQueue.poll()) != null) {
                    job.run();
                }
            }
        }
    };
}
