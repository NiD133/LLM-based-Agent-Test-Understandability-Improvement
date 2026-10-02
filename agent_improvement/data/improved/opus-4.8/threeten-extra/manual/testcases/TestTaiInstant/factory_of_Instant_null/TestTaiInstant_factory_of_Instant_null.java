package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#of(Instant)} rejects a {@code null} instant.
 */
public class TestTaiInstant_factory_of_Instant_null {

    @Test
    public void factory_of_Instant_null_throwsNullPointerException() {
        Instant nullInstant = null;
        // The factory must reject a null Instant rather than dereferencing it.
        //noinspection DataFlowIssue - intentionally passing null to verify the guard
        assertThrows(NullPointerException.class, () -> TaiInstant.of(nullInstant));
    }
}
