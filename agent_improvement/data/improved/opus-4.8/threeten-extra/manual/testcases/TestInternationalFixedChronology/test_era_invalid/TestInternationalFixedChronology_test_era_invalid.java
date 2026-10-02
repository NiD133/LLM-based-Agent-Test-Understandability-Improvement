package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link InternationalFixedChronology#eraOf(int)} rejects era values
 * outside the single supported era (CE, whose numeric value is 1).
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_invalid {

    /** Era values that fall outside the valid range, i.e. anything other than 1 (CE). */
    public static Object[][] data_invalidEraValues() {
        return new Object[][] {
            { -1 },
            { 0 },
            { 2 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalidEraValues")
    public void test_era_invalid(int invalidEraValue) {
        assertThrows(DateTimeException.class,
            () -> InternationalFixedChronology.INSTANCE.eraOf(invalidEraValue));
    }
}
