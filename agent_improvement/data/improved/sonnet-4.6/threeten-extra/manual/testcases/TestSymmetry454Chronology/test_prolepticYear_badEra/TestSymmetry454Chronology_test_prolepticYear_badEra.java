package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistEra;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry454Chronology#prolepticYear(Era, int)} throws
 * {@link ClassCastException} when passed an era that is not an {@code IsoEra}.
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_prolepticYear_badEra {

    /**
     * Provides era instances from calendars other than ISO (i.e. not {@code IsoEra}).
     * Symmetry454 only accepts {@code IsoEra}; every other era type must be rejected.
     */
    public static Era[][] data_prolepticYear_badEra() {
        return new Era[][] {
            {AccountingEra.BCE},
            {AccountingEra.CE},
            {CopticEra.BEFORE_AM},
            {CopticEra.AM},
            {DiscordianEra.YOLD},
            {EthiopicEra.BEFORE_INCARNATION},
            {EthiopicEra.INCARNATION},
            {HijrahEra.AH},
            {InternationalFixedEra.CE},
            {JapaneseEra.MEIJI},
            {JapaneseEra.TAISHO},
            {JapaneseEra.SHOWA},
            {JapaneseEra.HEISEI},
            {JulianEra.BC},
            {JulianEra.AD},
            {MinguoEra.BEFORE_ROC},
            {MinguoEra.ROC},
            {PaxEra.BCE},
            {PaxEra.CE},
            {ThaiBuddhistEra.BEFORE_BE},
            {ThaiBuddhistEra.BE},
        };
    }

    @ParameterizedTest
    @MethodSource("data_prolepticYear_badEra")
    public void test_prolepticYear_badEra(Era era) {
        assertThrows(ClassCastException.class,
                () -> Symmetry454Chronology.INSTANCE.prolepticYear(era, 4));
    }
}
