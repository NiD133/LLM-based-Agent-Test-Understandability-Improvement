package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant_null {

    @Test
    public void factory_of_Instant_null() {
        assertThrows(NullPointerException.class, () -> UtcInstant.of((Instant) null));
    }
}
