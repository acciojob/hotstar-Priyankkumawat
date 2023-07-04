package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.Transformer.WebSeriesTransformer;
import com.driver.model.ProductionHouse;
import com.driver.model.SubscriptionType;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;
//    @Autowired
//    WebSeriesTransformer webSeriesTransformer;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo

//        WebSeries webSeries=webSeriesTransformer.WebSeriesDtoToEntity(webSeriesEntryDto);
        String name=webSeriesEntryDto.getSeriesName();
        int age=webSeriesEntryDto.getAgeLimit();
        double rating=webSeriesEntryDto.getRating();
        SubscriptionType subscriptionType=webSeriesEntryDto.getSubscriptionType();

        WebSeries webSeries=new WebSeries(name,age,rating,subscriptionType);

        ProductionHouse productionHouse=productionHouseRepository.
                findById(webSeriesEntryDto.getProductionHouseId()).get();

        double ratingSum=0;

        if(webSeriesRepository.findBySeriesName(webSeries.getSeriesName()) != null){
            throw new Exception("Series is already present");
        }

        webSeries=webSeriesRepository.save(webSeries);

        for(WebSeries webSeries1 : productionHouse.getWebSeriesList()){
            ratingSum+=webSeries1.getRating();
        }

        webSeries.setProductionHouse(productionHouse);
        productionHouse.getWebSeriesList().add(webSeries);

        ratingSum += webSeries.getRating();
        ratingSum /= productionHouse.getWebSeriesList().size();
        productionHouse.setRatings(ratingSum);
        productionHouseRepository.save(productionHouse);
        return webSeries.getId();
    }
}
