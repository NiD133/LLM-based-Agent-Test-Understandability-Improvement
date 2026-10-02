package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonIncludePropertiesTest_testAll extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value allPropertiesIncluded = JsonIncludeProperties.Value.all();

    @Test
    public void testAll() {
        assertSame(allPropertiesIncluded, JsonIncludeProperties.Value.from(null));
        assertNull(allPropertiesIncluded.getIncluded());
        assertNull(allPropertiesIncluded.getOrdered());
        assertEquals(allPropertiesIncluded, allPropertiesIncluded);
        assertEquals("JsonIncludeProperties.Value(included=null,ordered=null)",
                allPropertiesIncluded.toString());
        assertEquals(0, allPropertiesIncluded.hashCode());
    }
}
