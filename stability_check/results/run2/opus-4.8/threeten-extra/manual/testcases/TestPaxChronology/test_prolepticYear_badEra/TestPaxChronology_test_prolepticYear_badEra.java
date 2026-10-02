package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxChronology#prolepticYear(java.time.chrono.Era, int)}
 * rejects an era that is not a {@code PaxEra}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_prolepticYear_badEra {

    @Test
    public void prolepticYear_withNonPaxEra_throwsClassCastException() {
        // IsoEra.CE is not a PaxEra, so it must be rejected.
        assertThrows(ClassCastException.class,
                () -> PaxChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
