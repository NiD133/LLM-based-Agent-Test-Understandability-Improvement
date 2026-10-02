package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestHalf_test_interfaces {

    private static final Object[][] HALF_ROLLOVER_CASES = {
            {1, -4, 1},
            {1, -3, 2},
            {1, -2, 1},
            {1, -1, 2},
            {1, 0, 1},
            {1, 1, 2},
            {1, 2, 1},
            {1, 3, 2},
            {1, 4, 1},
    };

    public static Object[][] data_plus() {
        return HALF_ROLLOVER_CASES;
    }

    public static Object[][] data_minus() {
        return HALF_ROLLOVER_CASES;
    }

    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(Half.class));
        assertTrue(Serializable.class.isAssignableFrom(Half.class));
        assertTrue(Comparable.class.isAssignableFrom(Half.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(Half.class));
    }
}
