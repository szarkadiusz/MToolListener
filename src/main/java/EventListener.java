import com.mongodb.*;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class EventListener {
    static String pinNumber;
    static Boolean pinState;
    static String dateTime;
    static MongoClient mongoClient = new MongoClient("192.168.1.129",27017);

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneOffset.UTC);

    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        final GpioController gpio = GpioFactory.getInstance();


        final GpioPinDigitalInput s1in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01);
        final GpioPinDigitalInput s1out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);
        final GpioPinDigitalInput s2in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);
        final GpioPinDigitalInput s2out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04);
        final GpioPinDigitalInput s3in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05);
        final GpioPinDigitalInput s3out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06);
        final GpioPinDigitalInput s4in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
        final GpioPinDigitalInput s4out = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08);


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


        while (true) {
            Thread.sleep(500);

        }
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller


    }

    public static void stateChange(GpioPinDigitalStateChangeEvent event) {
;

        pinNumber = event.getPin().toString();
        pinState = event.getState().isLow();
        dateTime = dateTimeFormatter.format(LocalDateTime.now());

        mongoDBDataInsert();
//        System.out.println(pinNumber + " " + pinState + " " + dateTime);

    }

public static void mongoDBDataInsert(){

if (pinState) {

    DB database = mongoClient.getDB("MTool");
    System.out.println("Connect to database successfully");

    DBCollection collection = database.getCollection("MToolData");
    System.out.println("Collection selected successfully");

    BasicDBObject document = new BasicDBObject();
    document.put("Station", pinNumber);
    document.put("DateTime", dateTime);
    document.put("PinState", pinState);
    collection.insert(document);
    System.out.println("Data inserted successfully");
}

}



}

