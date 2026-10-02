package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_Duration {

    /**
     * Subtracting a {@link Duration} from {@link Weeks} must fail: a duration of
     * hours cannot be converted to a whole number of weeks, so {@code minus}
     * throws a {@link DateTimeException}.
     */
    @Test
    public void test_minus_TemporalAmount_Duration() {
        Weeks oneWeek = Weeks.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneWeek.minus(twoHours));
    }
}
