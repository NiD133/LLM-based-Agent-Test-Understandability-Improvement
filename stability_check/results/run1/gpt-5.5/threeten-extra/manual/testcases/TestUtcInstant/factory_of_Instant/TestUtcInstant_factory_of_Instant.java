package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant {

    private static final long MJD_1970_01_01 = 40587L;
    private static final long TWO_NANOS_AFTER_MIDNIGHT = 2L;

    @Test
    public void factory_of_Instant() {
        Instant javaEpochWithTwoNanos = Instant.ofEpochSecond(0, 2);

        UtcInstant utcInstant = UtcInstant.of(javaEpochWithTwoNanos);

        assertEquals(MJD_1970_01_01, utcInstant.getModifiedJulianDay());
        assertEquals(TWO_NANOS_AFTER_MIDNIGHT, utcInstant.getNanoOfDay());
    }
}
