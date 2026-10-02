package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Weeks twelveWeeks = Weeks.of(12);

        // Dividing by 1 returns the same amount unchanged
        assertEquals(Weeks.of(12), twelveWeeks.dividedBy(1));

        // Exact divisions produce an exact result
        assertEquals(Weeks.of(6), twelveWeeks.dividedBy(2));
        assertEquals(Weeks.of(4), twelveWeeks.dividedBy(3));
        assertEquals(Weeks.of(3), twelveWeeks.dividedBy(4));

        // Non-exact divisions truncate toward zero (integer division)
        assertEquals(Weeks.of(2), twelveWeeks.dividedBy(5));  // 12/5 = 2 (remainder 2 discarded)
        assertEquals(Weeks.of(2), twelveWeeks.dividedBy(6));  // 12/6 = 2 (exact)

        // A negative divisor flips the sign of the result
        assertEquals(Weeks.of(-4), twelveWeeks.dividedBy(-3));
    }
}
