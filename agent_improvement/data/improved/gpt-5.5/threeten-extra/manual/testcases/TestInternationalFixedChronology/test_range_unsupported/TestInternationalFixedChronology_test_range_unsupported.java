package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_range_unsupported {

    @Test
    public void test_range_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> InternationalFixedDate.of(2012, 6, 28).range(MINUTE_OF_DAY));
    }
}
