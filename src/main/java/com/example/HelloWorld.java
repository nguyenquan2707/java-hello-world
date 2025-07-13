package com.example;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Margin;
import com.google.cloud.firestore.Firestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException, IOException {
        String APIKEY = System.getenv("BINANCE_API_KEY");
        String SECRETKEY = System.getenv("BINANCE_SECRET_KEY");

        System.out.println("APIKEY: " + APIKEY);
        System.out.println("Secret: " + SECRETKEY);


        SpotClient spotClient = new SpotClientImpl(APIKEY, SECRETKEY);
        Margin margin = spotClient.createMargin();
        Firestore database = FirestoreInit.initialize();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                fetchAndSaveInventory(margin, database);
            } catch (Exception e) {
                System.err.println("❌ Error fetching inventory: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.HOURS);  // Gọi ngay và sau đó mỗi giờ


        while (true) {
            Thread.sleep(2000); // Sleep 1 giây
//            BorrowSaver.saveDummy(database);
            //Load data
//            BorrowSaver.loadBorrowed(database);

            System.out.println("🚀 Hello from Java - deployed in the cloud!");
        }
    }

    private static void fetchAndSaveInventory(Margin margin, Firestore db) throws IOException {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("type", "ISOLATED");

        String response = margin.availableInventory(params);
        System.out.println("📦 Inventory fetched: " + response);

        Map<String, Object> data = new HashMap<>();
        data.put("inventory_json", response);
        data.put("timestamp", System.currentTimeMillis());

        db.collection("inventory_hourly").add(data);
        System.out.println("✅ Inventory saved to Firestore!");
    }
}
