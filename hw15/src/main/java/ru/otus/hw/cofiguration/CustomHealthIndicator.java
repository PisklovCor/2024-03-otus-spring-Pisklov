package ru.otus.hw.cofiguration;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private static final LocalTime START_OF_THE_WORKING_DAY = LocalTime.of(9, 0 );
    private static final LocalTime END_OF_THE_WORKING_DAY = LocalTime.of(18, 0);

    @Override
    public Health health() {

        final LocalTime time = LocalTime.now();

        if (time.isAfter(START_OF_THE_WORKING_DAY) && time.isBefore(END_OF_THE_WORKING_DAY)) {
            return Health.up()
                    .withDetail("message", "The service is working")
                    .build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "The service is only available from 9:00 to 18:00")
                    .build();
        }
    }
}
