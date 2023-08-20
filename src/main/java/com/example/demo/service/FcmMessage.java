package com.example.demo.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Notification notification;
        private String token;

        private Android android;

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
        private String title;
        private String body;
        private String image;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android{
        private NotificationSound notification;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class NotificationSound{
        private String sound;
    }


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


