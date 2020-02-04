package com.example.lib;

import java.util.concurrent.locks.ReentrantLock;

public class LockFairTest {
    private static ReentrantLock reentrantReadWriteLock = new ReentrantLock(true);

    public static int count = 0;


    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Thread thread = null;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread = new Thread("" + i) {
                @Override
                public void run() {
                    super.run();
                    System.out.println("threadNamae:" + Thread.currentThread().getName() + "----------->start");
                    reentrantReadWriteLock.lock();
                    System.out.println("threadNamae:" + Thread.currentThread().getName() + "get lock");
                    reentrantReadWriteLock.unlock();

                }
            };
            threads[i] = thread;
        }

        for(Thread thread1:threads){
            thread1.start();
        }

    }


}
