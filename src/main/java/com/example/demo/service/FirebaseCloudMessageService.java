package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL =
            "https://fcm.googleapis.com/v1/projects/laver-1f445/messages:send";
    private final ObjectMapper objectMapper;
    public void sendMessageTo(String targetToken, String title, String body, String sound) throws IOException{
        String message = makeMessage(targetToken, title, body, sound);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " +
                        getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body, String sound) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .android(FcmMessage.Android.builder()
                                .notification(
                                        FcmMessage.NotificationSound.builder()
                                                .sound(sound).build()
                                ).build())
                        .build()).validateOnly(false).build();
        return objectMapper.writeValueAsString(fcmMessage);

    }


    //    {
//        "message": {
//            "token": "your-token-value",
//            "notification": {
//                "title": "Test",
//                "body": "Test message from server"
//            },
//            "android": {
//                "notification": {
//                    "sound": "default"
//                }
//            },
//            "apns": {
//                "payload": {
//                    "sound": "default"
//                }
//            }
//        }
//    }

    private String getAccessToken() throws IOException{
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new
                        ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }


}


