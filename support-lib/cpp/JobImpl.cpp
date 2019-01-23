#include "JobImpl.hpp"

std::shared_ptr<Job> JobImpl::create(std::function<void()> func) {
    return std::make_shared<JobImpl>(func);
}

JobImpl::JobImpl(std::function<void()> func) : mFunc(func) {
}

void JobImpl::run() {
    mFunc();
}
