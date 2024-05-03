package ru.otus.hw.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AppProperties implements TestFileNameProvider {

    private String testFileName;
}
