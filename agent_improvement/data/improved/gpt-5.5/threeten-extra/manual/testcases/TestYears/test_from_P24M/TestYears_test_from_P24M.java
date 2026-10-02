package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P24M {

    @Test
    public void test_from_P24M() {
        Years expectedYears = Years.of(2);
        Period twentyFourMonths = Period.ofMonths(24);

        Years actualYears = Years.from(twentyFourMonths);

        assertEquals(expectedYears, actualYears);
    }
}
