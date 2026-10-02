package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;

public class TestHalf_test_interfaces {

    @Test
    public void test_interfaces() {
        assertTrue(Enum.class.isAssignableFrom(Half.class), "Half must remain an enum");
        assertTrue(Serializable.class.isAssignableFrom(Half.class), "Half must remain serializable");
        assertTrue(Comparable.class.isAssignableFrom(Half.class), "Half must remain comparable");
        assertTrue(TemporalAccessor.class.isAssignableFrom(Half.class), "Half must remain a temporal accessor");
    }
}
