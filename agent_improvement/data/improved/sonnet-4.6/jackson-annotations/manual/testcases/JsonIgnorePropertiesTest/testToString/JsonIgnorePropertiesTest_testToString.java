package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testToString extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testToString() {
        // Build a Value with allowSetters and merge enabled, then verify its string representation
        JsonIgnoreProperties.Value valueWithSettersAndMerge = EMPTY.withAllowSetters().withMerge();
        String expectedToString = "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=true,merge=true)";
        assertEquals(expectedToString, valueWithSettersAndMerge.toString());

        // Verify hashCode produces a non-zero value (zero would indicate a broken implementation)
        int hash = EMPTY.hashCode();
        assertNotEquals(0, hash, "hashCode() should not return 0 for the empty Value");
    }
}
