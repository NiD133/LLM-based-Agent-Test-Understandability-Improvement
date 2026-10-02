package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        Symmetry010Date expectedDate = Symmetry010Date.of(2014, 3, 23);
        Symmetry010Date startDate = Symmetry010Date.of(2014, 5, 26);

        assertEquals(
                expectedDate,
                startDate.minus(Symmetry010Chronology.INSTANCE.period(0, 2, 3)));
    }
}
