package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_wrongUnit_remainder {

    @Test
    public void test_from_wrongUnit_remainder() {
        Duration amountWithFractionalDays = Duration.ofHours(3);

        assertThrows(DateTimeException.class, () -> Days.from(amountWithFractionalDays));
    }
}
