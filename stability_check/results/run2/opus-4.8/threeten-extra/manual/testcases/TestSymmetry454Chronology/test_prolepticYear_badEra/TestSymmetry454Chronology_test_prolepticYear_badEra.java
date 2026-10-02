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
     * Every era below belongs to a chronology other than {@link Symmetry454Chronology},
     * so passing any of them to {@code prolepticYear} must be rejected.
     */
    public static Object[][] data_prolepticYear_badEra() {
        return new Era[][] {
            { AccountingEra.BCE }, { AccountingEra.CE },
            { CopticEra.BEFORE_AM }, { CopticEra.AM },
            { DiscordianEra.YOLD },
            { EthiopicEra.BEFORE_INCARNATION }, { EthiopicEra.INCARNATION },
            { HijrahEra.AH },
            { InternationalFixedEra.CE },
            { JapaneseEra.MEIJI }, { JapaneseEra.TAISHO }, { JapaneseEra.SHOWA }, { JapaneseEra.HEISEI },
            { JulianEra.BC }, { JulianEra.AD },
            { MinguoEra.BEFORE_ROC }, { MinguoEra.ROC },
            { PaxEra.BCE }, { PaxEra.CE },
            { ThaiBuddhistEra.BEFORE_BE }, { ThaiBuddhistEra.BE }
        };
    }

    @ParameterizedTest
    @MethodSource("data_prolepticYear_badEra")
    public void test_prolepticYear_badEra(Era era) {
        assertThrows(ClassCastException.class, () -> Symmetry454Chronology.INSTANCE.prolepticYear(era, 4));
    }
}
