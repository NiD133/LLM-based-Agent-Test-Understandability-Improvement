package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_P0Y {

    /**
     * Converting a zero-year {@link Period} via {@link Years#from} yields {@code Years.of(0)}.
     */
    @Test
    public void test_from_P0Y() {
        Period zeroYearPeriod = Period.ofYears(0);

        Years result = Years.from(zeroYearPeriod);

        assertEquals(Years.of(0), result);
    }
}
