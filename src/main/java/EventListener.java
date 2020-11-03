import com.mongodb.*;
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



    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");
//        PostCreator postCreator = new PostCreator();

//        MeasurementData measurementData = new MeasurementData();
//        measurementData.inputDataToString();
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

        // keep program running until user aborts (CTRL-C)
        while (true) {
            Thread.sleep(500);

        }
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller



    }

    public static void stateChange(GpioPinDigitalStateChangeEvent event) {

        pinNumber = event.getPin().toString();
        pinState = event.getState().isLow() ? true : false;
        dateTime = LocalDateTime.now();

        mongoDBDataInsert();
//        System.out.println(pinNumber + " " + pinState + " " + dateTime);

    }

public static void mongoDBDataInsert(){

MongoClient mongoClient = new MongoClient("192.168.1.1",27017);

    DB database = mongoClient.getDB("MToolData");
    DBCollection collection = database.getCollection("MToolListenerDB");
    BasicDBObject document = new BasicDBObject();
    document.put("Station", pinNumber);
    document.put("DateTime", dateTime);
    document.put("PinState", pinState);
    collection.insert(document);
}



}

