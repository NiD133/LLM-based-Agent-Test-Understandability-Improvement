package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        Symmetry010Date start = Symmetry010Date.of(2014, 5, 21);
        ChronoPeriod period = Symmetry010Chronology.INSTANCE.period(0, 2, 8);
        Symmetry010Date expected = Symmetry010Date.of(2014, 7, 29);
        assertEquals(expected, start.plus(period));
    }
}
