package ru.javawebinar.basejava.util;

public class LazySingleton {
    private LazySingleton() {
    }

    //    не ленивая инициализация + нет обработки исключений при вызове конструктора.
//    private static final LazySingleton INSTANCE = new LazySingleton();
//    public static LazySingleton getInstance() { return INSTANCE; }

    volatile private static LazySingleton INSTANCE;
    double sin = Math.sin(13.);
    int i;

/*  // Ленивая инициализация с синхронайзед - медленно.
    // без синхронайзед - не работает в многопоточке
    public static synchronized LazySingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingleton();
        }
        return INSTANCE;
    }*/

    // Ленивая инициализация с даблчеклокинг
    //- не совсем рабочая  - реордеринг - возможность JVM переставить инструкции.
    //- могла вернуться ссылка на уже созданный, но еще не проинициализированный объект
    // решение - объявить INSTANCE как volatile (ввели в Java 1.5), но опять же медленно
    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }

        }
        return INSTANCE;
    }

    // Еще вариант - с внутренним классом - initialization-on-Demand Holder
    //- держателем синглтона (не будет инициализирован пока не будет вызван)
    // работает потому что загрузка классов в JVM - ленивая, только когда класс встречается в коде.
    // public class Singleton {
    //   private static class SingletonHolder{
    //      private final static Singeton instance = new Singleton(); }
    // public static Singleton getInstance() { return SingletonHolder.instance;}


    // и вариант реализовать как Enum:
    // enum Singleton{
    // INSTANCE;
    // public static Singleton getInstance() {return INSTANCE;}}

}
