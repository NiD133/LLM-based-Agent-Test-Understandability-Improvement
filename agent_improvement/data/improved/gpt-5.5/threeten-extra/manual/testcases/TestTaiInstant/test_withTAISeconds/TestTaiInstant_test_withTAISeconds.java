package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestTaiInstant_test_withTAISeconds {

    public static Object[][] data_withTAISeconds() {
        return new Object[][] {
                {0L, 12345L, 1L, 1L, 12345L},
                {0L, 12345L, -1L, -1L, 12345L},
                {7L, 12345L, 2L, 2L, 12345L},
                {7L, 12345L, -2L, -2L, 12345L},
                {-99L, 12345L, 3L, 3L, 12345L},
                {-99L, 12345L, -3L, -3L, 12345L},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withTAISeconds")
    public void test_withTAISeconds(
            long originalTaiSeconds,
            long originalNanoAdjustment,
            long replacementTaiSeconds,
            Long expectedTaiSeconds,
            Long expectedNanoOfSecond) {

        TaiInstant instant = TaiInstant.ofTaiSeconds(originalTaiSeconds, originalNanoAdjustment)
                .withTaiSeconds(replacementTaiSeconds);

        assertEquals(expectedTaiSeconds.longValue(), instant.getTaiSeconds());
        assertEquals(expectedNanoOfSecond.longValue(), instant.getNano());
    }
}
