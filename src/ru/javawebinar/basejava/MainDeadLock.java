package ru.javawebinar.basejava;

public class MainDeadLock {

    public static void main(String[] args) {
        Object objectA = new Object();
        Object objectB = new Object();
        new Thread(() -> deadlock(objectA, objectB)).start();
        new Thread(() -> deadlock(objectB, objectA)).start();
    }

    private static void deadlock(Object o1, Object o2) {
        String threadName = Thread.currentThread().getName();
        synchronized (o1) {
            System.out.println(threadName + " захватил " + o1);
            System.out.println(threadName + " ждет захвата объекта " + o2);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println(threadName + " захватил " + o2);
            }
        }
    }
}
