package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/v1/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable long commentId) {
        return commentService.findById(commentId);
    }

    @GetMapping("/api/v1/comment/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentByBookId(@PathVariable("bookId") long bookId) {
        return commentService.findAllByBookId(bookId);
    }

    //todo: 2 контроллера на создание и редактирование

    @DeleteMapping("/api/v1/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") long commentId) {
        commentService.deleteById(commentId);
    }
}
