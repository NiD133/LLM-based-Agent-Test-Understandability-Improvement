package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_with_TemporalField_unsupported {

    @Test
    public void test_with_TemporalField_unsupported() {
        // MINUTE_OF_DAY is a time-based field; InternationalFixedDate only supports date fields
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> InternationalFixedDate.of(2012, 6, 28).with(MINUTE_OF_DAY, 0));
    }
}
