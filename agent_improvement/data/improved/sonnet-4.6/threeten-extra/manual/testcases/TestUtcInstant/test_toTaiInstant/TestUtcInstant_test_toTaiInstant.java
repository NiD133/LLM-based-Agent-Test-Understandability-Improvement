package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_toTaiInstant {

    // MJD of 1958-01-01, the date TAI was defined (TAI epoch)
    private static final long MJD_TAI_EPOCH = 36204;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // TAI-UTC offset in effect before 1972 leap-second era (10 seconds)
    private static final long INITIAL_TAI_UTC_OFFSET_SECS = 10;

    //-----------------------------------------------------------------------
    @Test
    public void test_toTaiInstant() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                UtcInstant utc = UtcInstant.ofModifiedJulianDay(MJD_TAI_EPOCH + i, j * NANOS_PER_SEC + 2L);
                TaiInstant test = utc.toTaiInstant();
                assertEquals(i * SECS_PER_DAY + j + INITIAL_TAI_UTC_OFFSET_SECS, test.getTaiSeconds());
                assertEquals(2, test.getNano());
            }
        }
    }
}
