package ru.otus.hw.cofiguration;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Конфигурация которая позволяется подключать IDE к H2, при запущенном приложение
 */
@Configuration
public class H2TcpConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9092");
    }
}
