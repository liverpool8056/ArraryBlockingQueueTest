package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public void get(ArrayBlockingQueue<Integer> blockingQueue) throws InterruptedException {
        Integer i =  blockingQueue.poll(1, TimeUnit.HOURS);
        System.out.println("get: " + i);
    }

    public void size(ArrayBlockingQueue<Integer> blockingQueue) {
        int size = blockingQueue.size();
        System.out.println("size: " + size);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        System.out.println("----------------Here we go----------------");
        new Thread(() -> {
            try {
                get(blockingQueue);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> size(blockingQueue)).start();

        System.out.println("----------------Finish----------------");
    }


}
