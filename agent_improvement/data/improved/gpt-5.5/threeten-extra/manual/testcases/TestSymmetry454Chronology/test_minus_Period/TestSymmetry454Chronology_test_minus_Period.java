package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        Symmetry454Date startDate = Symmetry454Date.of(2014, 5, 26);
        ChronoPeriod twoMonthsAndThreeDays = Symmetry454Chronology.INSTANCE.period(0, 2, 3);
        Symmetry454Date expectedDate = Symmetry454Date.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(twoMonthsAndThreeDays));
    }
}
