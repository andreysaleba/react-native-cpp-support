#include "JobQueueImpl.hpp"
#include "JobImpl.hpp"

std::shared_ptr<JobQueueImpl> JobQueueImpl::create() {
    return std::make_shared<JobQueueImpl>();
}

JobQueueImpl::JobQueueImpl() {
}

JobQueueImpl::~JobQueueImpl() {

}

std::shared_ptr<Job> JobQueueImpl::poll() {
    std::unique_lock<std::mutex> lock(mMutex);
    mCondition.wait(lock, [&]{ return !mQueue.empty(); });
    if (!mQueue.empty()) {
        auto func = mQueue.front();
        mQueue.pop_front();
        return JobImpl::create(func);
    } else {
        return nullptr;
    }
}

void JobQueueImpl::interruptPoll() {
    std::unique_lock<std::mutex> lock(mMutex);
    mCondition.notify_one();
}

void JobQueueImpl::enqueue(std::function<void()> function) {
    std::unique_lock<std::mutex> lock(mMutex);
    mQueue.push_back(function);
    mCondition.notify_one();
}
