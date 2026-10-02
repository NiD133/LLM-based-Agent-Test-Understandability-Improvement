package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Months twelveMonths = Months.of(12);
        Months dividedByNegativeThree = twelveMonths.dividedBy(-3);

        assertEquals(Months.of(-4), dividedByNegativeThree);
    }
}
