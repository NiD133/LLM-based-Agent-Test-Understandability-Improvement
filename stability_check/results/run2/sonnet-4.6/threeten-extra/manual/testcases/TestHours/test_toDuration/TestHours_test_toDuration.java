package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestHours_test_toDuration {

    // Range chosen to cover negative, zero, and positive hour values
    private static final int RANGE_MIN = -20;
    private static final int RANGE_MAX = 20;

    /**
     * toDuration() must return a Duration whose hour count matches the Hours amount.
     */
    @Test
    public void test_toDuration() {
        for (int i = RANGE_MIN; i < RANGE_MAX; i++) {
            assertEquals(Duration.ofHours(i), Hours.of(i).toDuration());
        }
    }

    /**
     * The deprecated toPeriod() is a direct alias for toDuration() and must
     * produce identical results across the same range.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void test_toPeriod_deprecatedAliasMatchesToDuration() {
        for (int i = RANGE_MIN; i < RANGE_MAX; i++) {
            assertEquals(Duration.ofHours(i), Hours.of(i).toPeriod());
        }
    }
}
