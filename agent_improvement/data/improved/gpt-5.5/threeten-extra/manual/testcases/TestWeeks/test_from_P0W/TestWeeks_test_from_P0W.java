package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P0W {

    @Test
    public void test_from_P0W() {
        Weeks expectedWeeks = Weeks.of(0);
        Period zeroWeekPeriod = Period.ofWeeks(0);

        assertEquals(expectedWeeks, Weeks.from(zeroWeekPeriod));
    }
}
