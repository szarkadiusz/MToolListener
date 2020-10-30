import java.time.format.DateTimeFormatter;

public class MeasurementData {


        String pin = EventListener.pinNumber;
        String state = String.valueOf(EventListener.pinState);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateTimeHigh= EventListener.dateTime.format(formatter);



//Logika w listener – is high klasa stan ?
//Update stanow czujnikow wyślij rest
//{ „stanowisko”: „Pierwsze”, „czas”: 15.32 }
//
//Class Pomiar {
//String stanowisko;
//double czas’
//}


//        dateTime=LocalDateTime.now().format();





}
