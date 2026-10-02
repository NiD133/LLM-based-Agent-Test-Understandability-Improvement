package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonIncludeProperties.Value#from(JsonIncludeProperties)} correctly
 * reads property names from a {@code @JsonIncludeProperties} annotation, that the resulting
 * {@code Value} reports the right included-set and ordered flag, that its {@code toString}
 * is well-formed, that two calls for the same annotation are equal, and that the value
 * survives a Java-serialization round-trip unchanged.
 */
public class JsonIncludePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    /** Target class carrying the annotation under test: includes exactly "foo" and "bar". */
    @JsonIncludeProperties(value = {"foo", "bar"})
    private static final class Bogus {}

    private Set<String> _set(String... args) {
        return new LinkedHashSet<>(Arrays.asList(args));
    }

    @Test
    public void testFromAnnotation() {
        // Build a Value from the annotation present on Bogus
        JsonIncludeProperties.Value value =
                JsonIncludeProperties.Value.from(Bogus.class.getAnnotation(JsonIncludeProperties.class));

        // The annotation is present, so the result must not be null
        assertNotNull(value);

        // Included set must contain exactly the two properties declared on Bogus
        Set<String> included = value.getIncluded();
        assertEquals(2, included.size(), "Expected exactly two included properties");
        assertEquals(_set("foo", "bar"), included);

        // No 'order' attribute was set, so getOrdered() must be null
        assertNull(value.getOrdered(), "Expected 'ordered' to be null when annotation has no order attribute");

        // toString must report both properties (order is unspecified because the backing set is a HashSet)
        String repr = value.toString();
        boolean fooFirst = repr.equals("JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean barFirst = repr.equals("JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(fooFirst || barFirst,
                "Unexpected toString output: " + repr);

        // Two Values built from the same annotation must be equal
        JsonIncludeProperties.Value valueCopy =
                JsonIncludeProperties.Value.from(Bogus.class.getAnnotation(JsonIncludeProperties.class));
        assertEquals(value, valueCopy, "Two Values from the same annotation must be equal");

        // The Value must survive a JDK serialization round-trip unchanged
        byte[] serialized = jdkSerialize(value);
        JsonIncludeProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized, "Deserialized Value must equal the original");
    }
}
