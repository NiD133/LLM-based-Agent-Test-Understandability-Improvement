package com.fasterxml.jackson.annotation;

import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testMergeIgnoreProperties extends AnnotationTestUtil {

    @Test
    public void testMergeIgnoreProperties() {
        // Each Value ignores a single distinct property name
        JsonIgnoreProperties.Value ignoringA = JsonIgnoreProperties.Value.empty().withIgnored("a");
        JsonIgnoreProperties.Value ignoringB = JsonIgnoreProperties.Value.empty().withIgnored("b");
        JsonIgnoreProperties.Value ignoringC = JsonIgnoreProperties.Value.empty().withIgnored("c");

        // mergeAll should produce a union of all ignored property names
        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(ignoringA, ignoringB, ignoringC);
        Set<String> ignoredProperties = merged.getIgnored();

        assertEquals(3, ignoredProperties.size());
        assertTrue(ignoredProperties.contains("a"));
        assertTrue(ignoredProperties.contains("b"));
        assertTrue(ignoredProperties.contains("c"));
    }
}
