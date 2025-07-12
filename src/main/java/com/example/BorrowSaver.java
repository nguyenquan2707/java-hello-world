package com.example;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class BorrowSaver {

    public static void saveBorrow(Firestore db, String symbol, double borrowableValue) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("symbol", symbol.toUpperCase());
            data.put("borrowable", borrowableValue);
            data.put("timestamp", System.currentTimeMillis());

            // Example: Collection = "borrow_history", Document ID = auto-generated
            db.collection("borrow_history").add(data);

            System.out.printf("✅ Saved borrow for %s: %.2f\n", symbol, borrowableValue);
        } catch (Exception e) {
            System.err.println("❌ Failed to save to Firestore: " + e.getMessage());
        }
    }

}
