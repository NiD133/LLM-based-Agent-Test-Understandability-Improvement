package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_negated {

    @Test
    public void test_negated() {
        Days zero = Days.of(0);
        Days positive = Days.of(12);
        Days negative = Days.of(-12);
        Days maxValue = Days.of(Integer.MAX_VALUE);

        assertEquals(Days.of(0), zero.negated());
        assertEquals(Days.of(-12), positive.negated());
        assertEquals(Days.of(12), negative.negated());
        assertEquals(Days.of(-Integer.MAX_VALUE), maxValue.negated());
    }
}
