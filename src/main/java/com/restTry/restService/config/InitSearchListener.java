package com.restTry.restService.config;

import com.restTry.restService.challenge.SearchSvcInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Component
@PropertySource("classpath:/com/restTry/restService/search.properties")
public class InitSearchListener {

    Logger logger = Logger.getLogger(this.getClass().getName());


    @Value("$(restTry.priceAdjust.enabled):false")
    private String priceAdjustFunctionalityEnabled;

    @Value("${restTry.adjustedPrices.filename}")
    private String adjustedPricesFileName;

    @Value("${restTry.itemPrices.resources.filename}")
    private String itemPricesResourceName;

    @Autowired
    SearchSvcInterface searchSvc;

    @Autowired
    ApplicationContext applicationContext;

    // Set order higher so this gets executed AFTER InitPriceAdjustListener in full configuration
    @EventListener(ApplicationReadyEvent.class)
    @Order(10)
    public void initServices() throws IOException {

        File searchInitFile;

        // Are we are running in integrated configuration

        if (priceAdjustFunctionalityEnabled.matches("false")) {

            searchInitFile = new File(adjustedPricesFileName);
            logger.info(">>>  Initializing Search Service in Full Configuration...");

            searchSvc.init(searchInitFile);
        } else { // only path applicable to CANDIDATES going through Screening
            logger.info(">>>  Initializing Search Service in Search Only Configuration...");
            searchInitFile = applicationContext.getResource("classpath:" + itemPricesResourceName).getFile();
            searchSvc.init(searchInitFile);
        }


    }

}
