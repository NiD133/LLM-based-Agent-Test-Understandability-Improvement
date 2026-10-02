package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P24M {

    @Test
    public void test_from_24MonthPeriod_convertsTo2Years() {
        // 24 months is exactly 2 years; Years.from() must perform the conversion
        Period twentyFourMonths = Period.ofMonths(24);
        Years expected = Years.of(2);

        assertEquals(expected, Years.from(twentyFourMonths));
    }
}
