package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        // 1 способ запуска - анонимный класс имл. Thread и переопределили run
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + "," + getState()); // сокращенная запись.
            }
        };
        thread0.start();

        // 2 способ запуска (предпочтительный) - класс Thread созданный от Runnable
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()); // У runnable нет getName, поэтому полная запись
        }).start();
        System.out.println(thread0.getName() + "," + thread0.getState());

        // доступ к полю от одного потока
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 100; j++) {
                counter++;
            }
        }
        System.out.println(counter);

        // многопоток без синхронизации (т.к. ++ не атомарен, глючит)
        counter = 0;
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    counter++;
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(counter);

        // многопоток с синхронизацией по классу ( исправили на объекту Lock)
        counter = 0;
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    staticInc();
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(counter);

        // многопоток с синхронизацией по объекту (this)
        counter = 0;
        MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.notStaticInc();
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(counter);

        // многопоток с join - чтоб не спать неизвестно сколько.
        counter = 0;
        List<Thread> threads = new ArrayList<>();
        MainConcurrency mainConcurrency1 = new MainConcurrency();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency1.notStaticInc();
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(counter);
    }


    private static void staticInc() { // private static synchronized void inc() { - синхронизация по классу
        double a = Math.sin(13.0);
        synchronized (LOCK) { // синхронизация по объекту
            counter++;
        }
    }

    private void notStaticInc() {
        synchronized (this) {
            counter++;
        }
    }
}
