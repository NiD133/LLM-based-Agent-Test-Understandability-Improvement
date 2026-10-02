package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_prolepticYear_badEra {

    /**
     * prolepticYear() requires an InternationalFixedEra; passing a foreign Era
     * implementation (IsoEra.CE) must trigger a ClassCastException.
     */
    @Test
    public void test_prolepticYear_badEra() {
        assertThrows(ClassCastException.class, () -> InternationalFixedChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
