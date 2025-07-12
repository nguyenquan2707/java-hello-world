package com.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.*;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirestoreInit {
//    public static Firestore initialize() throws IOException {
//        String jsonKey = System.getenv("FIREBASE_CONFIG_BASE64");
//        if (jsonKey == null) {
//            throw new RuntimeException("FIREBASE_CREDENTIALS_JSON env var is missing.");
//        }
//
//        InputStream serviceAccount = new ByteArrayInputStream(jsonKey.getBytes(StandardCharsets.UTF_8));
//
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        }
//
//        return FirestoreClient.getFirestore();
//    }

    public static Firestore initialize() throws IOException {
        String encoded = System.getenv("FIREBASE_CONFIG_BASE64");
        if (encoded == null || encoded.isEmpty()) {
            throw new IllegalStateException("Missing FIREBASE_CONFIG_BASE64 env var");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encoded);

        // Ghi tạm ra file để Firebase SDK đọc
        File tempFile = File.createTempFile("firebase-service-account", ".json");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(decodedBytes);
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(tempFile));
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);
        System.out.println("✅ Firebase initialized!");

        return FirestoreClient.getFirestore();
    }
}
