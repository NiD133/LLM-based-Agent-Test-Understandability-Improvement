package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Hours twelveHours = Hours.of(12);

        Hours dividedByNegativeThree = twelveHours.dividedBy(-3);

        assertEquals(Hours.of(-4), dividedByNegativeThree);
    }
}
