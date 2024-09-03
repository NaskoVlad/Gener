package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger number1 = new AtomicInteger(0);
    static AtomicInteger number2 = new AtomicInteger(0);
    static AtomicInteger number3 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        String abc = "abc";

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText(abc, 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String line : texts) {
                if (palindrome(line)) {
                    summa(line);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String line : texts) {
                if (letterOne(line)) {
                    summa(line);
                }
            }
        });
        thread2.start();


        Thread thread3 = new Thread(() -> {
            for (String line : texts) {
                if (increase(line)) {
                    summa(line);
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();


        System.out.println("Красивых слов с длиной 3: " + number1 + " шт");
        System.out.println("Красивых слов с длиной 4: " + number2 + " шт");
        System.out.println("Красивых слов с длиной 5: " + number3 + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void summa(String name) {
        int n3 = 3;
        int n4 = 4;
        int n5 = 5;

        if (name.length() == n3) {
            number1.getAndIncrement();
        } else if (name.length() == n4) {
            number2.getAndIncrement();
        } else if (name.length() == n5) {
            number3.getAndIncrement();
        }
    }

    public static boolean palindrome(String text) {
        int number = text.length();
        char beginning;
        char end;
        boolean result = false;

        for (int i = 0; i < (number / 2); i++) {
            beginning = text.charAt(i);
            end = text.charAt(number - i - 1);
            if (beginning != end) {
                result = false;
                break;
            }
            if (i == (number / 2) - 1) {
                result = true;
            }
        }
        return result;
    }

    public static boolean letterOne(String text) {
        boolean result = false;
        char symbol = text.charAt(0);
        char variable;

        for (int i = 1; i < text.length(); i++) {
            variable = text.charAt(i);
            if (symbol != variable) {
                result = false;
                break;
            }
            if (i == text.length() - 1) {
                result = true;
            }
        }
        return result;
    }

    public static boolean increase(String text) {
        boolean result = false;
        char beginning = text.charAt(0);
        char variable;

        for (int i = 1; i < text.length(); i++) {
            variable = text.charAt(i);
            if (variable < beginning) {
                result = false;
                break;
            } else {
                beginning = variable;
            }
            if (i == text.length() - 1) {
                result = true;
            }
        }
        return result;
    }
}