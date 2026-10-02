package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxChronology#eraOf(int)} maps era values to the matching {@link PaxEra}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        // Era value 1 is the Current Era; 0 is the Before Current Era.
        assertEquals(PaxEra.CE, PaxChronology.INSTANCE.eraOf(1));
        assertEquals(PaxEra.BCE, PaxChronology.INSTANCE.eraOf(0));
    }
}
