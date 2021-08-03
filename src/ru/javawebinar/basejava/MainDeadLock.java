package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) throws InterruptedException {
        Account accountA = new Account("A", 100);
        Account accountB = new Account("B", 200);

        Thread successful = new Thread(new BadTransfer(accountA, accountB, 20));
        successful.start();
        successful.join();

        Thread fromAToB = new Thread(new BadTransfer(accountA, accountB, 20));
        Thread fromBToA = new Thread(new BadTransfer(accountB, accountA, 35));
        fromAToB.start();
        fromBToA.start();
        fromAToB.join();
        fromBToA.join();

        System.out.println("If you see it - No deadlock!!!!");
    }

    public static class Account {
        private String name;
        private int money;

        public Account(String name, int money) {
            this.name = name;
            this.money = money;
        }
    }

    public static class BadTransfer implements Runnable {
        private Account source;
        private Account destination;
        private int transfer;

        public BadTransfer(Account source, Account destination, int transfer) {
            this.source = source;
            this.destination = destination;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.printf("%s - start transfer %s$ from %s to %s%n", threadName, transfer, source.name, destination.name);
            synchronized (source) {
                delay();
                synchronized (destination) {
                    source.money -= transfer;
                    destination.money += transfer;
                }
            }
            System.out.printf("%s - transfer %s$ from %s to %s is successful!%n%n", threadName, transfer, source.name, destination.name);
        }

        private void delay() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
