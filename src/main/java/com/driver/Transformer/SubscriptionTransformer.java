package com.driver.Transformer;

import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;

public class SubscriptionTransformer {
    public Subscription subscriptionDtoToEntity(SubscriptionEntryDto subscriptionEntryDto){
        Subscription subscription=new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        return subscription;
    }
}