package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that {@link JsonIncludeProperties.Value#from} correctly reads a
 * {@code @JsonIncludeProperties} annotation that also declares {@code order = true}.
 * <p>
 * The {@link Ordered} fixture below is annotated with three included property names
 * and {@code order = OptBoolean.TRUE}.
 */
public class JsonIncludePropertiesTest_testFromAnnotationOrdered extends AnnotationTestUtil {

    /** Fixture: includes three properties and requests that their order be preserved. */
    @JsonIncludeProperties(value = {"a", "b", "c"}, order = OptBoolean.TRUE)
    private static class Ordered {
    }

    @Test
    public void testFromAnnotationOrdered() {
        JsonIncludeProperties annotation = Ordered.class.getAnnotation(JsonIncludeProperties.class);

        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(annotation);

        assertNotNull(value);
        // The Ordered fixture lists exactly three properties to include...
        assertEquals(3, value.getIncluded().size());
        // ...and enables ordering, so getOrdered() reflects order = true.
        assertEquals(Boolean.TRUE, value.getOrdered());
    }
}
