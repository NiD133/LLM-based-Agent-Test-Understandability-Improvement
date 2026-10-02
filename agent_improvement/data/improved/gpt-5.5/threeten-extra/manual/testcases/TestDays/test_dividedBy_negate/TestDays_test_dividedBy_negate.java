package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Days twelveDays = Days.of(12);

        Days dividedByNegativeThree = twelveDays.dividedBy(-3);

        assertEquals(Days.of(-4), dividedByNegativeThree);
    }
}
