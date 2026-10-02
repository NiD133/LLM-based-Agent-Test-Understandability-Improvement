package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testOrderedEquality extends AnnotationTestUtil {

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testOrderedEquality() {
        // Equality of JsonIncludeProperties.Value depends on both the included property set
        // and the 'ordered' flag. Values with identical property sets but differing 'ordered'
        // flags (TRUE, FALSE, or null) must not be equal; identical flags must be equal.
        Set<String> properties = _set("a", "b");

        JsonIncludeProperties.Value valueOrderedTrue        = new JsonIncludeProperties.Value(properties, Boolean.TRUE);
        JsonIncludeProperties.Value valueOrderedFalse       = new JsonIncludeProperties.Value(properties, Boolean.FALSE);
        JsonIncludeProperties.Value anotherValueOrderedTrue = new JsonIncludeProperties.Value(properties, Boolean.TRUE);
        JsonIncludeProperties.Value valueOrderedNull        = new JsonIncludeProperties.Value(properties, null);

        // Different 'ordered' values => not equal
        assertNotEquals(valueOrderedTrue,  valueOrderedFalse);
        assertNotEquals(valueOrderedTrue,  valueOrderedNull);
        assertNotEquals(valueOrderedFalse, valueOrderedNull);

        // Same property set and same 'ordered' value (both TRUE) => equal
        assertEquals(valueOrderedTrue, anotherValueOrderedTrue);
    }
}
