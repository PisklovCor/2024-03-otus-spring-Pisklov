package ru.otus.hw.service;

public class StreamsIOService implements IOService {

    @Override
    public void printLine(String s) {
        System.out.println(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append(s);

        for (Object object : args) {
            stringBuilder.append("\n").append(object.toString());
        }

        System.out.println(stringBuilder);
    }
}
