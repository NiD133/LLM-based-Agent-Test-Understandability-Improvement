package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonIncludeProperties.Value} equality takes the
 * {@code ordered} flag into account: two values with the same included
 * properties are equal only when their {@code ordered} flags also match.
 */
public class JsonIncludePropertiesTest_testOrderedEquality extends AnnotationTestUtil {

    /** Builds an ordered Set, mirroring how the annotation stores included property names. */
    private Set<String> includedProperties(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testOrderedEquality() {
        // All values share the same included properties ("a", "b") and differ
        // only in the "ordered" flag, so equality is decided solely by that flag.
        JsonIncludeProperties.Value orderedTrue        = new JsonIncludeProperties.Value(includedProperties("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedFalse       = new JsonIncludeProperties.Value(includedProperties("a", "b"), Boolean.FALSE);
        JsonIncludeProperties.Value orderedTrueCopy    = new JsonIncludeProperties.Value(includedProperties("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedUndefined   = new JsonIncludeProperties.Value(includedProperties("a", "b"), null);

        // Different "ordered" flags -> not equal.
        assertNotEquals(orderedTrue, orderedFalse);
        assertNotEquals(orderedTrue, orderedUndefined);
        assertNotEquals(orderedFalse, orderedUndefined);

        // Same "ordered" flag (TRUE) -> equal.
        assertEquals(orderedTrue, orderedTrueCopy);
    }
}
