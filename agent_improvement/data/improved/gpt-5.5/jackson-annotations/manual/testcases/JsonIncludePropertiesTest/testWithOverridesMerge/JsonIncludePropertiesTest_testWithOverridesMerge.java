package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonIncludePropertiesTest_testWithOverridesMerge extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value allProperties = JsonIncludeProperties.Value.all();

    @JsonIncludeProperties({ "foo", "bar" })
    private static class Bogus { }

    private Set<String> includedProperties(String... propertyNames) {
        return new LinkedHashSet<String>(Arrays.asList(propertyNames));
    }

    @Test
    public void testWithOverridesMerge() {
        JsonIncludeProperties annotation = Bogus.class.getAnnotation(JsonIncludeProperties.class);
        JsonIncludeProperties.Value baseInclusions = JsonIncludeProperties.Value.from(annotation);
        JsonIncludeProperties.Value overrideInclusions = new JsonIncludeProperties.Value(includedProperties("foo"), false);

        JsonIncludeProperties.Value mergedInclusions = baseInclusions.withOverrides(overrideInclusions);

        Set<String> included = mergedInclusions.getIncluded();
        Set<String> expectedIncluded = includedProperties("foo");
        assertEquals(1, included.size());
        assertEquals(expectedIncluded, included);
    }
}
