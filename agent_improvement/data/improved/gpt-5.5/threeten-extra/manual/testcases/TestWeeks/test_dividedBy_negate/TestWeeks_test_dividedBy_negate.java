package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Weeks twelveWeeks = Weeks.of(12);

        Weeks dividedByNegativeThree = twelveWeeks.dividedBy(-3);

        assertEquals(Weeks.of(-4), dividedByNegativeThree);
    }
}
