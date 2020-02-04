package com.example.lib;

import java.util.UUID;

public class MyClass {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid:" + uuid.toString());
        //int clock = uuid.clockSequence();
        //System.out.println("clock:"+clock);
        //long times = uuid.timestamp();
        //.out.println("times:"+times);
        int version = uuid.version();
        System.out.println("version:" + version);

        String userId = "7848916";
        UUID uuid1 = UUID.nameUUIDFromBytes(userId.getBytes());
        System.out.println("uuid1" + uuid1);
        int version1 = uuid1.version();
        System.out.println("version1:" + version1);

        String userId1 = "7848916" + System.currentTimeMillis();
        UUID uuid11 = UUID.nameUUIDFromBytes(userId1.getBytes());
        System.out.println("uuid1" + uuid11);
        int version11 = uuid1.version();
        System.out.println("version1:" + version11);

        String uuid2 = getUUID();
        System.out.println("uuid2:"+uuid2);
        try {
            testException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("testException");

    }



    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    private static void testException(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                throw  new NullPointerException();
            }
        };
        thread.start();
    }

}
