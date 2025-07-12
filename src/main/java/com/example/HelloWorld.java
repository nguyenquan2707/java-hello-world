package com.example;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("🚀 Hello from Java - deployed in the cloud!");

        // Keep app alive
        while (true) {
            Thread.sleep(1000); // Sleep 1 giây
        }
    }
}
