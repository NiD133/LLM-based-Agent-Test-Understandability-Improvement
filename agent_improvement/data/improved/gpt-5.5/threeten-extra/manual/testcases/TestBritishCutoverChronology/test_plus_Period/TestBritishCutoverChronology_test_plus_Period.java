package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        assertEquals(
                BritishCutoverDate.of(1752, 10, 5),
                BritishCutoverDate.of(1752, 9, 2)
                        .plus(BritishCutoverChronology.INSTANCE.period(0, 1, 3)));

        assertEquals(
                BritishCutoverDate.of(1752, 9, 23),
                BritishCutoverDate.of(1752, 8, 12)
                        .plus(BritishCutoverChronology.INSTANCE.period(0, 1, 0)));

        assertEquals(
                BritishCutoverDate.of(2014, 7, 29),
                BritishCutoverDate.of(2014, 5, 26)
                        .plus(BritishCutoverChronology.INSTANCE.period(0, 2, 3)));
    }
}
