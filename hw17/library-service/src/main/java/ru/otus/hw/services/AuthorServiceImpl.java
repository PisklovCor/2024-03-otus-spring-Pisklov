package ru.otus.hw.services;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;

    @RateLimiter(name = "rateLimiter")
    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).toList();
    }
}
