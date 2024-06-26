package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.h2.jdbc.JdbcResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final GenreRepository genreRepository;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return Optional.ofNullable(jdbc.query(
                "select b.id as b_id, b.title as b_tt," +
                        " a.id as a_id, a.full_name as a_fn, g.id as g_id, g.name as g_name" +
                        " from books b" +
                        " left join authors a on a.id = b.author_id" +
                        " left join books_genres bg on bg.book_id = b.id" +
                        " left join genres g on g.id = bg.genre_id" +
                        " where b.id = :id", params, new BookResultSetExtractor()));
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbc.query(
                "select b.id as b_id, b.title as b_tt," +
                        " a.id as a_id, a.full_name as a_fn" +
                        " from books b left join authors a on a.id = b.author_id", new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("select book_id, genre_id from books_genres",
                new DataClassRowMapper<>(BookGenreRelation .class));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {

        for (Book book : booksWithoutGenres) {
            var bookGenreRelationIds = relations.stream()
                    .filter(r -> r.bookId() == book.getId()).map(r -> r.genreId).toList();
            book.setGenres(genres.stream().filter(g -> bookGenreRelationIds.contains(g.getId())).toList());
        }

    }

    @SuppressWarnings("DataFlowIssue")
    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        jdbc.update("insert into books (title, author_id) values (:title, :author_id)"
                , params, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        int result = jdbc.update("update books set title = :title, author_id = :author_id where id = :id", params);

        if (result < 1) {
            throw new EntityNotFoundException("The book is not updated");
        }

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {

        final long bookId = book.getId();
        List<Genre> genres = book.getGenres();

        final String query = "insert into books_genres (book_id, genre_id) values (%s, :id)";

        jdbc.batchUpdate(String.format(query, bookId), SqlParameterSourceUtils.createBatch(genres));
    }

    private void removeGenresRelationsFor(Book book) {
        Map<String, Object> params = Collections.singletonMap("book_id", book.getId());
        jdbc.update("delete from books_genres where book_id = :book_id", params);
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            Author author = new Author();
            author.setId(resultSet.getLong("a_id"));
            author.setFullName(resultSet.getString("a_fn"));

            Book book = new Book();
            book.setId(resultSet.getLong("b_id"));
            book.setTitle(resultSet.getString("b_tt"));
            book.setAuthor(author);

            return book;
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {

            if (((JdbcResultSet) resultSet).getResult().getRowCount() < 1) {
                return null;
            }

            Book book = new Book();
            Author author = new Author();
            List<Genre> genreList = new ArrayList<>();

            while (resultSet.next()) {
                author.setId(resultSet.getLong("a_id"));
                author.setFullName(resultSet.getString("a_fn"));
                book.setId(resultSet.getLong("b_id"));
                book.setTitle(resultSet.getString("b_tt"));
                genreList.add(new Genre(resultSet.getLong("g_id"), resultSet.getString("g_name")));
            }

            book.setAuthor(author);
            book.setGenres(genreList);

            return book;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
