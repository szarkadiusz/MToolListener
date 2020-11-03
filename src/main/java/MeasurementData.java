//import java.time.Duration;
//import java.time.format.DateTimeFormatter;
//
//public class MeasurementData {
//    static String pin;
//    static String dateTimeHigh;
//
//    public String inputDataToString() {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        if (EventListener.pinState) {
//            pin = EventListener.pinNumber;
//            dateTimeHigh = EventListener.dateTime.format(formatter);
//        }
//
//        System.out.println("{\"Pin\":" + pin + ", \"DateTime\":\"" + dateTimeHigh + "\"}");
//
//        return "{\"Pin\":" + pin + ", \"DateTime\":\"" + dateTimeHigh + "\"}";
//    }
//
//
//////
////        station1CycleTime = Duration.between(dateTimeS1out,dateTimeS1in);
////        station2CycleTime = Duration.between(dateTimeS2out,dateTimeS2in);
////        station3CycleTime = Duration.between(dateTimeS3out,dateTimeS3in);
////        station4CycleTime = Duration.between(dateTimeS4out,dateTimeS4in);
//
//
////Logika w listener – is high klasa stan ?
////Update stanow czujnikow wyślij rest
////{ „stanowisko”: „Pierwsze”, „czas”: 15.32 }
////
////Class Pomiar {
////String stanowisko;
////double czas’
////}
//
//
////        dateTime=LocalDateTime.now().format();
//
//
//}
