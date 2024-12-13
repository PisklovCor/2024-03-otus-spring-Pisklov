package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.hw.handlers.BookHandler;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Пример описания RouterFunction
 * <a href="https://habr.com/ru/articles/565056">Реактивное программирование со Spring, часть 3 WebFlux</a>
 */
@Configuration
@RequiredArgsConstructor
public class FunctionalBookEndpointsConfiguration {

    @Bean
    public RouterFunction<ServerResponse> composedBookRoutes(BookRepository repository, BookMapper mapper,
                                                             BookHandler handler) {
        return route()
                .GET("/route/v1/book/{bookId}", accept(APPLICATION_JSON),
                        request -> repository.findById(request.pathVariable("bookId"))
                                .flatMap(b -> ok().contentType(APPLICATION_JSON).body(fromValue(mapper.toDto(b))))
                                .switchIfEmpty(notFound().build())
                )
                .POST("/route/v1/book", accept(APPLICATION_JSON), handler::createBook)
                .PUT("/route/v1/book", accept(APPLICATION_JSON), handler::updateBook)
                .DELETE("/route/v1/book/{bookId}", accept(APPLICATION_JSON),
                        request -> repository.findById(request.pathVariable("bookId"))
                                .flatMap(b -> ServerResponse.noContent().build(repository.delete(b)))
                                .switchIfEmpty(notFound().build())
                )
                .build();
    }
}