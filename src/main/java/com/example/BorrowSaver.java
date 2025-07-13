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
                System.out.println("- Symbol: " + doc.getId() + ", Borrow: " + doc.getDouble("value"));
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Failed to load borrow data: " + e.getMessage());
        }
    }

    public static void saveBorrow(Firestore firestore) {
        CollectionReference collection = firestore.collection("borrow_history");

        Map<String, Double> dummyData = new HashMap<>();
        dummyData.put("OMNI", 12345.67);
        dummyData.put("PMG", 9876.54);
        dummyData.put("BANANA", 45000.0);

        dummyData.forEach((symbol, value) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("value", value);

            DocumentReference docRef = collection.document(symbol);
            ApiFuture<?> result = docRef.set(data);

            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("✅ Dummy data saved to Firestore.");
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
