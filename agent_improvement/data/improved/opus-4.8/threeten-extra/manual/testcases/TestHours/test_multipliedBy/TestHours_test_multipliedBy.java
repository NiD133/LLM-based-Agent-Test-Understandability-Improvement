package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#multipliedBy(int)}.
 */
public class TestHours_test_multipliedBy {

    @Test
    public void multipliedBy_scalesTheNumberOfHours() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(0), fiveHours.multipliedBy(0), "5 hours x 0 = 0 hours");
        assertEquals(Hours.of(5), fiveHours.multipliedBy(1), "5 hours x 1 = 5 hours");
        assertEquals(Hours.of(10), fiveHours.multipliedBy(2), "5 hours x 2 = 10 hours");
        assertEquals(Hours.of(15), fiveHours.multipliedBy(3), "5 hours x 3 = 15 hours");
        assertEquals(Hours.of(-15), fiveHours.multipliedBy(-3), "5 hours x -3 = -15 hours");
    }
}
