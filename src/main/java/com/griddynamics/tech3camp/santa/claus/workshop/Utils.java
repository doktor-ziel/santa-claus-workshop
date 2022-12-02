package com.griddynamics.tech3camp.santa.claus.workshop;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static void justSleepForLongRandomMoment() {
        sleepForProvidedTime(ThreadLocalRandom.current().nextInt(50, 100));
    }


    public static void justSleepForShortRandomMoment() {
        sleepForProvidedTime(ThreadLocalRandom.current().nextInt(5, 50));
    }

    public static void sleepForProvidedTime(long millisToWait) {
        try {
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error during sleeping", e);
        }
    }
}
