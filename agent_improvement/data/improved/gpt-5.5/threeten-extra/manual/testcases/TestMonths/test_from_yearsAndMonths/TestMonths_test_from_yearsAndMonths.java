package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_yearsAndMonths {

    @Test
    public void test_from_yearsAndMonths() {
        Period threeYearsAndFiveMonths = Period.of(3, 5, 0);

        assertEquals(Months.of(41), Months.from(threeYearsAndFiveMonths));
    }
}
