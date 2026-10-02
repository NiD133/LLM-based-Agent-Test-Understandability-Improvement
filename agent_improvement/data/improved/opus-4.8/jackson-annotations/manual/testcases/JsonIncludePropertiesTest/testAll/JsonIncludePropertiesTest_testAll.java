package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the behaviour of the "include everything" default value,
 * {@link JsonIncludeProperties.Value#all()}, which represents an
 * undefined include-set (no explicit property filtering).
 */
public class JsonIncludePropertiesTest_testAll extends AnnotationTestUtil {

    /** The canonical "include all properties" value under test. */
    private final JsonIncludeProperties.Value includeAll = JsonIncludeProperties.Value.all();

    @Test
    public void testAll() {
        // from(null) must return the shared ALL singleton, not a fresh copy.
        assertSame(includeAll, JsonIncludeProperties.Value.from(null));

        // "Include all" means neither an explicit property set nor an order is defined.
        assertNull(includeAll.getIncluded());
        assertNull(includeAll.getOrdered());

        // Value is equal to itself.
        assertEquals(includeAll, includeAll);

        // toString and hashCode reflect the null (undefined) state.
        assertEquals("JsonIncludeProperties.Value(included=null,ordered=null)", includeAll.toString());
        assertEquals(0, includeAll.hashCode());
    }
}
