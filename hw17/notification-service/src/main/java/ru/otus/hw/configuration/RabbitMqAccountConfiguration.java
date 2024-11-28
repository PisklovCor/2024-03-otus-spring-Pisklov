package ru.otus.hw.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqAccountConfiguration {

    private static final String ORDER_MAIN_EXCHANGE_NAME = "main-exchange-order";

    private static final String ACCOUNT_MAIN_EXCHANGE_NAME = "main-exchange-account";

    private static final String LIBRARY_MAIN_EXCHANGE_NAME = "main-exchange-library";

    private static final String DEAD_LETTER_EXCHANGE_NAME = "dead-letter-exchange";

    private static final String ORDER_BASE_ROUTING_KEY = "order.message.";

    private static final String ACCOUNT_BASE_ROUTING_KEY = "account.message.";

    private static final String LIBRARY_BASE_ROUTING_KEY = "library.message.";

    private static final String DEAD_LETTER_QUEUE = "dead-letter-queue";


    @Bean
    public TopicExchange topicOrderExchange() {
        return new TopicExchange(ORDER_MAIN_EXCHANGE_NAME);
    }

    @Bean
    public TopicExchange topicAccountExchange() {
        return new TopicExchange(ACCOUNT_MAIN_EXCHANGE_NAME);
    }

    @Bean
    public TopicExchange topicLibraryExchange() {
        return new TopicExchange(LIBRARY_MAIN_EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    @Bean
    public Queue allMessagesOrderServiceQueue() {
        return new Queue("order-messages");
    }

    @Bean
    public Binding orderMessagesBinding() {
        return BindingBuilder.bind(allMessagesOrderServiceQueue())
                .to(topicOrderExchange())
                .with(ORDER_BASE_ROUTING_KEY + "*");
    }

    @Bean
    public Queue allMessagesAccountServiceQueue() {
        return new Queue("account-messages");
    }

    @Bean
    public Binding accountMessagesBinding() {
        return BindingBuilder.bind(allMessagesAccountServiceQueue())
                .to(topicAccountExchange())
                .with(ACCOUNT_BASE_ROUTING_KEY + "*");
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DEAD_LETTER_EXCHANGE_NAME);
    }

    @Bean
    public Queue allMessagesLibraryServiceQueue() {
        return QueueBuilder.durable("library-messages")
                .maxLength(5L)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Binding libraryMessagesBinding() {
        return BindingBuilder.bind(allMessagesLibraryServiceQueue())
                .to(topicLibraryExchange())
                .with(LIBRARY_BASE_ROUTING_KEY + "*");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange());
    }
}
