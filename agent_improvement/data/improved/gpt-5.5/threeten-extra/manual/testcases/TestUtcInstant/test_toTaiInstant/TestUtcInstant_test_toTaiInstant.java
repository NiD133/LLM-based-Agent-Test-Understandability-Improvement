package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_toTaiInstant {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1000000000L;

    private static final int START_DAY_OFFSET = -1000;
    private static final int END_DAY_OFFSET_EXCLUSIVE = 1000;
    private static final int SECONDS_PER_TEST_DAY = 10;
    private static final long UTC_EPOCH_MJD = 36204;
    private static final long TAI_OFFSET_SECONDS = 10;
    private static final long NANO_ADJUSTMENT = 2L;

    //-----------------------------------------------------------------------
    @Test
    public void test_toTaiInstant() {
        for (int i = START_DAY_OFFSET; i < END_DAY_OFFSET_EXCLUSIVE; i++) {
            for (int j = 0; j < SECONDS_PER_TEST_DAY; j++) {
                UtcInstant utc = UtcInstant.ofModifiedJulianDay(UTC_EPOCH_MJD + i, j * NANOS_PER_SEC + NANO_ADJUSTMENT);
                TaiInstant test = utc.toTaiInstant();
                assertEquals(i * SECS_PER_DAY + j + TAI_OFFSET_SECONDS, test.getTaiSeconds());
                assertEquals(NANO_ADJUSTMENT, test.getNano());
            }
        }
    }
}
