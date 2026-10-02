package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxChronology#prolepticYear(java.time.chrono.Era, int)}
 * rejects an era that does not belong to the Pax calendar system.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_prolepticYear_badEra {

    @Test
    public void prolepticYear_withNonPaxEra_throwsClassCastException() {
        // IsoEra is not a PaxEra, so the conversion must be rejected.
        assertThrows(ClassCastException.class,
                () -> PaxChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
