package ru.javawebinar.basejava;

import java.util.concurrent.CyclicBarrier;

public class MainDeadLock {
    private static CyclicBarrier barrier = new CyclicBarrier(2);

    public static void main(String[] args) {
        Object objectA = new Object();
        Object objectB = new Object();
        new Thread(() -> deadlock(objectA, objectB)).start();
        new Thread(() -> deadlock(objectB, objectA)).start();
    }

    private static void deadlock(Object o1, Object o2) {
        synchronized (o1) {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println(Thread.currentThread().getName() + "- no deadlock");
            }
        }
    }
}
