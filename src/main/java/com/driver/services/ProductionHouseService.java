package com.driver.services;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.Transformer.ProductionTransformer;
import com.driver.model.ProductionHouse;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionHouseService {
    @Autowired
    ProductionHouseRepository productionHouseRepository;
    @Autowired
    ProductionTransformer productionTransformer;
    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionHouse= productionTransformer.productionHouseDtoToEntity(productionHouseEntryDto);
        productionHouseRepository.save(productionHouse);
        productionHouse.setRatings(0);
        return  productionHouse.getId();
    }
}