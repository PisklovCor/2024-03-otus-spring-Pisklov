package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final LocalizedMessagesService localizedMessagesServiceImpl;

    private final IOService streamsIOService;

    @Override
    public void printLine(String s) {
        streamsIOService.printLine(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        streamsIOService.printFormattedLine(s, args);
    }

    @Override
    public String readString() {
        return streamsIOService.readString();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        return streamsIOService.readStringWithPrompt(prompt);
    }

    @Override
    public int readIntForRange(int min, int max, String errorMessage) {
        return streamsIOService.readIntForRange(min, max, errorMessage);
    }

    @Override
    public int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage) {
        return streamsIOService.readIntForRangeWithPrompt(min, max, prompt, errorMessage);
    }

    @Override
    public void printLineLocalized(String code) {
        streamsIOService.printLine(localizedMessagesServiceImpl.getMessage(code));
    }

    @Override
    public void printFormattedLineLocalized(String code, Object... args) {
        streamsIOService.printLine(localizedMessagesServiceImpl.getMessage(code, args));
    }

    @Override
    public String readStringWithPromptLocalized(String promptCode) {
        return streamsIOService.readStringWithPrompt(localizedMessagesServiceImpl.getMessage(promptCode));
    }

    @Override
    public int readIntForRangeLocalized(int min, int max, String errorMessageCode) {
        return streamsIOService.readIntForRange(min, max, localizedMessagesServiceImpl.getMessage(errorMessageCode));
    }

    @Override
    public int readIntForRangeWithPromptLocalized(int min, int max, String promptCode, String errorMessageCode) {
        return streamsIOService.readIntForRangeWithPrompt(min, max,
                localizedMessagesServiceImpl.getMessage(promptCode),
                localizedMessagesServiceImpl.getMessage(errorMessageCode)
        );
    }

    @Override
    public String getMessage(String code, Object... args) {
        return localizedMessagesServiceImpl.getMessage(code, args);
    }
}
