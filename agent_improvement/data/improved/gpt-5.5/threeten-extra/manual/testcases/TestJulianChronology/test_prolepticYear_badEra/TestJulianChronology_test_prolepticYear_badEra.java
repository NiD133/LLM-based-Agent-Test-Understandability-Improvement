package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_badEra() {
        assertThrows(ClassCastException.class, () -> JulianChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
