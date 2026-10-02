package com.fasterxml.jackson.annotation;

import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonIncludeProperties.Value#from} correctly reads both
 * the property names and the {@code order} flag from a {@link JsonIncludeProperties}
 * annotation when that flag is set to {@link OptBoolean#TRUE}.
 */
public class JsonIncludePropertiesTest_testFromAnnotationOrdered extends AnnotationTestUtil {

    /**
     * Fixture class carrying an annotation that lists three properties in a
     * specific order and marks them as defining the serialization order.
     */
    @JsonIncludeProperties(order = OptBoolean.TRUE, value = {"id", "code", "name"})
    private static final class ThreePropertiesOrdered { }

    @Test
    public void testFromAnnotationOrdered() {
        // Build a Value from the annotation placed on ThreePropertiesOrdered.
        JsonIncludeProperties ann = ThreePropertiesOrdered.class.getAnnotation(JsonIncludeProperties.class);
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(ann);

        assertNotNull(value, "Value.from() must not return null for a non-null annotation");

        Set<String> includedProperties = value.getIncluded();
        assertEquals(3, includedProperties.size(),
                "Expected exactly three included properties: 'id', 'code', and 'name'");

        // The annotation had order = OptBoolean.TRUE, so getOrdered() must reflect that.
        assertEquals(Boolean.TRUE, value.getOrdered(),
                "getOrdered() must return TRUE when the annotation sets order = OptBoolean.TRUE");
    }
}
