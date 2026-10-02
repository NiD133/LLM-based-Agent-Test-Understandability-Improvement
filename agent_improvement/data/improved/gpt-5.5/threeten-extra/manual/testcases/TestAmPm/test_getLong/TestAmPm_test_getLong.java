package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_getLong {

    @Test
    public void test_getLong() {
        assertEquals(0, AmPm.AM.getLong(AMPM_OF_DAY));
        assertEquals(1, AmPm.PM.getLong(AMPM_OF_DAY));
    }
}
