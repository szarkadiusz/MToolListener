import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.joda.time.PeriodType;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EventListener {
    static String pinNumber;
    static Boolean pinState;
    static LocalDateTime dateTime;
    static LocalDateTime dateTimeS1in;
    static LocalDateTime dateTimeS1out;
    static LocalDateTime dateTimeS2in;
    static LocalDateTime dateTimeS2out;
    static LocalDateTime dateTimeS3in;
    static LocalDateTime dateTimeS3out;
    static LocalDateTime dateTimeS4in;
    static LocalDateTime dateTimeS4out;
    static Duration station1CycleTime;
    static Duration station2CycleTime;
    static Duration station3CycleTime;
    static Duration station4CycleTime;
    static LocalDate station1Date;
    static LocalDate station2Date;
    static LocalDate station3Date;
    static LocalDate station4Date;


    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput s1in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01);
        final GpioPinDigitalInput s1out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);
        final GpioPinDigitalInput s2in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);
        final GpioPinDigitalInput s2out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04);
        final GpioPinDigitalInput s3in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05);
        final GpioPinDigitalInput s3out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06);
        final GpioPinDigitalInput s4in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
        final GpioPinDigitalInput s4out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08);

        // set shutdown state for this input pin


        // create and register gpio pin listener
        s1in.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
                ;
            }

        });
        s1out.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });
        s2in.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });
        s2out.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });
        s3in.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });
        s3out.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });
        s4in.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });
        s4out.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                stateChange(event);
            }

        });


        if (s1in.isLow()){
            dateTimeS1in=LocalDateTime.now();
        }

        if (s1out.isLow()){
            dateTimeS1out=LocalDateTime.now();
            station1Date= LocalDate.now();
        }

        if (s2in.isLow()){
            dateTimeS2in=LocalDateTime.now();
        }

        if (s2out.isLow()){
            dateTimeS2out=LocalDateTime.now();
            station2Date= LocalDate.now();
        }

        if (s3in.isLow()){
            dateTimeS3in=LocalDateTime.now();
        }

        if (s3out.isLow()){
            dateTimeS3out=LocalDateTime.now();
            station3Date= LocalDate.now();
        }

        if (s4in.isLow()){
            dateTimeS4in=LocalDateTime.now();
        }

        if (s4out.isLow()){
            dateTimeS4out=LocalDateTime.now();
            station4Date= LocalDate.now();
        }

         station1CycleTime = Duration.between(dateTimeS1out,dateTimeS1in);
         station2CycleTime = Duration.between(dateTimeS2out,dateTimeS2in);
         station3CycleTime = Duration.between(dateTimeS3out,dateTimeS3in);
         station4CycleTime = Duration.between(dateTimeS4out,dateTimeS4in);



        // keep program running until user aborts (CTRL-C)
        while (true) {
            Thread.sleep(500);

        }
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller


    }

    public static void stateChange(GpioPinDigitalStateChangeEvent event) {

        if(pinNumber==RaspiPin.GPIO_01.toString() || )

        pinNumber = event.getPin().toString();
        pinState = event.getState().isLow() ? true : false;
        dateTime = LocalDateTime.now();



        System.out.println(pinNumber + " " + pinState + " " + dateTime);

    }




}

