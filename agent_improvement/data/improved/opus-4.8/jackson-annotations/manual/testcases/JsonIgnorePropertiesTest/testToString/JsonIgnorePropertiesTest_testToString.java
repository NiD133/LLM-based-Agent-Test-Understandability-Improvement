package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Verifies the {@link Object#toString()} and {@link Object#hashCode()}
 * implementations of {@link JsonIgnoreProperties.Value}.
 */
public class JsonIgnorePropertiesTest_testToString extends AnnotationTestUtil {

    /** The default "empty" configuration used as the starting point for each case. */
    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testToString() {
        // Start from EMPTY and enable allowSetters and merge; toString() should
        // report every flag, with allowSetters=true and merge=true reflecting the changes.
        JsonIgnoreProperties.Value value = EMPTY.withAllowSetters().withMerge();

        assertEquals(
                "JsonIgnoreProperties.Value("
                        + "ignored=[],"
                        + "ignoreUnknown=false,"
                        + "allowGetters=false,"
                        + "allowSetters=true,"
                        + "merge=true)",
                value.toString());
    }

    @Test
    public void testHashCodeIsNonZero() {
        // No precise expected value, but the implementation should not collapse to 0.
        assertNotEquals(0, EMPTY.hashCode(), "Should not get 0 for hash");
    }
}
