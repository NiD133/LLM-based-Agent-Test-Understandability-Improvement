package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toUtcInstant {

    // MJD of the TAI epoch (1958-01-01T00:00:00 TAI)
    private static final long MJD_TAI_EPOCH = 36204L;
    // Seconds per day
    private static final long SECONDS_PER_DAY = 24 * 60 * 60;
    // TAI-UTC offset in seconds at the start of the UTC scale (1972-01-01), applied here as initial offset
    private static final long TAI_UTC_OFFSET_SECONDS = 10L;

    //-----------------------------------------------------------------------
    @Test
    public void test_toUtcInstant() {
        // Verify that converting a TaiInstant to a UtcInstant produces the expected UTC date and nanosecond.
        // For day offset i and intra-day second j:
        //   TAI seconds = i * SECONDS_PER_DAY + j + TAI_UTC_OFFSET_SECONDS
        //   Expected UTC = MJD (MJD_TAI_EPOCH + i), nanosOfDay = j * 1_000_000_000 + 2
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(MJD_TAI_EPOCH + i, j * 1000000000L + 2L);
                TaiInstant test = TaiInstant.ofTaiSeconds(i * SECONDS_PER_DAY + j + TAI_UTC_OFFSET_SECONDS, 2);
                assertEquals(expected, test.toUtcInstant());
            }
        }
    }
}
