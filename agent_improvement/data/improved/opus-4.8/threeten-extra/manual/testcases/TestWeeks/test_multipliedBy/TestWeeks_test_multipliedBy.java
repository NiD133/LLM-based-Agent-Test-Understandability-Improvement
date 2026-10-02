package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#multipliedBy(int)}.
 */
public class TestWeeks_test_multipliedBy {

    //-----------------------------------------------------------------------
    @Test
    public void test_multipliedBy() {
        Weeks fiveWeeks = Weeks.of(5);

        // multiplying by various scalars scales the number of weeks accordingly
        assertEquals(Weeks.of(0), fiveWeeks.multipliedBy(0), "5 weeks * 0");
        assertEquals(Weeks.of(5), fiveWeeks.multipliedBy(1), "5 weeks * 1");
        assertEquals(Weeks.of(10), fiveWeeks.multipliedBy(2), "5 weeks * 2");
        assertEquals(Weeks.of(15), fiveWeeks.multipliedBy(3), "5 weeks * 3");

        // a negative scalar yields a negative amount
        assertEquals(Weeks.of(-15), fiveWeeks.multipliedBy(-3), "5 weeks * -3");
    }
}
