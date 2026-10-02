package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_prolepticYearBad {

    @ParameterizedTest
    @ValueSource(ints = { -10, -1, 0 })
    public void test_prolepticYearBad(int invalidYearOfEra) {
        assertThrows(
                DateTimeException.class,
                () -> InternationalFixedChronology.INSTANCE.prolepticYear(InternationalFixedEra.CE, invalidYearOfEra));
    }
}
