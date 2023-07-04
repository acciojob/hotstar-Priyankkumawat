package com.driver.Transformer;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;

public class ProductionTransformer {
    public ProductionHouse productionHouseDtoToEntity(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionHouse=new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        return productionHouse;
    }
}
