package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_yearsAndMonths {

    @Test
    public void test_from_yearsAndMonths() {
        // 3 explicit years + 24 months (= 2 years) converts to 5 years total
        Period periodWithYearsAndMonths = Period.of(3, 24, 0);
        Years expectedYears = Years.of(5);
        assertEquals(expectedYears, Years.from(periodWithYearsAndMonths));
    }
}
