package ru.otus.hw.cofiguration;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.primary.Author;
import ru.otus.hw.models.primary.Book;
import ru.otus.hw.models.primary.Genre;
import ru.otus.hw.services.TransformationService;

@SuppressWarnings("unused")
@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 2;

    private static final int PAGE_SIZE = 2;

    private static final String MIGRATION_DATA_JOB_NAME = "migrationDataJob";

    private static final String SQL_QUERY_AUTHORS = "select * from authors";

    private static final String SQL_QUERY_GENRES = "select * from genres";

    private static final String SQL_QUERY_BOOK = "select * from books";

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private EntityManagerFactory primaryEntityManagerFactory;

    @Bean
    public JpaPagingItemReader<Author> jpaPagingAuthorItemReader12() throws Exception {

        JpaPagingItemReader<Author> readerAuthor = new JpaPagingItemReader<>();

        JpaNativeQueryProvider<Author> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(SQL_QUERY_AUTHORS);
        queryProvider.setEntityClass(Author.class);
        queryProvider.afterPropertiesSet();

        readerAuthor.setEntityManagerFactory(primaryEntityManagerFactory);
        readerAuthor.setPageSize(PAGE_SIZE);
        readerAuthor.setQueryProvider(queryProvider);
        readerAuthor.afterPropertiesSet();
        readerAuthor.setSaveState(true);

        return readerAuthor;
    }

    @Bean
    public JpaPagingItemReader<Genre> jpaPagingGenreItemReader() throws Exception {

        JpaPagingItemReader<Genre> readerGenre = new JpaPagingItemReader<>();

        JpaNativeQueryProvider<Genre> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(SQL_QUERY_GENRES);
        queryProvider.setEntityClass(Genre.class);
        queryProvider.afterPropertiesSet();

        readerGenre.setEntityManagerFactory(primaryEntityManagerFactory);
        readerGenre.setPageSize(PAGE_SIZE);
        readerGenre.setQueryProvider(queryProvider);
        readerGenre.afterPropertiesSet();
        readerGenre.setSaveState(true);

        return readerGenre;
    }

    @Bean
    public JpaPagingItemReader<Book> jpaPagingBookItemReader() throws Exception {

        JpaPagingItemReader<Book> readerBook = new JpaPagingItemReader<>();

        JpaNativeQueryProvider<Book> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(SQL_QUERY_BOOK);
        queryProvider.setEntityClass(Book.class);
        queryProvider.afterPropertiesSet();

        readerBook.setEntityManagerFactory(primaryEntityManagerFactory);
        readerBook.setPageSize(PAGE_SIZE);
        readerBook.setQueryProvider(queryProvider);
        readerBook.afterPropertiesSet();
        readerBook.setSaveState(true);

        return readerBook;
    }

    @Bean
    public ItemProcessor<Author, ru.otus.hw.models.secondary.Author> processorAuthor(
            TransformationService transformationService) {
        return transformationService::transformationAuthor;
    }

    @Bean
    public ItemProcessor<Genre, ru.otus.hw.models.secondary.Genre> processorGenre(
            TransformationService transformationService) {
        return transformationService::transformationGenre;
    }

    @Bean
    public ItemProcessor<Book, ru.otus.hw.models.secondary.Book> processorBook(
            TransformationService transformationService) {
        return transformationService::transformationBook;
    }

    @Bean
    public MongoItemWriter<ru.otus.hw.models.secondary.Author> writerAuthor(MongoTemplate secondaryMongoTemplate) {
        MongoItemWriter<ru.otus.hw.models.secondary.Author> writer = new MongoItemWriter<>();
        writer.setTemplate(secondaryMongoTemplate);
        return writer;
    }

    @Bean
    public MongoItemWriter<ru.otus.hw.models.secondary.Genre> writerGenre(MongoTemplate secondaryMongoTemplate) {
        MongoItemWriter<ru.otus.hw.models.secondary.Genre> writer = new MongoItemWriter<>();
        writer.setTemplate(secondaryMongoTemplate);
        return writer;
    }

    @Bean
    public MongoItemWriter<ru.otus.hw.models.secondary.Book> writerBook(MongoTemplate secondaryMongoTemplate) {
        MongoItemWriter<ru.otus.hw.models.secondary.Book> writer = new MongoItemWriter<>();
        writer.setTemplate(secondaryMongoTemplate);
        return writer;
    }

    @Bean
    public Job migrationDataJob(Step transformAuthorStep, Step transformGenreStep, Step transformBookStep) {
        return new JobBuilder(MIGRATION_DATA_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(splitFlow(transformAuthorStep, transformGenreStep))
                .next(transformBookStep)
                .end()
                .build();
    }

    @Bean
    public Flow splitFlow(Step transformAuthorStep, Step transformGenreStep) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(new SimpleAsyncTaskExecutor("spring_batch"))
                .add(flow1(transformAuthorStep), flow2(transformGenreStep))
                .build();
    }

    @Bean
    public Flow flow1(Step transformAuthorStep) {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(transformAuthorStep)
                .end();
    }

    @Bean
    public Flow flow2(Step transformGenreStep) {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(transformGenreStep)
                .build();
    }

    @Bean
    public Step transformAuthorStep(JpaPagingItemReader<Author> jpaPagingAuthorItemReader,
                                   MongoItemWriter<ru.otus.hw.models.secondary.Author> writerAuthor,
                                   ItemProcessor<Author, ru.otus.hw.models.secondary.Author> processorAuthor) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<Author, ru.otus.hw.models.secondary.Author>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(jpaPagingAuthorItemReader)
                .processor(processorAuthor)
                .writer(writerAuthor)
                .build();
    }

    @Bean
    public Step transformGenreStep(JpaPagingItemReader<Genre> jpaPagingGenreItemReader,
                                     MongoItemWriter<ru.otus.hw.models.secondary.Genre> writerGenre,
                                     ItemProcessor<Genre, ru.otus.hw.models.secondary.Genre> processorGenre) {
        return new StepBuilder("transformGenreStep", jobRepository)
                .<Genre, ru.otus.hw.models.secondary.Genre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(jpaPagingGenreItemReader)
                .processor(processorGenre)
                .writer(writerGenre)
                .build();
    }

    @Bean
    public Step transformBookStep(JpaPagingItemReader<Book> jpaPagingBookItemReader,
                                   MongoItemWriter<ru.otus.hw.models.secondary.Book> writerBook,
                                   ItemProcessor<Book, ru.otus.hw.models.secondary.Book> processorBook) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<Book, ru.otus.hw.models.secondary.Book>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(jpaPagingBookItemReader)
                .processor(processorBook)
                .writer(writerBook)
                .build();
    }
}
