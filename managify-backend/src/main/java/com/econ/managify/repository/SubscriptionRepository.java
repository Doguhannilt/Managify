package com.econ.managify.repository;

import com.econ.managify.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> { Subscription findByUserId(Long userId);}
