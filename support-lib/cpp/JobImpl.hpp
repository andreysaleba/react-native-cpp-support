#include "Job.hpp"

#include <memory>
#include <functional>

class JobImpl : public Job {
public:
    static std::shared_ptr<Job> create(std::function<void()> func);
    JobImpl(std::function<void()> func);

    void run() override;

private:
    const std::function<void()> mFunc;
};