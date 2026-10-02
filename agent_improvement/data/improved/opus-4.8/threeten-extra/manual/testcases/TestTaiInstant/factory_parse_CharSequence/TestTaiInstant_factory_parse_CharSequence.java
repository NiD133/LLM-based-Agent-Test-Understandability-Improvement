package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#parse(CharSequence)} correctly reads back the
 * canonical {@code "{seconds}.{nanos}s(TAI)"} text format, recovering both the
 * seconds and the nanosecond-of-second components.
 */
public class TestTaiInstant_factory_parse_CharSequence {

    /** Lowest seconds value exercised by the parse sweep (inclusive). */
    private static final int FIRST_SECONDS = -1000;
    /** One past the highest seconds value exercised by the parse sweep. */
    private static final int LAST_SECONDS_EXCLUSIVE = 1000;

    /** Lowest nanosecond-of-second value exercised (inclusive). */
    private static final int FIRST_NANOS = 900_000_000;
    /** One past the highest nanosecond-of-second value exercised. */
    private static final int LAST_NANOS_EXCLUSIVE = 990_000_000;
    /** Step between successive nanosecond-of-second values. */
    private static final int NANOS_STEP = 10_000_000;

    @Test
    public void factory_parse_CharSequence() {
        for (int seconds = FIRST_SECONDS; seconds < LAST_SECONDS_EXCLUSIVE; seconds++) {
            for (int nanos = FIRST_NANOS; nanos < LAST_NANOS_EXCLUSIVE; nanos += NANOS_STEP) {
                String text = seconds + "." + nanos + "s(TAI)";

                TaiInstant parsed = TaiInstant.parse(text);

                assertEquals(seconds, parsed.getTaiSeconds());
                assertEquals(nanos, parsed.getNano());
            }
        }
    }
}
