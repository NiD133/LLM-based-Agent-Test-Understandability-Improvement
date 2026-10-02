package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#dividedBy(int)}.
 * <p>
 * Division uses integer arithmetic, so any fractional remainder is discarded
 * (for example, 12 divided by 5 yields 2, not 2.4).
 */
public class TestDays_test_dividedBy {

    @Test
    public void dividedBy_usesIntegerDivision() {
        Days twelveDays = Days.of(12);

        assertEquals(Days.of(12), twelveDays.dividedBy(1), "12 / 1 = 12");
        assertEquals(Days.of(6), twelveDays.dividedBy(2), "12 / 2 = 6");
        assertEquals(Days.of(4), twelveDays.dividedBy(3), "12 / 3 = 4");
        assertEquals(Days.of(3), twelveDays.dividedBy(4), "12 / 4 = 3");
        assertEquals(Days.of(2), twelveDays.dividedBy(5), "12 / 5 = 2 (remainder dropped)");
        assertEquals(Days.of(2), twelveDays.dividedBy(6), "12 / 6 = 2");
        assertEquals(Days.of(-4), twelveDays.dividedBy(-3), "12 / -3 = -4 (negative divisor)");
    }
}
