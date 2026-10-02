package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#of(Instant)} rejects a null instant.
 */
public class TestUtcInstant_factory_of_Instant_null {

    @Test
    public void factory_of_Instant_null() {
        // Passing a null Instant to the factory must fail fast with a NullPointerException.
        //noinspection DataFlowIssue - intentionally testing the null argument
        assertThrows(NullPointerException.class, () -> UtcInstant.of((Instant) null));
    }
}
