package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(JulianEra.AD, JulianChronology.INSTANCE.eraOf(1));
        assertEquals(JulianEra.BC, JulianChronology.INSTANCE.eraOf(0));
    }
}
