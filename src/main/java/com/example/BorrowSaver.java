package com.example;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;


import java.util.HashMap;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.concurrent.ExecutionException;

public class BorrowSaver {

    /**
     * Load data
     */
    public static void loadBorrowed(Firestore firestore) {
        try {
            CollectionReference collection = firestore.collection("borrow_history");
            ApiFuture<QuerySnapshot> future = collection.get();
            QuerySnapshot snapshot = future.get();

            System.out.println("📥 Loaded dummy data from Firestore:");
            for (DocumentSnapshot doc : snapshot.getDocuments()) {
                System.out.println("- Symbol: " + doc.getId() + ", Borrow: " + doc.getDouble("borrowable"));
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Failed to load borrow data: " + e.getMessage());
        }
    }

    public static void saveDummy(Firestore firestore) {
        Map<String, Object> dummyData = new HashMap<>();
        dummyData.put("symbol", "BTC_USDT");
        dummyData.put("borrowable", 123456.78);
        dummyData.put("timestamp", System.currentTimeMillis());

        try {
            ApiFuture<DocumentReference> future = firestore
                    .collection("borrow_history")
                    .add(dummyData);

            DocumentReference docRef = future.get();  // blocking
            System.out.println("✅ Dummy saved with ID: " + docRef.getId());
        } catch (Exception e) {
            System.err.println("❌ Failed to save dummy: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
