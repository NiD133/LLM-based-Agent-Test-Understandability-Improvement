package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link JsonIgnoreProperties.Value#mergeAll(JsonIgnoreProperties.Value...)}
 * combines the ignored-property names from several values into a single union.
 */
public class JsonIgnorePropertiesTest_testMergeIgnoreProperties extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testMergeIgnoreProperties() {
        // Each Value ignores exactly one distinct property.
        JsonIgnoreProperties.Value ignoresA = EMPTY.withIgnored("a");
        JsonIgnoreProperties.Value ignoresB = EMPTY.withIgnored("b");
        JsonIgnoreProperties.Value ignoresC = EMPTY.withIgnored("c");

        // Merging them should yield the union of all ignored names.
        JsonIgnoreProperties.Value merged =
                JsonIgnoreProperties.Value.mergeAll(ignoresA, ignoresB, ignoresC);

        java.util.Set<String> ignoredNames = merged.getIgnored();
        assertEquals(3, ignoredNames.size(), "merge should keep all three names");
        assertTrue(ignoredNames.contains("a"));
        assertTrue(ignoredNames.contains("b"));
        assertTrue(ignoredNames.contains("c"));
    }
}
