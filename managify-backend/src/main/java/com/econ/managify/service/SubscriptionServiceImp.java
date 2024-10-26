package com.econ.managify.service;

import com.econ.managify.interfaces.SubscriptionService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.PlanType;
import com.econ.managify.model.Subscription;
import com.econ.managify.model.User;
import com.econ.managify.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImp  implements SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;
    public SubscriptionServiceImp(UserService userService, SubscriptionRepository subscriptionRepository) {
        this.userService = userService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (!isValid(subscription)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.getSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)) {subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));}
        else {subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));}
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)){return true;}
        LocalDate endDate = subscription.getGetSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
