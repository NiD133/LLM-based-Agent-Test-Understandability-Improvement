package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_range {

    // AmPm.range(AMPM_OF_DAY) must delegate to the field's own declared range (0–1),
    // since AmPm adds no additional constraint beyond what AMPM_OF_DAY already defines.
    @Test
    public void test_range() {
        ValueRange fieldRange = AMPM_OF_DAY.range();
        ValueRange amRange   = AmPm.AM.range(AMPM_OF_DAY);

        assertEquals(fieldRange, amRange);
    }
}
