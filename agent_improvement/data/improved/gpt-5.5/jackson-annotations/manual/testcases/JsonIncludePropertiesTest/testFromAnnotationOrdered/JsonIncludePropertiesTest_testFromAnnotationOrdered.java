package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonIncludePropertiesTest_testFromAnnotationOrdered extends AnnotationTestUtil {

    @JsonIncludeProperties(value = { "first", "second", "third" }, order = OptBoolean.TRUE)
    static class Ordered {
    }

    @Test
    public void testFromAnnotationOrdered() {
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(
                Ordered.class.getAnnotation(JsonIncludeProperties.class));

        assertNotNull(value);
        assertEquals(3, value.getIncluded().size());
        assertEquals(Boolean.TRUE, value.getOrdered());
    }
}
