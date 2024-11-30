package ru.otus.hw.handlers;

//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.http.HttpStatus;
//
//import org.springframework.stereotype.Service;
//
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//import ru.otus.hw.dto.BookCreateDto;
//import ru.otus.hw.dto.BookDto;
//import ru.otus.hw.exceptions.NotFoundException;
//import ru.otus.hw.mappers.BookMapper;
//import ru.otus.hw.models.Author;
//
//import ru.otus.hw.models.Genre;
//import ru.otus.hw.repositories.AuthorRepository;
//import ru.otus.hw.repositories.BookRepository;
//import ru.otus.hw.repositories.GenreRepository;
//
//import java.util.List;


//@Slf4j
//@Service
//@RequiredArgsConstructor
public class BookHandler {

//    private final AuthorRepository authorRepository;
//
//    private final GenreRepository genreRepository;
//
//    private final BookRepository bookRepository;
//
//    private final BookMapper mapper;
//
//    public Mono<ServerResponse> createBook(ServerRequest request) {
//        Mono<BookCreateDto> book = request.bodyToMono(BookCreateDto.class);
//        Mono<Author> author = book.flatMap(dto -> authorRepository.findById(dto.getAuthorId())
//                .switchIfEmpty(Mono.create(emitter -> emitter.error(
//                        new NotFoundException("Author with id %s not found".formatted(dto.getAuthorId()))))));
////        Mono<List<Genre>> genres = book.flatMap(dto -> genreRepository.findAllById(dto.getGenresId())
////                .collectList()
////                .flatMap(list -> {
////                    if (list.size() != dto.getGenresId().size()) {
////                        return Mono.create(emitter -> emitter.error(
////                                new NotFoundException("Not all genres found from list %s"
////                                        .formatted(dto.getGenresId()))));
////                    } else {
////                        return Mono.just(list);
////                    }
////                }));
////        log.info(author.toString());
////        log.info(genres.toString());
////        var result =  Mono.zip(book, author, genres)
////                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
////                .flatMap(bookRepository::save)
////                .map(mapper::toDto);
////        return ServerResponse.status(HttpStatus.CREATED)
////                .contentType(MediaType.APPLICATION_JSON)
////                .body(result, BookDto.class);
//        return result.flatMap(r -> ServerResponse.status(HttpStatus.OK).body(r, BookDto.class));
//    }
//
//    public Mono<ServerResponse> updateBook(ServerRequest request) {
//        return Mono.empty();
//    }
}
