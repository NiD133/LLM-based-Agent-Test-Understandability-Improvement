package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_plus_overflowTooBig {

    private static final long SECONDS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECONDS_PER_DAY * NANOS_PER_SECOND;

    @Test
    public void test_plus_overflowTooBig() {
        UtcInstant instantAtEndOfLastRepresentableDay =
                UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);

        assertThrows(
                ArithmeticException.class,
                () -> instantAtEndOfLastRepresentableDay.plus(Duration.ofNanos(1)));
    }
}
