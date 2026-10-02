package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    // A class annotated to ignore properties "foo" and "bar" — used as annotation source
    @JsonIgnoreProperties(value = {"foo", "bar"})
    private static class BeanWithFooBarIgnored { }

    private Set<String> setOf(String... values) {
        return new LinkedHashSet<>(Arrays.asList(values));
    }

    @Test
    public void testFromAnnotation() throws Exception {
        // Build a Value by reading the @JsonIgnoreProperties annotation on the test class
        JsonIgnoreProperties annotation = BeanWithFooBarIgnored.class.getAnnotation(JsonIgnoreProperties.class);
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(annotation);

        assertNotNull(value);

        // Annotation does not set allowGetters / allowSetters / merge, so all default to false
        assertFalse(value.getMerge());
        assertFalse(value.getAllowGetters());
        assertFalse(value.getAllowSetters());

        // Annotation declares exactly two properties to ignore: "foo" and "bar"
        Set<String> ignoredProperties = value.getIgnored();
        assertEquals(2, ignoredProperties.size());
        assertEquals(setOf("foo", "bar"), ignoredProperties);

        // Confirm the Value survives a JDK serialize / deserialize round-trip unchanged
        byte[] serialized = jdkSerialize(value);
        JsonIgnoreProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }
}
