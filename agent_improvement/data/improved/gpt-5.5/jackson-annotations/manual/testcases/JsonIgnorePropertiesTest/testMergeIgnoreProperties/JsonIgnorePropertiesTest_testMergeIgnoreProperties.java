package com.fasterxml.jackson.annotation;

import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonIgnorePropertiesTest_testMergeIgnoreProperties extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

    @Test
    public void testMergeIgnoreProperties() {
        JsonIgnoreProperties.Value ignoredA = emptyValue.withIgnored("a");
        JsonIgnoreProperties.Value ignoredB = emptyValue.withIgnored("b");
        JsonIgnoreProperties.Value ignoredC = emptyValue.withIgnored("c");

        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(
                ignoredA, ignoredB, ignoredC);
        Set<String> ignoredProperties = mergedValue.getIgnored();

        assertEquals(3, ignoredProperties.size());
        assertTrue(ignoredProperties.contains("a"));
        assertTrue(ignoredProperties.contains("b"));
        assertTrue(ignoredProperties.contains("c"));
    }
}
