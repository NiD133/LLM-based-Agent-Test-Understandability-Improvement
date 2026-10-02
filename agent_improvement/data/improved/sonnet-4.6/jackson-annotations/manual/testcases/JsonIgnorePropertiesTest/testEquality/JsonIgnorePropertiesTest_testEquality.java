package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testEquality extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEquality() {
        // A value should always be equal to itself (reflexive equality)
        assertEquals(EMPTY, EMPTY, "EMPTY should equal itself");

        // EMPTY already has merge=true, so withMerge() must return the exact same instance
        assertSame(EMPTY, EMPTY.withMerge(),
                "withMerge() on a Value that already has merge=true should return the same instance");

        // Create a variant that differs only in merge=false
        JsonIgnoreProperties.Value valueWithoutMerge = EMPTY.withoutMerge();

        // The new value should be equal to itself
        assertEquals(valueWithoutMerge, valueWithoutMerge,
                "valueWithoutMerge should equal itself");

        // EMPTY (merge=true) and valueWithoutMerge (merge=false) must not be equal in either direction
        assertFalse(EMPTY.equals(valueWithoutMerge),
                "EMPTY (merge=true) should not equal valueWithoutMerge (merge=false)");
        assertFalse(valueWithoutMerge.equals(EMPTY),
                "valueWithoutMerge (merge=false) should not equal EMPTY (merge=true)");
    }
}
