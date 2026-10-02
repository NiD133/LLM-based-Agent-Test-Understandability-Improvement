package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_Duration {

    @Test
    public void test_plus_TemporalAmount_Duration() {
        Weeks oneWeek = Weeks.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneWeek.plus(twoHours));
    }
}
