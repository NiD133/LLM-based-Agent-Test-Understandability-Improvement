package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestTaiInstant_test_withNano {

    @Nullable
    public static Object[][] data_withNano() {
        return new @Nullable Object[][] {
                {0L, 12345L, 1, 0L, 1L},
                {7L, 12345L, 2, 7L, 2L},
                {-99L, 12345L, 3, -99L, 3L},
                {-99L, 12345L, 999999999, -99L, 999999999L},
                {-99L, 12345L, -1, null, 0L},
                {-99L, 12345L, 1000000000, null, 0L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNano")
    public void test_withNano(
            long taiSeconds,
            long nanoAdjustment,
            int requestedNano,
            @Nullable Long expectedTaiSeconds,
            long expectedNano) {

        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);

        if (expectedTaiSeconds != null) {
            TaiInstant result = instant.withNano(requestedNano);
            assertEquals(expectedTaiSeconds.longValue(), result.getTaiSeconds());
            assertEquals(expectedNano, result.getNano());
        } else {
            assertThrows(IllegalArgumentException.class, () -> instant.withNano(requestedNano));
        }
    }
}
