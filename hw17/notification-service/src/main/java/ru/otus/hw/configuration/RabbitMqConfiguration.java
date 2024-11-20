package ru.otus.hw.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {


    public static final String BASE_ROUTING_KEY = "order.message.";

    public static final String DEAD_LETTER_QUEUE = "dead-letter-queue";

    public static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";

    private static final String MAIN_EXCHANGE_NAME = "main-exchange-order";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MAIN_EXCHANGE_NAME);
    }

//    @Bean
//    public FanoutExchange deadLetterExchange() {
//        return new FanoutExchange(DEAD_LETTER_EXCHANGE);
//    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue allOrderMessagesQueue() {
        return new Queue("order-messages");
    }

    @Bean
    public Binding allActivityBinding() {
        return BindingBuilder.bind(allOrderMessagesQueue())
                .to(topicExchange())
                .with(BASE_ROUTING_KEY + "*");
    }

//    @Bean
//    public Queue statCalcCommandsQueue() {
//        return QueueBuilder.durable("stat-calc-commands-queue")
//                .maxLength(5)
//                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
//                .build();
//    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }



//    @Bean
//    public Binding deadLetterBinding() {
//        return BindingBuilder.bind(deadLetterQueue())
//                .to(deadLetterExchange());
//    }
}
