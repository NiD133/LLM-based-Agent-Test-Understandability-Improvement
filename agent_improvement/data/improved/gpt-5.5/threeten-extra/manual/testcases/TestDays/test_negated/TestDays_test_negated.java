package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_negated {

    @Test
    public void test_negated() {
        assertEquals(Days.of(0), Days.of(0).negated());
        assertEquals(Days.of(-12), Days.of(12).negated());
        assertEquals(Days.of(12), Days.of(-12).negated());
        assertEquals(Days.of(-Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE).negated());
    }
}
