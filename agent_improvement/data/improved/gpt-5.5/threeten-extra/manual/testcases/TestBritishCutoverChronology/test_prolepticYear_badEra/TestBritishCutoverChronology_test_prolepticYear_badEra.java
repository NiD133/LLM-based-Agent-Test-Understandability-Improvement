package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_prolepticYear_badEra {

    private static final IsoEra NON_BRITISH_CUTOVER_ERA = IsoEra.CE;
    private static final int YEAR_OF_ERA = 4;

    @Test
    public void test_prolepticYear_badEra() {
        assertThrows(
                ClassCastException.class,
                () -> BritishCutoverChronology.INSTANCE.prolepticYear(NON_BRITISH_CUTOVER_ERA, YEAR_OF_ERA));
    }
}
