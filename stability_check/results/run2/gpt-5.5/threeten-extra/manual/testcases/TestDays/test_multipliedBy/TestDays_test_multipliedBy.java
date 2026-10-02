package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Days fiveDays = Days.of(5);

        assertMultipliedBy(fiveDays, 0, 0);
        assertMultipliedBy(fiveDays, 1, 5);
        assertMultipliedBy(fiveDays, 2, 10);
        assertMultipliedBy(fiveDays, 3, 15);
        assertMultipliedBy(fiveDays, -3, -15);
    }

    private void assertMultipliedBy(Days baseDays, int scalar, int expectedDays) {
        assertEquals(Days.of(expectedDays), baseDays.multipliedBy(scalar));
    }
}
