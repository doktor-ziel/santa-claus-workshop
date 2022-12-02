package com.griddynamics.tech3camp.santa.claus.workshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.griddynamics.tech3camp.santa.claus.workshop.Utils.*;

public class GiftProductionTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(GiftProductionTask.class);

    private final String giftToProduct;

    public GiftProductionTask(String giftToProduct) {
        this.giftToProduct = giftToProduct;
    }

    @Override
    public void run() {
        justSleepForLongRandomMoment();
        logger.info("Gift '{}' done", giftToProduct);
    }
}
