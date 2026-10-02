package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Years twelveYears = Years.of(12);

        assertEquals(Years.of(-4), twelveYears.dividedBy(-3));
    }
}
