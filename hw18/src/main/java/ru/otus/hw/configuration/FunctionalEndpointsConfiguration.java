//package ru.otus.hw.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import ru.otus.hw.mappers.AuthorMapper;
//import ru.otus.hw.models.Author;
//import ru.otus.hw.repositories.AuthorRepository;
//import ru.otus.spring.domain.Person;
//import ru.otus.spring.repository.PersonRepository;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.web.reactive.function.BodyInserters.fromValue;
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
//import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//
//@Configuration
//@RequiredArgsConstructor
//public class FunctionalEndpointsConfiguration {
//
//    AuthorRepository repository;
//
//    private final AuthorMapper mapper;
//
//    @Bean
//    public RouterFunction<ServerResponse> composedRoutes(AuthorRepository repository) {
//        return route()
//                // эта функция должна стоять раньше findAll - порядок следования роутов - важен
//                .GET("/func/person",
//                        queryParam("name", StringUtils::isNotEmpty),
//                        request -> request.queryParam("name")
//                                .map(name -> ok().body(repository.findAllByLastName(name), Person.class))
//                                .orElse(badRequest().build())
//                )
//                // пример другой реализации - начиная с запроса репозитория
//                .GET("/func/person", queryParam("age", StringUtils::isNotEmpty),
//                        request ->
//                                ok()
//                                        .contentType(MediaType.APPLICATION_JSON)
//                                        .body(repository.findAll()
//                                                .map(Integer::parseInt)
//                                                .orElseThrow(IllegalArgumentException::new)), Person.class)
//                )
//                // Обратите внимание на использование хэндлера
//                .GET("/func/person", accept(APPLICATION_JSON), new PersonHandler(repository)::list)
//                // Обратите внимание на использование pathVariable
//                .GET("/func/person/{id}", accept(APPLICATION_JSON),
//                        request -> repository.findById(Long.parseLong(request.pathVariable("id")))
//                                .flatMap(person -> ok().contentType(APPLICATION_JSON).body(fromValue(person)))
//                                .switchIfEmpty(notFound().build())
//                ).build();
//    }
//
//    // Это пример хэндлера, который даже не бин
//    static class PersonHandler {
//
//        private final PersonRepository repository;
//
//        PersonHandler(PersonRepository repository) {
//            this.repository = repository;
//        }
//
//        Mono<ServerResponse> list(ServerRequest request) {
//            // Обратите внимание на пример другого порядка создания response от Flux
//            return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Person.class);
//        }
//    }
//
//
//    @Bean
//    public RouterFunction<ServerResponse> composedRoutesAuthor(AuthorRepository repository) {
//        return route()
//                // эта функция должна стоять раньше findAll - порядок следования роутов - важен
//
//
//                // Обратите внимание на использование хэндлера
//                .GET("/func/person", accept(APPLICATION_JSON), cast(repository.findAll()));
//    }
//
//
//    Mono<ServerResponse> cast(Flux<Author> flux) {
//        return ok().contentType(APPLICATION_JSON).body(flux, Author.class);
//    }
//}
