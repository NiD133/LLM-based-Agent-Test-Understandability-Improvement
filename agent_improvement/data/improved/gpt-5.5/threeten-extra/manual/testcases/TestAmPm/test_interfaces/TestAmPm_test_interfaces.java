package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_interfaces {

    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(AmPm.class));
        assertTrue(Serializable.class.isAssignableFrom(AmPm.class));
        assertTrue(Comparable.class.isAssignableFrom(AmPm.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(AmPm.class));
    }
}
