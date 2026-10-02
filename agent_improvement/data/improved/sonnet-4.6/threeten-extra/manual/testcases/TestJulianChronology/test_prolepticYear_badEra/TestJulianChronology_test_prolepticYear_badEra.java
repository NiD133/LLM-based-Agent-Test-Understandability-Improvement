package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_prolepticYear_badEra {

    /**
     * Passing a non-JulianEra (IsoEra.CE) to prolepticYear must throw ClassCastException,
     * because the implementation casts the Era to JulianEra.
     */
    @Test
    public void test_prolepticYear_badEra() {
        assertThrows(ClassCastException.class, () -> JulianChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
