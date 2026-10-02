package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toUtcInstant {

    private static final int FIRST_DAY_OFFSET = -1000;
    private static final int LAST_DAY_OFFSET_EXCLUSIVE = 1000;
    private static final int FIRST_SECOND_OF_DAY = 0;
    private static final int LAST_SECOND_OF_DAY_EXCLUSIVE = 10;

    private static final long MJD_AT_TAI_EPOCH_UTC_DATE = 36204L;
    private static final int SECONDS_PER_DAY = 24 * 60 * 60;
    private static final int TAI_MINUS_UTC_SECONDS_AT_EPOCH = 10;
    private static final long NANOS_PER_SECOND = 1000000000L;
    private static final long NANO_ADJUSTMENT = 2L;

    @Test
    public void test_toUtcInstant() {
        for (int dayOffset = FIRST_DAY_OFFSET; dayOffset < LAST_DAY_OFFSET_EXCLUSIVE; dayOffset++) {
            for (int secondOfDay = FIRST_SECOND_OF_DAY;
                    secondOfDay < LAST_SECOND_OF_DAY_EXCLUSIVE;
                    secondOfDay++) {
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(
                        MJD_AT_TAI_EPOCH_UTC_DATE + dayOffset,
                        secondOfDay * NANOS_PER_SECOND + NANO_ADJUSTMENT);

                TaiInstant test = TaiInstant.ofTaiSeconds(
                        dayOffset * SECONDS_PER_DAY + secondOfDay + TAI_MINUS_UTC_SECONDS_AT_EPOCH,
                        NANO_ADJUSTMENT);

                assertEquals(expected, test.toUtcInstant());
            }
        }
    }
}
