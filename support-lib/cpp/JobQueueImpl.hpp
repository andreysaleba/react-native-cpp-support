#include "JobQueue.hpp"
#include "Job.hpp"

#include <functional>
#include <list>
#include <memory>
#include <mutex>

class JobQueueImpl : public JobQueue {
public:
    static std::shared_ptr<JobQueueImpl> create();
    JobQueueImpl();
    virtual ~JobQueueImpl();

    std::shared_ptr<Job> poll() override;
    void interruptPoll() override;

    void enqueue(std::function<void()> function);

private:
    std::list<std::function<void()>> mQueue;
    std::mutex mMutex;
    std::condition_variable mCondition;
};
