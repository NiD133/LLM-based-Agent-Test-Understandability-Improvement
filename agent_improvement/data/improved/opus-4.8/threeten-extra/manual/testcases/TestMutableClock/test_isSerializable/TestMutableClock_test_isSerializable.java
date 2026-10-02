package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MutableClock} supports Java serialization.
 */
public class TestMutableClock_test_isSerializable {

    @Test
    public void test_isSerializable() {
        // MutableClock must implement Serializable so it can be persisted/transmitted.
        boolean mutableClockIsSerializable =
                Serializable.class.isAssignableFrom(MutableClock.class);

        assertTrue(mutableClockIsSerializable,
                "MutableClock should implement Serializable");
    }
}
