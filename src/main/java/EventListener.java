import com.mongodb.*;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.IOException;
import java.time.Duration;
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
Station station = new Station();
if (pinState) {

    DB database = mongoClient.getDB("MTool");
    System.out.println("Connect to database successfully");

    DBCollection collection = database.getCollection("MToolData");
    System.out.println("Collection selected successfully");

    BasicDBObject document = new BasicDBObject();
    document.put("Station", pinNumber);
    document.put("DateTime", dateTime);
    document.put("PinState", pinState);
    document.put("CT", station.productProductionStart);
    collection.insert(document);
    System.out.println("Data inserted successfully");
}

}


public static class StateHolder{
        public static Station[] stations = new Station[4];

}
public static class Station {
    private int productCounter = 0;
    private LocalDateTime productProductionStart = null;


    public void startPinActivated() {

            productProductionStart=LocalDateTime.now();
        }



    public void endPinActivated() {


            long productCycleTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - productProductionStart.toEpochSecond(ZoneOffset.UTC);
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

