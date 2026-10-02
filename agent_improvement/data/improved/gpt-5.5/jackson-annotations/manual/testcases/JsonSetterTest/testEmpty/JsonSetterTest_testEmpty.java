package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonSetterTest_testEmpty extends AnnotationTestUtil {

    private final JsonSetter.Value emptySetterValue = JsonSetter.Value.empty();

    @Test
    public void testEmpty() {
        assertDefaultNullHandling();
        assertAnnotationType();
        assertNonDefaultAccessorsAreUnset();
    }

    private void assertDefaultNullHandling() {
        assertEquals(Nulls.DEFAULT, emptySetterValue.getValueNulls());
        assertEquals(Nulls.DEFAULT, emptySetterValue.getContentNulls());
    }

    private void assertAnnotationType() {
        assertEquals(JsonSetter.class, emptySetterValue.valueFor());
    }

    private void assertNonDefaultAccessorsAreUnset() {
        assertNull(emptySetterValue.nonDefaultValueNulls());
        assertNull(emptySetterValue.nonDefaultContentNulls());
    }
}
