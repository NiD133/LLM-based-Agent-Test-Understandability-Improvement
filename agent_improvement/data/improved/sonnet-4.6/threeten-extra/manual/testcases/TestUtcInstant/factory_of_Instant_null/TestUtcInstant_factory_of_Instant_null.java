package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_Instant_null {

    /**
     * Verifies that passing a null Instant to UtcInstant.of() throws NullPointerException,
     * as required by the method contract ("not null" parameter).
     */
    @Test
    public void factory_of_Instant_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> UtcInstant.of((Instant) null));
    }
}
