package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_negated {

    @Test
    public void test_negated() {
        assertEquals(Weeks.of(0), Weeks.of(0).negated());
        assertEquals(Weeks.of(-12), Weeks.of(12).negated());
        assertEquals(Weeks.of(12), Weeks.of(-12).negated());
        // MAX_VALUE negates safely; MIN_VALUE would overflow, so this is the boundary case tested
        assertEquals(Weeks.of(-Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE).negated());
    }
}
