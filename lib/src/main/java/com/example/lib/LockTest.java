package com.example.lib;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);

    private static Lock w = reentrantReadWriteLock.writeLock();
    private static Lock r = reentrantReadWriteLock.readLock();
    public static int count = 0;


    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Thread[] threads = new Thread[10];
        Thread thread = null;
        for (int i = 0; i < 10; i++) {
           if(i%3==0){
               thread = createReadThread(i);
           }else{
               thread = createWriteThread(i);
           }

           threads[i] = thread;
        }

        for(Thread thread1 : threads){
            thread1.start();
        }
    }

    public static Thread createReadThread(int i){
        return new Thread("" + i) {
            @Override
            public void run() {
                System.out.println("read" + Thread.currentThread().getName());
                super.run();
                r.lock();
                System.out.println("read get ------------>lock " + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("threadNamae:" + Thread.currentThread().getName() + "read count:" + count);
                r.unlock();

            }
        };

    }


    public static Thread createWriteThread(int i){
       return new Thread("" + i) {
            @Override
            public void run() {
                System.out.println("write" + Thread.currentThread().getName());
                super.run();
                w.lock();
                System.out.println("write get ------------>lock" + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("threadNamae:" + Thread.currentThread().getName() + "write count:" + count++);
                w.unlock();

            }
        };
    }


}
