package com.ahalikov.logdemo.asynclogger;

import java.util.Random;

/**
 * Application main class
 *
 * @author ahalikov
 */
class App {
    static final LogWorker logWorker = new LogWorker();
    static final Random random = new Random();

    public static void main(String[] args) {
        Thread[] t = new Thread[10];
        for (int i = 0; i < 10; i++) {
            (t[i] = new Thread(() -> {
                logWorker.log("hello world " + random.nextInt(10));
            })).start();
        }
    }
}
