package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_Months {

    @Test
    public void test_plus_TemporalAmount_Months() {
        Months fiveMonths = Months.of(5);

        assertEquals(Months.of(5), fiveMonths.plus(Months.of(0)));
        assertEquals(Months.of(7), fiveMonths.plus(Months.of(2)));
        assertEquals(Months.of(3), fiveMonths.plus(Months.of(-2)));

        Months oneBelowMaxValue = Months.of(Integer.MAX_VALUE - 1);
        assertEquals(Months.of(Integer.MAX_VALUE), oneBelowMaxValue.plus(Months.of(1)));

        Months oneAboveMinValue = Months.of(Integer.MIN_VALUE + 1);
        assertEquals(Months.of(Integer.MIN_VALUE), oneAboveMinValue.plus(Months.of(-1)));
    }
}
