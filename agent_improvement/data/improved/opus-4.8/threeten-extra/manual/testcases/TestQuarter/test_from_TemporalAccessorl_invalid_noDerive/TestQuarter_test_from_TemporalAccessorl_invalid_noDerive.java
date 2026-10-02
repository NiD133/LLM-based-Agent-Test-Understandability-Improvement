package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#from(java.time.temporal.TemporalAccessor)} rejects a
 * temporal object from which a quarter-of-year cannot be derived.
 */
public class TestQuarter_test_from_TemporalAccessorl_invalid_noDerive {

    @Test
    public void from_temporalWithoutDateInformation_throwsDateTimeException() {
        // A LocalTime carries only time-of-day, so no quarter can be derived from it.
        LocalTime timeWithoutDate = LocalTime.of(12, 30);

        assertThrows(DateTimeException.class, () -> Quarter.from(timeWithoutDate));
    }
}
