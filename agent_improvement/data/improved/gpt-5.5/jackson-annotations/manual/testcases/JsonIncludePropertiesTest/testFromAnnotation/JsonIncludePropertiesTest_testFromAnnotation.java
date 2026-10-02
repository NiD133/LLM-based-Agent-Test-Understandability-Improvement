package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    @JsonIncludeProperties({"foo", "bar"})
    private static class Bogus { }

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testFromAnnotation() {
        JsonIncludeProperties annotation = Bogus.class.getAnnotation(JsonIncludeProperties.class);
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(annotation);

        assertNotNull(value);

        Set<String> includedProperties = value.getIncluded();
        assertEquals(2, value.getIncluded().size());
        assertEquals(_set("foo", "bar"), includedProperties);
        assertNull(value.getOrdered());

        String description = value.toString();
        boolean descriptionStartsWithFoo =
                description.equals("JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean descriptionStartsWithBar =
                description.equals("JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(descriptionStartsWithFoo || descriptionStartsWithBar);

        JsonIncludeProperties.Value valueFromSameAnnotation = JsonIncludeProperties.Value.from(
                Bogus.class.getAnnotation(JsonIncludeProperties.class));
        assertEquals(value, valueFromSameAnnotation);

        byte[] serializedValue = jdkSerialize(value);
        JsonIncludeProperties.Value deserializedValue = jdkDeserialize(serializedValue);
        assertEquals(value, deserializedValue);
    }
}
