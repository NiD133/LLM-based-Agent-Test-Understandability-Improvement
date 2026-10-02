package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

/**
 * Tests subtracting a {@link ChronoPeriod} from a {@link Symmetry010Date}
 * via {@link Symmetry010Date#minus}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting a period of 2 months and 3 days from 2014-05-26
        // yields 2014-03-23 in the Symmetry010 calendar system.
        Symmetry010Date startDate = Symmetry010Date.of(2014, 5, 26);
        ChronoPeriod periodToSubtract = Symmetry010Chronology.INSTANCE.period(0, 2, 3);

        Symmetry010Date expectedDate = Symmetry010Date.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(periodToSubtract));
    }
}
