package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestTaiInstant_test_withNano {

    /**
     * Test data for withNano().
     *
     * Columns: taiSeconds, nanoAdjustment, newNano, expectedTai, expectedNano
     *
     * When expectedTai is null, withNano(newNano) is expected to throw
     * IllegalArgumentException because newNano is out of the valid range [0, 999_999_999].
     */
    public static Object[][] data_withNano() {
        return new @Nullable Object[][] {
            // Valid nano-of-second replacements: TAI seconds are unchanged, nano is replaced
            {  0L, 12345L,           1,  0L,           1L },
            {  7L, 12345L,           2,  7L,           2L },
            { -99L, 12345L,          3, -99L,          3L },
            { -99L, 12345L,  999999999, -99L,  999999999L },

            // Invalid nano-of-second values: expect IllegalArgumentException
            { -99L, 12345L,         -1, null,          0L },  // below minimum (0)
            { -99L, 12345L, 1000000000, null,          0L },  // above maximum (999_999_999)
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNano")
    public void test_withNano(long tai, long nanos, int newNano, @Nullable Long expectedTai, long expectedNanos) {
        TaiInstant i = TaiInstant.ofTaiSeconds(tai, nanos);
        if (expectedTai != null) {
            TaiInstant withNano = i.withNano(newNano);
            assertEquals(expectedTai.longValue(), withNano.getTaiSeconds());
            assertEquals(expectedNanos, withNano.getNano());
        } else {
            assertThrows(IllegalArgumentException.class, () -> i.withNano(newNano));
        }
    }
}
