package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistEra;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_badEra {

    /**
     * Eras that belong to calendar systems other than Symmetry454. Passing any of
     * these to {@link Symmetry454Chronology#prolepticYear(Era, int)} is invalid.
     */
    public static Object[][] data_prolepticYear_badEra() {
        return new Era[][] {
            { AccountingEra.BCE }, { AccountingEra.CE },
            { CopticEra.BEFORE_AM }, { CopticEra.AM },
            { DiscordianEra.YOLD },
            { EthiopicEra.BEFORE_INCARNATION }, { EthiopicEra.INCARNATION },
            { HijrahEra.AH },
            { InternationalFixedEra.CE },
            { JapaneseEra.MEIJI }, { JapaneseEra.TAISHO },
            { JapaneseEra.SHOWA }, { JapaneseEra.HEISEI },
            { JulianEra.BC }, { JulianEra.AD },
            { MinguoEra.BEFORE_ROC }, { MinguoEra.ROC },
            { PaxEra.BCE }, { PaxEra.CE },
            { ThaiBuddhistEra.BEFORE_BE }, { ThaiBuddhistEra.BE }
        };
    }

    @ParameterizedTest
    @MethodSource("data_prolepticYear_badEra")
    public void test_prolepticYear_badEra(Era era) {
        // A non-Symmetry454 era cannot be cast to IsoEra, so prolepticYear must reject it.
        assertThrows(ClassCastException.class,
            () -> Symmetry454Chronology.INSTANCE.prolepticYear(era, 4));
    }
}
