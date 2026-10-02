package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#from(TemporalAccessor)} rejects a null argument.
 */
public class TestAmPm_test_from_TemporalAccessor_null {

    @Test
    public void from_nullTemporalAccessor_throwsNullPointerException() {
        TemporalAccessor nullTemporal = null;
        assertThrows(NullPointerException.class, () -> AmPm.from(nullTemporal));
    }
}
