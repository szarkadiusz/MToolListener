//import okhttp3.*;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//
//public class PostCreator {
//
//
//    OkHttpClient client = new OkHttpClient();
//    MeasurementData measurementData = new MeasurementData();
//
//    public void jsonCreator() throws IOException {
//        String json = measurementData.inputDataToString();
//
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json"), json);
//
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/piresponse")
//                .post(body)
//                .build();
//
//        Call call = client.newCall(request);
//        Response response = call.execute();
//
//    }
//
//
//
//}