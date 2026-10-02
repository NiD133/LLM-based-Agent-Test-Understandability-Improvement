package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testEmpty extends AnnotationTestUtil {

    // Renamed from EMPTY to avoid shadowing JsonSetter.Value.EMPTY constant
    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void testEmpty_valueNullsIsDefault() {
        assertEquals(Nulls.DEFAULT, emptyValue.getValueNulls(),
                "Empty JsonSetter.Value should use DEFAULT null handling for value nulls");
    }

    @Test
    public void testEmpty_contentNullsIsDefault() {
        assertEquals(Nulls.DEFAULT, emptyValue.getContentNulls(),
                "Empty JsonSetter.Value should use DEFAULT null handling for content nulls");
    }

    @Test
    public void testEmpty_valueForIsJsonSetterAnnotation() {
        assertEquals(JsonSetter.class, emptyValue.valueFor(),
                "Empty JsonSetter.Value should be associated with the JsonSetter annotation type");
    }

    @Test
    public void testEmpty_nonDefaultValueNullsIsNullWhenDefault() {
        assertNull(emptyValue.nonDefaultValueNulls(),
                "nonDefaultValueNulls() should return null when value nulls is DEFAULT");
    }

    @Test
    public void testEmpty_nonDefaultContentNullsIsNullWhenDefault() {
        assertNull(emptyValue.nonDefaultContentNulls(),
                "nonDefaultContentNulls() should return null when content nulls is DEFAULT");
    }
}
