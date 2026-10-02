package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_yearsAndMonths {

    @Test
    public void test_from_yearsAndMonths() {
        // 3 years and 5 months = 3 * 12 + 5 = 41 total months
        int years = 3;
        int months = 5;
        int expectedTotalMonths = years * 12 + months;

        assertEquals(Months.of(expectedTotalMonths), Months.from(Period.of(years, months, 0)));
    }
}
