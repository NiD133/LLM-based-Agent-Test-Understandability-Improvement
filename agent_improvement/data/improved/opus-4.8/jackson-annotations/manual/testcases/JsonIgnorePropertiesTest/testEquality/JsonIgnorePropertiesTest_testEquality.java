package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies the {@code equals} contract of {@link JsonIgnoreProperties.Value},
 * focusing on how the {@code merge} flag participates in equality.
 */
public class JsonIgnorePropertiesTest_testEquality extends AnnotationTestUtil {

    /** Default instance; note that {@code empty()} already has {@code merge == true}. */
    private final JsonIgnoreProperties.Value emptyWithMerge = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEquality() {
        // A value must equal itself.
        assertEquals(emptyWithMerge, emptyWithMerge);

        // The empty instance already enables merge, so withMerge() is a no-op
        // that returns the very same cached instance.
        assertSame(emptyWithMerge, emptyWithMerge.withMerge());

        // Flipping merge off produces a distinct value that still equals itself...
        JsonIgnoreProperties.Value emptyWithoutMerge = emptyWithMerge.withoutMerge();
        assertEquals(emptyWithoutMerge, emptyWithoutMerge);

        // ...but is not equal to the merge-enabled instance, in either direction.
        assertNotEquals(emptyWithMerge, emptyWithoutMerge);
        assertNotEquals(emptyWithoutMerge, emptyWithMerge);
    }
}
