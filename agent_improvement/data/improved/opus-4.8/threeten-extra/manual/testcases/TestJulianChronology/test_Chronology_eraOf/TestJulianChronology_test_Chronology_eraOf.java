package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#eraOf(int)} maps era values to the
 * corresponding {@link JulianEra} constants: 1 is AD and 0 is BC.
 */
public class TestJulianChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(JulianEra.AD, JulianChronology.INSTANCE.eraOf(1), "era value 1 should map to AD");
        assertEquals(JulianEra.BC, JulianChronology.INSTANCE.eraOf(0), "era value 0 should map to BC");
    }
}
