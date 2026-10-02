package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistEra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Symmetry010Chronology#prolepticYear(Era, int)} throws
 * {@link ClassCastException} when supplied with an era that is not an {@code IsoEra}.
 */
@DisplayName("Symmetry010Chronology.prolepticYear() rejects non-IsoEra instances")
public class TestSymmetry010Chronology_test_prolepticYear_badEra {

    /**
     * Returns era instances from calendars other than ISO.
     * Every value here must trigger a ClassCastException inside prolepticYear().
     */
    public static Era[][] data_prolepticYear_badEra() {
        return new Era[][] {
            { AccountingEra.BCE },
            { AccountingEra.CE },
            { CopticEra.BEFORE_AM },
            { CopticEra.AM },
            { DiscordianEra.YOLD },
            { EthiopicEra.BEFORE_INCARNATION },
            { EthiopicEra.INCARNATION },
            { HijrahEra.AH },
            { InternationalFixedEra.CE },
            { JapaneseEra.MEIJI },
            { JapaneseEra.TAISHO },
            { JapaneseEra.SHOWA },
            { JapaneseEra.HEISEI },
            { JulianEra.BC },
            { JulianEra.AD },
            { MinguoEra.BEFORE_ROC },
            { MinguoEra.ROC },
            { PaxEra.BCE },
            { PaxEra.CE },
            { ThaiBuddhistEra.BEFORE_BE },
            { ThaiBuddhistEra.BE },
        };
    }

    @ParameterizedTest
    @MethodSource("data_prolepticYear_badEra")
    @DisplayName("prolepticYear() throws ClassCastException for non-IsoEra")
    public void test_prolepticYear_badEra(Era era) {
        assertThrows(ClassCastException.class,
                () -> Symmetry010Chronology.INSTANCE.prolepticYear(era, 4));
    }
}
