package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Years fiveYears = Years.of(5);

        assertMultipliedBy(fiveYears, 0, Years.of(0));
        assertMultipliedBy(fiveYears, 1, Years.of(5));
        assertMultipliedBy(fiveYears, 2, Years.of(10));
        assertMultipliedBy(fiveYears, 3, Years.of(15));
        assertMultipliedBy(fiveYears, -3, Years.of(-15));
    }

    private static void assertMultipliedBy(Years base, int scalar, Years expected) {
        assertEquals(expected, base.multipliedBy(scalar));
    }
}
