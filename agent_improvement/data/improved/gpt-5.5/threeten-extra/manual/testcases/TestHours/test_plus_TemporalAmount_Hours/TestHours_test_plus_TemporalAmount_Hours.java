package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_Hours {

    @Test
    public void test_plus_TemporalAmount_Hours() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(5), fiveHours.plus(Hours.of(0)));
        assertEquals(Hours.of(7), fiveHours.plus(Hours.of(2)));
        assertEquals(Hours.of(3), fiveHours.plus(Hours.of(-2)));

        Hours oneHourBelowMax = Hours.of(Integer.MAX_VALUE - 1);
        assertEquals(Hours.of(Integer.MAX_VALUE), oneHourBelowMax.plus(Hours.of(1)));

        Hours oneHourAboveMin = Hours.of(Integer.MIN_VALUE + 1);
        assertEquals(Hours.of(Integer.MIN_VALUE), oneHourAboveMin.plus(Hours.of(-1)));
    }
}
