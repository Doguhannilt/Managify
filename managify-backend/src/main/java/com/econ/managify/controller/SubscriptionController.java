package com.econ.managify.controller;

import com.econ.managify.interfaces.SubscriptionService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.model.PlanType;
import com.econ.managify.model.Subscription;
import com.econ.managify.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeUserSubscription(@RequestHeader("Authorization") String jwt, @RequestParam PlanType planType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.updateSubscription(user.getId(),planType);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
