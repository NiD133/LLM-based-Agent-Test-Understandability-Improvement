package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_badEra() {
        assertThrows(
                ClassCastException.class,
                () -> PaxChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
