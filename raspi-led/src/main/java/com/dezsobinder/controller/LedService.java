package com.dezsobinder.controller;

import com.dezsobinder.model.LedRegistry;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;

public class LedService {
    private final LedRegistry ledRegistry;

    public LedService(LedRegistry ledRegistry) {
        this.ledRegistry = ledRegistry;
    }

    public void registerLed(Context context, int pinAddress, String id) {
        DigitalOutputProvider digitalInputProvider = context.provider("pigpio-digital-output");
        var led = digitalInputProvider.create(DigitalOutput.newConfigBuilder(context).address(pinAddress).id(id));
        ledRegistry.getLeds().add(led);
        System.out.println("LED id: " + id + " is now added to the registry");
    }

    public void turnAllOn() {
        ledRegistry.getLeds().forEach(DigitalOutput::high);
    }

    public void turnAllOnWithDelay(long milliseconds) throws InterruptedException {
        for (DigitalOutput led : ledRegistry.getLeds()) {
            led.high();
            System.out.println(led.getId() + " is now turned on");
            Thread.sleep(milliseconds);
        }
    }

    public void turnAllOffWithDelay(long millisecond) throws InterruptedException {
        for (DigitalOutput led : ledRegistry.getLeds()) {
            led.low();
            System.out.println(led.getId() + " is now turned off");
            Thread.sleep(millisecond);
        }
    }

    public void blinkAll(int blinkTimes, long millisecondsBetweenBlinks) throws InterruptedException {
        for (long i = 0; i < blinkTimes; i++) {
            if (i % 2 == 0) {
                ledRegistry.getLeds().forEach(DigitalOutput::low);
            } else {
                ledRegistry.getLeds().forEach(DigitalOutput::high);
            }
            Thread.sleep(millisecondsBetweenBlinks);
        }
    }

    public void turnOffAllLeds() {
        ledRegistry.getLeds().forEach(DigitalOutput::low);
        System.out.println("Turning off all leds");
    }
}
