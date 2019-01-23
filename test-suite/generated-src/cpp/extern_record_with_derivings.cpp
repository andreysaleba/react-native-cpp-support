// AUTOGENERATED FILE - DO NOT MODIFY!
// This file generated by Djinni from yaml-test.djinni

#include "extern_record_with_derivings.hpp"  // my header


bool operator==(const ExternRecordWithDerivings& lhs, const ExternRecordWithDerivings& rhs) {
    return lhs.member == rhs.member &&
           lhs.e == rhs.e;
}

bool operator!=(const ExternRecordWithDerivings& lhs, const ExternRecordWithDerivings& rhs) {
    return !(lhs == rhs);
}

bool operator<(const ExternRecordWithDerivings& lhs, const ExternRecordWithDerivings& rhs) {
    if (lhs.member < rhs.member) {
        return true;
    }
    if (rhs.member < lhs.member) {
        return false;
    }
    if (lhs.e < rhs.e) {
        return true;
    }
    if (rhs.e < lhs.e) {
        return false;
    }
    return false;
}

bool operator>(const ExternRecordWithDerivings& lhs, const ExternRecordWithDerivings& rhs) {
    return rhs < lhs;
}

bool operator<=(const ExternRecordWithDerivings& lhs, const ExternRecordWithDerivings& rhs) {
    return !(rhs < lhs);
}

bool operator>=(const ExternRecordWithDerivings& lhs, const ExternRecordWithDerivings& rhs) {
    return !(lhs < rhs);
}