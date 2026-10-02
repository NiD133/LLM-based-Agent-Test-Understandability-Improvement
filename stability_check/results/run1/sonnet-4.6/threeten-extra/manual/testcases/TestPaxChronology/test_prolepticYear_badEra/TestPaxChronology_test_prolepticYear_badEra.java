package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Verifies that PaxChronology.prolepticYear rejects era types other than PaxEra.
 * The method casts its era argument to PaxEra, so passing any other Era implementation
 * (such as IsoEra) must throw ClassCastException.
 */
@SuppressWarnings("static-method")
public class TestPaxChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_badEra() {
        // IsoEra.CE is not a PaxEra; prolepticYear must throw ClassCastException
        assertThrows(ClassCastException.class, () -> PaxChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
