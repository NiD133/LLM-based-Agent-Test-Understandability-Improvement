package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedChronology#prolepticYear} rejects an era
 * that does not belong to the International Fixed calendar system.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_badEra() {
        // IsoEra is not an InternationalFixedEra, so it must be rejected.
        assertThrows(
            ClassCastException.class,
            () -> InternationalFixedChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
