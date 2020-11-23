import com.mongodb.*;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.tz.UTCProvider;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventListener {
//    static Integer pinNumber;
//    static Boolean pinState;
//    static String dateTime;


    static MongoClient mongoClient = new MongoClient("192.168.1.98", 27017);

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneOffset.UTC);

    public static void main(String args[]) throws InterruptedException, IOException {
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        final GpioController gpio = GpioFactory.getInstance();
        

        List<Pin> pins = Arrays.asList(
                RaspiPin.GPIO_01,
                RaspiPin.GPIO_02,
                RaspiPin.GPIO_03,
                RaspiPin.GPIO_04,
                RaspiPin.GPIO_05,
                RaspiPin.GPIO_06,
                RaspiPin.GPIO_07,
                RaspiPin.GPIO_08);

        for (Pin pin : pins) {
            GpioPinDigitalInput gpioPinDigitalInput = gpio.provisionDigitalInputPin(pin);
            gpioPinDigitalInput.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // display pin state on console
                    stateChange(event);
                    ;
                }

            });
        }

        while (true) {
            Thread.sleep(500);

        }
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller


    }

    public static void stateChange(GpioPinDigitalStateChangeEvent event) {


        Integer pinNumber;
        Boolean pinState;
        String dateTime;

        System.out.println(event.getPin().toString().substring(6,7));
        pinNumber = Integer.parseInt(event.getPin().toString().substring(6,7));
        pinState = event.getState().isLow();
//        dateTime = dateTimeFormatter.format(LocalDateTime.now());

        if (pinState) {
            int stationNumber = (pinNumber + 1) / 2;
            Station station = StateHolder.stations[stationNumber-1];
            boolean out = pinNumber % 2 == 0;
            if (out) {
                station.endPinActivated();
            } else {
                station.startPinActivated();
            }
        }

    }




    public static class StateHolder {

        public static Station[] stations; 

        static {
            int size = 4;
            stations = new Station[size];
            for (int i = 0; i < size; i++) {
                stations[i] = new Station();
            }
        }
    }

    public static class Station {
        private int productCounter = 0;
        private LocalDateTime productProductionStart = null;
        private  long productCycleTime;

        public void startPinActivated() {

            productProductionStart = LocalDateTime.now();
        }


        public void endPinActivated() {
            
            if (productProductionStart == null) {
                System.out.println("warning mistake");
                return;
            }

            productCycleTime= LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() - productProductionStart.toInstant(ZoneOffset.UTC).toEpochMilli();
            productProductionStart = null;
            productCounter++;
            System.out.println("Database updated");
            mongoDBDataInsert(this);
        }


    }
    public static void mongoDBDataInsert(Station station) {



        DB database = mongoClient.getDB("MTool");
        System.out.println("Connect to database successfully");

        DBCollection collection = database.getCollection("MToolData");
        System.out.println("Collection selected successfully");

        BasicDBObject document = new BasicDBObject();

        document.put("CTmiliseconds", station.productCycleTime);
        document.put("ProductCount", station.productCounter);
        document.put("DateLeaved", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now()));
        collection.insert(document);
        System.out.println("Data inserted successfully");


    }

}

