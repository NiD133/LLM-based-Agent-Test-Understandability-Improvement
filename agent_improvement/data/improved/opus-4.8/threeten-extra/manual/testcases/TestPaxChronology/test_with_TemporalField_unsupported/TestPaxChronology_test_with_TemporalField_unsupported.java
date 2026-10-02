package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link PaxDate} with a time-based field is rejected.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_with_TemporalField_unsupported {

    /**
     * A PaxDate carries no time-of-day information, so {@code with(MINUTE_OF_DAY, ...)}
     * must fail rather than silently ignore the unsupported field.
     */
    @Test
    public void with_timeBasedField_throwsUnsupportedTemporalType() {
        PaxDate date = PaxDate.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class,
                () -> date.with(MINUTE_OF_DAY, 0));
    }
}
