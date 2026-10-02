package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_get {

    @Test
    public void test_get() {
        assertEquals(0, AmPm.AM.get(AMPM_OF_DAY));
        assertEquals(1, AmPm.PM.get(AMPM_OF_DAY));
    }
}
