package com.example;

import com.google.cloud.firestore.Firestore;

import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException, IOException {

        Firestore db = FirestoreInit.initialize();
        // Keep app alive
        while (true) {
            Thread.sleep(1000); // Sleep 1 giây
            String symbol = "BANANAS31";
            double currentBorrowable = 18500;
            BorrowSaver.saveBorrow(db, symbol, currentBorrowable);

            System.out.println("🚀 Hello from Java - deployed in the cloud!");
        }
    }
}
