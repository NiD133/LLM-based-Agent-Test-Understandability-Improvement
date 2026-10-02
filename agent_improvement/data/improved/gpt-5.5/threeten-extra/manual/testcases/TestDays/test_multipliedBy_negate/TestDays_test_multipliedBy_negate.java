package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Days fiveDays = Days.of(5);
        Days expectedNegativeProduct = Days.of(-15);

        assertEquals(expectedNegativeProduct, fiveDays.multipliedBy(-3));
    }
}
