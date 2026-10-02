package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MutableClock#of(Instant, ZoneId)} rejects a null time-zone.
 */
public class TestMutableClock_test_of_nullZone {

    /**
     * {@code of} requires a non-null zone, so passing {@code null} for the
     * zone must raise a {@link NullPointerException} even when the instant is
     * valid.
     */
    @Test
    public void of_withNullZone_throwsNullPointerException() {
        ZoneId nullZone = null; // testing the null-zone rejection path

        assertThrows(
                NullPointerException.class,
                () -> MutableClock.of(Instant.EPOCH, nullZone));
    }
}
