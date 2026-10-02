package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_era_invalid {

    public static Object[][] data_invalidEraValues() {
        return new Object[][] {
                { -1 },
                { 0 },
                { 2 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalidEraValues")
    public void test_era_invalid(int eraValue) {
        assertThrows(
                DateTimeException.class,
                () -> InternationalFixedChronology.INSTANCE.eraOf(eraValue));
    }
}
