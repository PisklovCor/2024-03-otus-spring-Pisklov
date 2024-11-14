package ru.otus.hw.services;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreMapper genreMapper;

    private final GenreRepository genreRepository;

    @RateLimiter(name = "rateLimiter")
    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).toList();
    }
}
