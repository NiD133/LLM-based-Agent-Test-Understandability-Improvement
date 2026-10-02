package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_Duration {

    @Test
    public void test_plus_TemporalAmount_Duration() {
        Years oneYear = Years.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneYear.plus(twoHours));
    }
}
