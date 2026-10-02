package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Seconds fiveSeconds = Seconds.of(5);

        assertMultipliedBy(fiveSeconds, 0, 0);
        assertMultipliedBy(fiveSeconds, 1, 5);
        assertMultipliedBy(fiveSeconds, 2, 10);
        assertMultipliedBy(fiveSeconds, 3, 15);
        assertMultipliedBy(fiveSeconds, -3, -15);
    }

    private void assertMultipliedBy(Seconds base, int scalar, int expectedSeconds) {
        assertEquals(Seconds.of(expectedSeconds), base.multipliedBy(scalar));
    }
}
