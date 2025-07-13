package com.example;

import com.google.cloud.firestore.Firestore;

import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException, IOException {

        Firestore database = FirestoreInit.initialize();
        // Keep app alive
        while (true) {
            Thread.sleep(2000); // Sleep 1 giây
            BorrowSaver.saveBorrow(database);

            //Load data
//            BorrowSaver.loadBorrowed(database);

            System.out.println("🚀 Hello from Java - deployed in the cloud!");
        }
    }
}
