package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#from(java.time.temporal.TemporalAccessor)} rejects a
 * temporal that carries no half-of-year information and cannot be converted to one.
 */
public class TestHalf_test_from_TemporalAccessorl_invalid_noDerive {

    @Test
    public void from_withTimeOnlyTemporal_throwsDateTimeException() {
        // A LocalTime has no date component, so no half-of-year can be derived from it.
        LocalTime timeWithoutDate = LocalTime.of(12, 30);

        assertThrows(DateTimeException.class, () -> Half.from(timeWithoutDate));
    }
}
