package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static final Lock LOCK = new ReentrantLock();
    //    private int counter;
    private AtomicInteger atomicCounter = new AtomicInteger();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat());
//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // предыдущие варианты см в коммитах Lesson 11.

        /*
        // Работа с защелкой из пакета concurrent.
        // Ждем, пока счетчик защелки не станет равным нулю - т.е. пока не отработают все потоки,
        // но не дольше 10 секунд.
        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });
            thread.start();
            threads.add(thread);
        }

        latch.await(10, TimeUnit.SECONDS);
        System.out.println(mainConcurrency.counter);
        */

        // Работа с Executors
        // Лучше определять, сколько потоков создавать, в зависимости от доступного числа ядер,
        // для предсказуемости поведения.
        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(processorsNumber);
//        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
//                    sdf.format(new Date()); // плохо, из-за использования непотокобезопасного объекта
//                   Лучше использовать свой экземпляр для каждого потока, но создавать дорого.
//                  или использовать DateTimeFormatter (потокобезопасен) или ThreadLocal.
                    System.out.println(threadLocal.get().format(new Date()));

                }
                latch.countDown();
                return 1; // меняем Runnable на Callable, вернет Future (отложенный результат)
            });
//            System.out.println(future.isDone() + " " + future.get() + " " + future.isDone());
        }

        // если не ожидать, то часть нитей не успеет запуститься - у нас всего 4 нити макс.
        latch.await(10, TimeUnit.SECONDS);

        // если executorService не погасить, программа останется работать.
        // shutdown vs shutdownNow:
        // shutdown позволяет завершиться уже запущеным задачам.
        // shutdownNow пытается их прервать.
        executorService.shutdown();

        System.out.println(mainConcurrency.atomicCounter);
    }

    private void inc() { // synchronized
        atomicCounter.incrementAndGet();
        /*
        LOCK.lock();
        try {
            counter++;
        } finally {
            LOCK.unlock();
        }
        */
    }
}
