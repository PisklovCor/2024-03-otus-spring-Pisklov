package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamsIOServiceTest {
    private IOService streamsIOService;

    @BeforeEach
    void setUp() {
        streamsIOService =  new StreamsIOService();
    }
    @DisplayName("корректно печатается 1 строка")
    @Test
    void printLine() throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));
        streamsIOService.printLine("did it!");
        bo.flush();
        String allWrittenLines = bo.toString();
        assertTrue(allWrittenLines.contains("did it!"));
    }
}