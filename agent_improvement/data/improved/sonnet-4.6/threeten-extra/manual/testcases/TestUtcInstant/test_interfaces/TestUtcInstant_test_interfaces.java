package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_interfaces {

    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(UtcInstant.class));
        assertTrue(Comparable.class.isAssignableFrom(UtcInstant.class));
    }
}
