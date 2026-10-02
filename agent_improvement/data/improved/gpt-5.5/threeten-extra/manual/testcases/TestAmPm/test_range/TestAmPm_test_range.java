package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_range {

    @Test
    public void test_range() {
        ValueRange expectedAmPmRange = AMPM_OF_DAY.range();

        assertEquals(expectedAmPmRange, AmPm.AM.range(AMPM_OF_DAY));
    }
}
