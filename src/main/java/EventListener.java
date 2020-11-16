import com.mongodb.*;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventListener {
//    static Integer pinNumber;
//    static Boolean pinState;
//    static String dateTime;


    static MongoClient mongoClient = new MongoClient("192.168.1.129", 27017);

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
// TODO: 14.11.2020  wyciagnasc int ?
        pinNumber = Integer.valueOf(event.getPin().toString().substring(5,6));
        pinState = event.getState().isLow();
//        dateTime = dateTimeFormatter.format(LocalDateTime.now());

        if (pinState) {
            int stationNumber = (pinNumber + 1) / 2;
            Station station = StateHolder.stations[stationNumber];
            boolean out = pinNumber % 2 == 0;
            if (out) {
                station.endPinActivated();
            } else {
                station.startPinActivated();
            }
        }

//        System.out.println(pinNumber + " " + pinState + " " + dateTime);

    }

    public static void mongoDBDataInsert(Station station) {
     

            DB database = mongoClient.getDB("MTool");
            System.out.println("Connect to database successfully");

            DBCollection collection = database.getCollection("MToolData");
            System.out.println("Collection selected successfully");

            BasicDBObject document = new BasicDBObject();

            document.put("CT", station.productCycleTime);
            document.put("Count", station.productCounter);
            collection.insert(document);
            System.out.println("Data inserted successfully");
        

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

            productCycleTime= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - productProductionStart.toEpochSecond(ZoneOffset.UTC);
            productProductionStart = null;
            productCounter++;
            System.out.println("Wytworzylem pprodukt numer dataPoczatkuWytwarzania, zajelo to czasJakiToZajelo"); // + sztal do Mongo
            
        }


    }


//    Rekord w mongo:
//    ID
//    Numer szeregowy produktu (z tego licznika)
//    Czas oslugi na tym stanowisku
//    Data zjechanie ze stanowiska
//    numer stanowiska
//        + sprobuj zapisac date i godzine w formacie Mongo


}

