//package ru.otus.hw.clients;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.otus.hw.BaseIntegrationTest;
//import ru.otus.hw.SpringBootApplicationTest;
//import ru.otus.hw.jms.JmsOrderMessage;
//
//import static ru.otus.hw.dictionaries.MessageType.CREATION;
//
//class RabbitMqConsumersTest extends BaseIntegrationTest {
//
//    private static final String ROUTING_KEY = "order.message.created";
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private RabbitAdmin rabbitAdmin;
//
//    @Autowired
//    private RabbitListenerEndpointRegistry endpointRegistry;
//
//    @BeforeEach
//    void setUp() {
//        rabbitAdmin.purgeQueue("order-messages", true);
//    }
//
//    @AfterEach
//    void cleanUp() {
//        rabbitAdmin.purgeQueue("order-messages", true);
//        endpointRegistry.stop();
//    }
//
//    @Test
//    void processImportantMessages() {
//        sendTestMessageToQueue();
//        startRabbitListener();
//    }
//
//    private void sendTestMessageToQueue() {
//        rabbitTemplate.convertAndSend(
//                ROUTING_KEY,
//                JmsOrderMessage.builder()
//                        .login("user")
//                        .messageType(CREATION)
//                        .bookTitle("test_title")
//                        .build()
//        );
//    }
//
//    private void startRabbitListener() {
//        endpointRegistry.getListenerContainer("update-book-edition-queue-listener").start();
//    }
//}