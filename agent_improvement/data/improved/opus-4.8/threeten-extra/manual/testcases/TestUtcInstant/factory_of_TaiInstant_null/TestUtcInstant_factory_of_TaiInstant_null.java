package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#of(TaiInstant)} rejects a {@code null} argument.
 */
public class TestUtcInstant_factory_of_TaiInstant_null {

    @Test
    public void factory_of_TaiInstant_null() {
        // Passing a null TaiInstant to the factory must throw NullPointerException.
        //noinspection DataFlowIssue - deliberately passing null to verify the null check
        assertThrows(NullPointerException.class, () -> UtcInstant.of((TaiInstant) null));
    }
}
