//package ru.otus.hw;
//
////import org.springframework.amqp.rabbit.core.RabbitAdmin;
////import org.springframework.amqp.rabbit.core.RabbitTemplate;
////import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
////import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
////import org.springframework.beans.factory.annotation.Autowired;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.TestPropertySource;
//import org.testcontainers.containers.RabbitMQContainer;
//import org.testcontainers.containers.output.Slf4jLogConsumer;
//import org.testcontainers.junit.jupiter.Container;
//
//import static org.reflections.Reflections.log;
//
//@SpringRabbitTest
//@RabbitListenerTest
//@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
//public class BaseIntegrationTest extends SpringBootApplicationTest {
//
//    private static final String MAIN_EXCHANGE_NAME = "main-exchange-order";
//
////
////    @Autowired
////    private RabbitTemplate template;
////
////    @Autowired
////    private RabbitAdmin admin;
////
////    @Autowired
////    private RabbitListenerEndpointRegistry registry;
//
//    @Container
//    public static RabbitMQContainer rabbitMQContainer =
//            new RabbitMQContainer("rabbitmq:3-management")
//                    .withCreateContainerCmdModifier(cmd -> cmd.withName("RabbitMQ-Tester"))
//                    .withExposedPorts(5672, 15672)
//                    .withEnv("RABBITMQ_NODE_PORT", "5672")
//                    .withLogConsumer(new Slf4jLogConsumer(log));
//
//
//    @TestConfiguration
//    public static class Config {
//
//        @Bean
//        public Jackson2JsonMessageConverter jsonConverter(ObjectMapper objectMapper) {
//            return new Jackson2JsonMessageConverter(objectMapper);
//        }
//
//        @Bean
//        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
//                                             Jackson2JsonMessageConverter jsonConverter) {
//            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//            rabbitTemplate.setExchange(MAIN_EXCHANGE_NAME);
//            rabbitTemplate.setMessageConverter(jsonConverter);
//            return rabbitTemplate;
//        }
//
//    }
//
//}
