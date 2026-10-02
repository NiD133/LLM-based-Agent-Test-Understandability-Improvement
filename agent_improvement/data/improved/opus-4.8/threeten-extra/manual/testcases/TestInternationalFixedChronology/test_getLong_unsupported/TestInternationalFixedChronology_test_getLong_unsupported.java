package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedDate#getLong} rejects time-based fields.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_getLong_unsupported {

    /**
     * An International Fixed date carries no time-of-day information, so querying a
     * time-based field such as MINUTE_OF_DAY must fail rather than return a value.
     */
    @Test
    public void test_getLong_unsupported() {
        InternationalFixedDate date = InternationalFixedDate.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.getLong(MINUTE_OF_DAY));
    }
}
