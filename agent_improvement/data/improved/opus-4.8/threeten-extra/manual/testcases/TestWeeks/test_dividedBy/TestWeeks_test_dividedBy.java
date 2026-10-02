package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#dividedBy(int)}.
 * <p>
 * Division uses integer (truncating) arithmetic, so any fractional part of the
 * result is discarded rather than rounded.
 */
public class TestWeeks_test_dividedBy {

    @Test
    public void dividedBy_usesTruncatingIntegerDivision() {
        Weeks twelveWeeks = Weeks.of(12);

        // Dividing by 1 returns the same amount unchanged.
        assertEquals(Weeks.of(12), twelveWeeks.dividedBy(1));
        // 12 / 2 = 6, exact.
        assertEquals(Weeks.of(6), twelveWeeks.dividedBy(2));
        // 12 / 3 = 4, exact.
        assertEquals(Weeks.of(4), twelveWeeks.dividedBy(3));
        // 12 / 4 = 3, exact.
        assertEquals(Weeks.of(3), twelveWeeks.dividedBy(4));
        // 12 / 5 = 2.4 -> truncated to 2.
        assertEquals(Weeks.of(2), twelveWeeks.dividedBy(5));
        // 12 / 6 = 2, exact.
        assertEquals(Weeks.of(2), twelveWeeks.dividedBy(6));
        // 12 / -3 = -4: a negative divisor yields a negative amount.
        assertEquals(Weeks.of(-4), twelveWeeks.dividedBy(-3));
    }
}
