package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/")
    public String listBook(Model model) {
        model.addAttribute("keywords", "Library catalog");
        return "list";
    }

    @GetMapping("/book/edit")
    public String editBook(@RequestParam("id") long id) {
        return "edit";
    }

    @GetMapping("/book/create")
    public String createBook() {
        return "create";
    }
}
