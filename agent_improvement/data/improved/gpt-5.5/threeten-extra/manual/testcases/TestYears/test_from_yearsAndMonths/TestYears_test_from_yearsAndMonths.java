package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_yearsAndMonths {

    @Test
    public void test_from_yearsAndMonths() {
        Years expectedYears = Years.of(5);
        Years actualYears = Years.from(Period.of(3, 24, 0));

        assertEquals(expectedYears, actualYears);
    }
}
