package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_P2Y {

    @Test
    public void test_from_P2Y() {
        Months twoYearsInMonths = Months.from(new MockYearsMonths(2, 0));

        assertEquals(Months.of(24), twoYearsInMonths);
    }
}
