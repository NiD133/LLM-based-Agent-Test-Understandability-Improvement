package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonIncludePropertiesTest_testWithOverridesAll extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value allPropertiesIncluded = JsonIncludeProperties.Value.all();

    @JsonIncludeProperties({ "foo", "bar" })
    private static class Bogus { }

    private Set<String> orderedSet(String... propertyNames) {
        return new LinkedHashSet<String>(Arrays.asList(propertyNames));
    }

    @Test
    public void testWithOverridesAll() {
        JsonIncludeProperties annotation = Bogus.class.getAnnotation(JsonIncludeProperties.class);
        JsonIncludeProperties.Value annotationIncludes = JsonIncludeProperties.Value.from(annotation);

        JsonIncludeProperties.Value mergedIncludes = annotationIncludes.withOverrides(allPropertiesIncluded);
        Set<String> includedProperties = mergedIncludes.getIncluded();

        assertEquals(2, includedProperties.size());
        assertEquals(orderedSet("foo", "bar"), includedProperties);
    }
}
