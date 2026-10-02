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
 * {@link ClassCastException} for any era that is not an {@code IsoEra}.
 *
 * <p>The Symmetry454 calendar only recognises {@code IsoEra} (BCE/CE), so
 * passing any other era type must fail-fast with a {@code ClassCastException}.
 */
public class TestSymmetry454Chronology_test_prolepticYear_badEra {

    /**
     * Provides Era instances from various non-ISO calendar systems.
     * None of these implement {@code IsoEra}, so every entry is a "bad" era
     * for the Symmetry454 chronology.
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

    /**
     * Verifies that supplying a non-{@code IsoEra} to {@code prolepticYear}
     * causes a {@code ClassCastException}.  The Symmetry454 chronology casts
     * its era argument to {@code IsoEra} internally, so any other era type
     * must be rejected immediately.
     */
    @ParameterizedTest
    @MethodSource("data_prolepticYear_badEra")
    public void test_prolepticYear_badEra(Era era) {
        assertThrows(ClassCastException.class,
                () -> Symmetry454Chronology.INSTANCE.prolepticYear(era, 4));
    }
}
