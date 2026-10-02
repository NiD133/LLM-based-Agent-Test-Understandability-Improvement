package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        PaxDate startDate = PaxDate.of(2014, 5, 26);
        PaxDate expectedDate = PaxDate.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(PaxChronology.INSTANCE.period(0, 2, 3)));
    }
}
