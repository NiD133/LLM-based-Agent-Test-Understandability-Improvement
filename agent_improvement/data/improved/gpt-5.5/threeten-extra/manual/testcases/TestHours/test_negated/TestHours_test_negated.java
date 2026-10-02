package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_negated {

    @Test
    public void test_negated() {
        assertEquals(Hours.of(0), Hours.of(0).negated());
        assertEquals(Hours.of(-12), Hours.of(12).negated());
        assertEquals(Hours.of(12), Hours.of(-12).negated());
        assertEquals(Hours.of(-Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE).negated());
    }
}
