package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestHalf_test_interfaces {

    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(Half.class));
        assertTrue(Serializable.class.isAssignableFrom(Half.class));
        assertTrue(Comparable.class.isAssignableFrom(Half.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(Half.class));
    }
}
