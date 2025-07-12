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

public class FirestoreInit {
    public static Firestore initialize() throws IOException {
        String jsonKey = System.getenv("FIREBASE_CREDENTIALS_JSON");
        if (jsonKey == null) {
            throw new RuntimeException("FIREBASE_CREDENTIALS_JSON env var is missing.");
        }

        InputStream serviceAccount = new ByteArrayInputStream(jsonKey.getBytes(StandardCharsets.UTF_8));


        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        return FirestoreClient.getFirestore();
    }
}
