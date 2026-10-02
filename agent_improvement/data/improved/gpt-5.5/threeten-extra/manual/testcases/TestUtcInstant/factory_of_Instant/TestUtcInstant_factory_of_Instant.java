package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant {

    @Test
    public void factory_of_Instant() {
        Instant epochPlusTwoNanos = Instant.ofEpochSecond(0, 2);

        UtcInstant test = UtcInstant.of(epochPlusTwoNanos);

        assertEquals(40587, test.getModifiedJulianDay());
        assertEquals(2, test.getNanoOfDay());
    }
}
