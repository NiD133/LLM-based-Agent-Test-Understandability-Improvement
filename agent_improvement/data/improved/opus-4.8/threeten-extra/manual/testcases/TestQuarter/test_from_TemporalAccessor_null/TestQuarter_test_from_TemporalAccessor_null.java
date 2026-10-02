package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#from(TemporalAccessor)} rejects a {@code null} argument
 * by throwing a {@link NullPointerException}.
 */
public class TestQuarter_test_from_TemporalAccessor_null {

    @Test
    public void from_nullTemporalAccessor_throwsNullPointerException() {
        TemporalAccessor nullTemporal = null;
        assertThrows(NullPointerException.class, () -> Quarter.from(nullTemporal));
    }
}
