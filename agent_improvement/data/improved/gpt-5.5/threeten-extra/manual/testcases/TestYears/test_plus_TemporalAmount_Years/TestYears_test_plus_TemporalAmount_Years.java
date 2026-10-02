package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_Years {

    @Test
    public void test_plus_TemporalAmount_Years() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(5), fiveYears.plus(Years.of(0)));
        assertEquals(Years.of(7), fiveYears.plus(Years.of(2)));
        assertEquals(Years.of(3), fiveYears.plus(Years.of(-2)));

        Years oneLessThanMax = Years.of(Integer.MAX_VALUE - 1);
        Years oneMoreThanMin = Years.of(Integer.MIN_VALUE + 1);

        assertEquals(Years.of(Integer.MAX_VALUE), oneLessThanMax.plus(Years.of(1)));
        assertEquals(Years.of(Integer.MIN_VALUE), oneMoreThanMin.plus(Years.of(-1)));
    }
}
