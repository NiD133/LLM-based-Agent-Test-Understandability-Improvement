package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_get_null {

    /**
     * Verifies that {@link AmPm#get(java.time.temporal.TemporalField)} throws
     * {@link NullPointerException} when a null field is supplied, in accordance
     * with the {@code TemporalAccessor} contract that disallows null arguments.
     */
    @Test
    public void test_get_null() {
        assertThrows(NullPointerException.class, () -> AmPm.PM.get(null));
    }
}
