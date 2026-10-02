package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#range(java.time.temporal.TemporalField)}.
 */
public class TestAmPm_test_range {

    /**
     * For the supported AMPM_OF_DAY field, range(...) should return the
     * field's own range (0 to 1).
     */
    @Test
    public void test_range_returnsFieldRangeForAmPmOfDay() {
        assertEquals(AMPM_OF_DAY.range(), AmPm.AM.range(AMPM_OF_DAY));
    }
}
