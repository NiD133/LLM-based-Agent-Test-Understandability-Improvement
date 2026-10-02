package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P2Y {

    @Test
    public void test_from_P2Y() {
        Years twoYearAmount = Years.of(2);
        Period twoYearPeriod = Period.ofYears(2);

        assertEquals(twoYearAmount, Years.from(twoYearPeriod));
    }
}
