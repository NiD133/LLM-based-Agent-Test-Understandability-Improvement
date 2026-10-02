package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link InternationalFixedChronology#prolepticYear} rejects
 * year-of-era values outside the supported range.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_prolepticYearBad {

    /**
     * Year-of-era values that are not valid for the CE era
     * (the chronology only supports positive years).
     */
    public static Object[][] data_prolepticYear_bad() {
        return new Object[][] { { -10 }, { -1 }, { 0 } };
    }

    @ParameterizedTest
    @MethodSource("data_prolepticYear_bad")
    public void test_prolepticYearBad(int yearOfEra) {
        assertThrows(DateTimeException.class,
                () -> InternationalFixedChronology.INSTANCE.prolepticYear(InternationalFixedEra.CE, yearOfEra));
    }
}
