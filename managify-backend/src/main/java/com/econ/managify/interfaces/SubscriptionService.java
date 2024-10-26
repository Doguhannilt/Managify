package com.econ.managify.interfaces;

import com.econ.managify.model.PlanType;
import com.econ.managify.model.Subscription;
import com.econ.managify.model.User;

public interface SubscriptionService {
        Subscription createSubscription(User user);
        Subscription getUserSubscription(Long userId) throws Exception;
        Subscription updateSubscription(Long userId, PlanType planType);
        boolean isValid(Subscription subscription);
}
