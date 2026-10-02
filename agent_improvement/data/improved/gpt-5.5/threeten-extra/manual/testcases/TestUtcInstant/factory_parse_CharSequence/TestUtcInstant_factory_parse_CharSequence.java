package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_parse_CharSequence {

    private static final long MJD_1972_12_31_LEAP = 41682;
    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    @Test
    public void factory_parse_CharSequence() {
        assertEquals(
                UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY - NANOS_PER_SEC),
                UtcInstant.parse("1972-12-31T23:59:59Z"));
        assertEquals(
                UtcInstant.ofModifiedJulianDay(MJD_1972_12_31_LEAP, NANOS_PER_DAY),
                UtcInstant.parse("1972-12-31T23:59:60Z"));
    }
}
