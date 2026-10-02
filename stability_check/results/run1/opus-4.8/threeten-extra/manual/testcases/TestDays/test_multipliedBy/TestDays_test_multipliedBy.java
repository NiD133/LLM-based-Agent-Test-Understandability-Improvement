package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#multipliedBy(int)}.
 */
public class TestDays_test_multipliedBy {

    @Test
    public void multipliedBy_scalesTheNumberOfDays() {
        Days fiveDays = Days.of(5);

        assertEquals(Days.of(0), fiveDays.multipliedBy(0), "5 days * 0 = 0 days");
        assertEquals(Days.of(5), fiveDays.multipliedBy(1), "5 days * 1 = 5 days");
        assertEquals(Days.of(10), fiveDays.multipliedBy(2), "5 days * 2 = 10 days");
        assertEquals(Days.of(15), fiveDays.multipliedBy(3), "5 days * 3 = 15 days");
        assertEquals(Days.of(-15), fiveDays.multipliedBy(-3), "5 days * -3 = -15 days");
    }
}
