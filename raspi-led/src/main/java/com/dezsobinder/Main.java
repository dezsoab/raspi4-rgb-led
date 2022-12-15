package com.dezsobinder;

import com.dezsobinder.controller.LedService;
import com.dezsobinder.model.LedRegistry;
import com.pi4j.Pi4J;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("==== STARTING APPLICATION ====");

        LedRegistry ledRegistry = new LedRegistry();
        LedService ledService = new LedService(ledRegistry);

        var pi4j = Pi4J.newAutoContext();

        ledService.registerLed(pi4j, 22, "red");
        ledService.registerLed(pi4j, 27, "blue");
        ledService.registerLed(pi4j, 17, "green");

        ledService.turnAllOnWithDelay(3000);

        ledService.blinkAll(15, 130);

        ledService.turnAllOn();

        ledService.turnAllOffWithDelay(2000);


        System.out.println("== SHUTTING DOWN ==");
        pi4j.shutdown();
        System.out.println("=== DONE ===");
    }
}
