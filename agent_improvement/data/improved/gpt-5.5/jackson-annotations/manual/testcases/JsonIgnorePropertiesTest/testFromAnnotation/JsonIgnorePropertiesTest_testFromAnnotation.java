package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonIgnorePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @JsonIgnoreProperties({ "foo", "bar" })
    static class Bogus { }

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testFromAnnotation() throws Exception {
        JsonIgnoreProperties annotation = Bogus.class.getAnnotation(JsonIgnoreProperties.class);
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(annotation);

        assertNotNull(value);
        assertFalse(value.getMerge());
        assertFalse(value.getAllowGetters());
        assertFalse(value.getAllowSetters());

        Set<String> ignoredProperties = value.getIgnored();
        assertEquals(2, value.getIgnored().size());
        assertEquals(_set("foo", "bar"), ignoredProperties);

        byte[] serializedValue = jdkSerialize(value);
        JsonIgnoreProperties.Value deserializedValue = jdkDeserialize(serializedValue);
        assertEquals(value, deserializedValue);
    }
}
