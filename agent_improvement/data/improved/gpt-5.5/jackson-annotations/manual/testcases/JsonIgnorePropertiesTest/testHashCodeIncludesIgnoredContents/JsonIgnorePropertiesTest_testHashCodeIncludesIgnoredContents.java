package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JsonIgnorePropertiesTest_testHashCodeIncludesIgnoredContents extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

    @Test
    public void testHashCodeIncludesIgnoredContents() {
        JsonIgnoreProperties.Value ignoredAAndB = emptyValue.withIgnored("a", "b");
        JsonIgnoreProperties.Value ignoredCAndD = emptyValue.withIgnored("c", "d");

        assertNotEquals(ignoredAAndB, ignoredCAndD);
        assertNotEquals(ignoredAAndB.hashCode(), ignoredCAndD.hashCode());
    }
}
