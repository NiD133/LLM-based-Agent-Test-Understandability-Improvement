package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonIgnorePropertiesTest_testEmpty extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEmpty() {
        assertSame(emptyValue, JsonIgnoreProperties.Value.from(null));
        assertEquals(0, emptyValue.getIgnored().size());
        assertFalse(emptyValue.getAllowGetters());
        assertFalse(emptyValue.getAllowSetters());
    }
}
