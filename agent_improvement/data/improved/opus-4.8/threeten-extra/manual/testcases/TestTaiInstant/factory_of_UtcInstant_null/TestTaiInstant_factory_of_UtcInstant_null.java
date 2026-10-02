package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#of(UtcInstant)} rejects a null argument.
 */
public class TestTaiInstant_factory_of_UtcInstant_null {

    @Test
    public void factory_of_UtcInstant_null_throwsNullPointerException() {
        // The cast disambiguates the overloaded of(...) factory; null is the value under test.
        assertThrows(NullPointerException.class, () -> TaiInstant.of((UtcInstant) null));
    }
}
