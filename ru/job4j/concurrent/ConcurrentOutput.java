package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        System.out.println(Thread.currentThread().getName());
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        System.out.println(Thread.currentThread().getName());
        Thread third = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        third.start();
        System.out.println(Thread.currentThread().getName());
        Thread noName = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        noName.start();
        System.out.println(Thread.currentThread().getName());
    }
}