package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Years fiveYears = Years.of(5);
        Years expectedResult = Years.of(-15);

        assertEquals(expectedResult, fiveYears.multipliedBy(-3));
    }
}
