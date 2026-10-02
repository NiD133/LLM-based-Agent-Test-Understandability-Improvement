package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_interfaces {

    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(Quarter.class));
        assertTrue(Serializable.class.isAssignableFrom(Quarter.class));
        assertTrue(Comparable.class.isAssignableFrom(Quarter.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(Quarter.class));
    }
}
