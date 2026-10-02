package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#from(TemporalAccessor)} rejects a {@code null} argument.
 */
public class TestHalf_test_from_TemporalAccessor_null {

    @Test
    public void from_nullTemporalAccessor_throwsNullPointerException() {
        // Half.from must reject null rather than silently accepting it.
        assertThrows(NullPointerException.class, () -> Half.from((TemporalAccessor) null));
    }
}
