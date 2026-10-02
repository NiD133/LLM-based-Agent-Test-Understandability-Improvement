package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(JulianEra.AD, BritishCutoverChronology.INSTANCE.eraOf(1));
        assertEquals(JulianEra.BC, BritishCutoverChronology.INSTANCE.eraOf(0));
    }
}
