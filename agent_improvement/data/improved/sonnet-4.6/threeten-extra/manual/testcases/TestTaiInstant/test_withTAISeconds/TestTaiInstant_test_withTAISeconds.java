package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link TaiInstant#withTaiSeconds} replaces the TAI seconds component
 * while preserving the nanosecond-of-second unchanged.
 */
public class TestTaiInstant_test_withTAISeconds {

    /**
     * Test cases for {@link TaiInstant#withTaiSeconds}.
     *
     * <p>Columns: initialTaiSeconds | initialNanos | newTaiSeconds | expectedTaiSeconds | expectedNanos
     */
    public static Object[][] data_withTAISeconds() {
        return new Object[][] {
            //  initialTai   initialNanos   newTai   expectedTai   expectedNanos
            {        0L,        12345L,      1L,          1L,        12345L },
            {        0L,        12345L,     -1L,         -1L,        12345L },
            {        7L,        12345L,      2L,          2L,        12345L },
            {        7L,        12345L,     -2L,         -2L,        12345L },
            {      -99L,        12345L,      3L,          3L,        12345L },
            {      -99L,        12345L,     -3L,         -3L,        12345L },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withTAISeconds")
    public void test_withTAISeconds(
            long initialTai, long initialNanos,
            long newTai,
            long expectedTai, long expectedNanos) {

        TaiInstant original = TaiInstant.ofTaiSeconds(initialTai, initialNanos);
        TaiInstant result = original.withTaiSeconds(newTai);

        assertEquals(expectedTai, result.getTaiSeconds(),
                "TAI seconds should be replaced with the new value");
        assertEquals(expectedNanos, result.getNano(),
                "Nanoseconds should be preserved unchanged");
    }
}
