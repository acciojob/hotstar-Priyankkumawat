package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.Transformer.SubscriptionTransformer;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    SubscriptionTransformer subscriptionTransformer;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        Subscription subscription=subscriptionTransformer.subscriptionDtoToEntity(subscriptionEntryDto);
        User user=userRepository.findById(subscriptionEntryDto.getUserId()).get();
        subscription.setUser(user);
        int amount=0;
        if(subscription.getSubscriptionType().toString().toUpperCase().equals("BASIC")){
            amount += 500 + 200*subscription.getNoOfScreensSubscribed();
        }
        else if(subscription.getSubscriptionType().toString().toUpperCase().equals("PRO")){
            amount += 800 + 250*subscription.getNoOfScreensSubscribed();
        }
        else{
            amount += 1000 + 350*subscription.getNoOfScreensSubscribed();
        }

        Date date = new java.util.Date();
        subscription.setStartSubscriptionDate(date);
        subscription.setTotalAmountPaid(amount);

        user.setSubscription(subscription);
        userRepository.save(user);
        return amount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        User user=userRepository.findById(userId).get();
        Subscription subscription=user.getSubscription();
        int updatedAmount=0;
        int diff=0;
        if (subscription.getSubscriptionType().toString().toUpperCase().equals("ELITE")){
            throw new Exception("Already the best Subscription");
        }
        else if(subscription.getSubscriptionType().toString().toUpperCase().equals("PRO")){
            updatedAmount = 1000 + 350*subscription.getNoOfScreensSubscribed();
            diff=updatedAmount-subscription.getTotalAmountPaid();

            subscription.setSubscriptionType(SubscriptionType.ELITE);
        }
        else{
            updatedAmount = 800 + 250*subscription.getNoOfScreensSubscribed();
            diff=updatedAmount-subscription.getTotalAmountPaid();

            subscription.setSubscriptionType(SubscriptionType.PRO);
        }
        subscription.setTotalAmountPaid(updatedAmount);
        user.setSubscription(subscription);
        userRepository.save(user);
        return diff;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        int amount=0;
        for(Subscription subscription : subscriptionRepository.findAll()){
            amount+=subscription.getTotalAmountPaid();
        }
        return amount;
    }

}
