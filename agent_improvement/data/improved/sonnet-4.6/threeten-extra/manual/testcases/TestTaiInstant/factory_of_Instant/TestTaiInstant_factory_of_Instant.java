package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_of_Instant {

    // Modified Julian Day numbers that anchor the two epochs used in the calculation
    private static final long MJD_TAI_EPOCH  = 36204L; // 1958-01-01, the TAI epoch
    private static final long MJD_UNIX_EPOCH = 40587L; // 1970-01-01, the Unix epoch

    private static final long SECONDS_PER_DAY = 24L * 60 * 60;

    // Leap seconds accumulated from the TAI epoch (1958-01-01) to the Unix epoch (1970-01-01)
    private static final long LEAP_SECONDS_AT_UNIX_EPOCH = 10;

    @Test
    public void factory_of_Instant() {
        TaiInstant test = TaiInstant.of(Instant.ofEpochSecond(0, 2));
        long expectedTaiSeconds = (MJD_UNIX_EPOCH - MJD_TAI_EPOCH) * SECONDS_PER_DAY + LEAP_SECONDS_AT_UNIX_EPOCH;
        assertEquals(expectedTaiSeconds, test.getTaiSeconds());
        assertEquals(2, test.getNano());
    }
}
