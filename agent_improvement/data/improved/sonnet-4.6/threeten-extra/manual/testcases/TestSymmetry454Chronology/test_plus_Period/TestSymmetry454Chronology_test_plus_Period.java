package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        Symmetry454Date startDate = Symmetry454Date.of(2014, 5, 21);
        ChronoPeriod period = Symmetry454Chronology.INSTANCE.period(0, 2, 8);
        Symmetry454Date expectedDate = Symmetry454Date.of(2014, 8, 1);

        assertEquals(expectedDate, startDate.plus(period));
    }
}
