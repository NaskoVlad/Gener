package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger number1 = new AtomicInteger();
    static AtomicInteger number2 = new AtomicInteger();
    static AtomicInteger number3 = new AtomicInteger();

    public static void main(String[] args) {
        String abc = "abc";
        System.out.println("Hello world!");

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        for (int i = 0; i < texts.length; i++) {
            String name = texts[i];
            new Thread(() -> {
                for (int j = 0; j < name.length() / 2; j++) {
                    if (name.charAt(j) == name.charAt(name.length() - 1 - j)) {
                        if (j + 1 == name.length() / 2) {
                            summa(name);
                        }
                    } else {
                        return;
                    }
                }
            }).start();

            new Thread(() -> {
                for (int j = 1; j < name.length(); j++) {
                    if (name.charAt(0) == name.charAt(j)) {
                        if (j + 1 == name.length()) {
                            summa(name);
                        }
                    } else {
                        return;
                    }
                }
            }).start();

            new Thread(() -> {
                int index = abc.indexOf(name.charAt(0));
                for (int j = 1; j < name.length() - 1; j++) {
                    if (name.charAt(j) == abc.charAt(index)) {
                        if (j + 1 == name.length()) {
                            summa(name);
                            number3.getAndIncrement();
                        }
                    } else if (name.charAt(j + 1) == abc.charAt(index)) {
                        j++;
                        if (j + 1 == name.length()) {
                            summa(name);
                        }
                    } else {
                        if (index + 1 == abc.length()) {
                            return;
                        } else {
                            index++;
                        }
                    }
                }
            }).start();
        }
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
}