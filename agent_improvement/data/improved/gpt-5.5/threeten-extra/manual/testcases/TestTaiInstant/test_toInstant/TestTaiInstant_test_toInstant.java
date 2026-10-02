package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toInstant {

    private static final int FIRST_TEST_DAY = -1000;
    private static final int LAST_TEST_DAY_EXCLUSIVE = 1000;
    private static final int SECONDS_CHECKED_PER_DAY = 10;
    private static final int SECONDS_PER_DAY = 24 * 60 * 60;

    private static final long UTC_SECONDS_AT_TAI_DAY_ZERO = -378691200L;
    private static final int TAI_SECONDS_AHEAD_OF_UTC_OFFSET = 10;
    private static final int NANO_OF_SECOND = 2;

    @Test
    public void test_toInstant() {
        for (int dayOffset = FIRST_TEST_DAY; dayOffset < LAST_TEST_DAY_EXCLUSIVE; dayOffset++) {
            for (int secondOffset = 0; secondOffset < SECONDS_CHECKED_PER_DAY; secondOffset++) {
                Instant expected = expectedInstant(dayOffset, secondOffset);
                TaiInstant test = taiInstant(dayOffset, secondOffset);

                assertEquals(expected, test.toInstant());
            }
        }
    }

    private static Instant expectedInstant(int dayOffset, int secondOffset) {
        long epochSecond = UTC_SECONDS_AT_TAI_DAY_ZERO + dayOffset * SECONDS_PER_DAY + secondOffset;
        return Instant.ofEpochSecond(epochSecond).plusNanos(NANO_OF_SECOND);
    }

    private static TaiInstant taiInstant(int dayOffset, int secondOffset) {
        long taiSecond = dayOffset * SECONDS_PER_DAY + secondOffset + TAI_SECONDS_AHEAD_OF_UTC_OFFSET;
        return TaiInstant.ofTaiSeconds(taiSecond, NANO_OF_SECOND);
    }
}
