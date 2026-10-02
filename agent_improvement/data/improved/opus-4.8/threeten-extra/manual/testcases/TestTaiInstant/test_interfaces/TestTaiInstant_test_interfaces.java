package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant} exposes the standard interfaces that callers rely on.
 */
public class TestTaiInstant_test_interfaces {

    @Test
    public void test_interfaces() {
        // TaiInstant must be serializable so instances can be persisted or transmitted.
        assertTrue(Serializable.class.isAssignableFrom(TaiInstant.class));
        // TaiInstant must be comparable so instances can be ordered on the time-line.
        assertTrue(Comparable.class.isAssignableFrom(TaiInstant.class));
    }
}
