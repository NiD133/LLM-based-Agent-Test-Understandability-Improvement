package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIncludeProperties.Value#all()}, which represents the "include
 * all properties" sentinel — the default state when no filtering is configured.
 */
public class JsonIncludePropertiesTest_testAll extends AnnotationTestUtil {

    // The canonical "include everything" value: null included-set means no filtering applied.
    private final JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.all();

    /**
     * {@code from(null)} must return the shared ALL singleton, not a new instance,
     * because null annotation source means "no restriction configured".
     */
    @Test
    public void testFromNullReturnsSameAllSingleton() {
        assertSame(allValue, JsonIncludeProperties.Value.from(null));
    }

    /**
     * The ALL value reports a null included-set to signal "no filtering" —
     * null is intentionally distinct from an empty set, which would mean "include nothing".
     */
    @Test
    public void testAllHasNullIncludedSet() {
        assertNull(allValue.getIncluded());
    }

    /**
     * The ALL value reports a null ordered flag to signal "ordering not specified".
     */
    @Test
    public void testAllHasNullOrdered() {
        assertNull(allValue.getOrdered());
    }

    /**
     * The ALL value must be equal to itself (reflexive equality).
     */
    @Test
    public void testAllEqualsItself() {
        assertEquals(allValue, allValue);
    }

    /**
     * The string representation encodes both fields so that log output
     * and diagnostic messages are self-describing.
     */
    @Test
    public void testAllToStringShowsNullFields() {
        assertEquals("JsonIncludeProperties.Value(included=null,ordered=null)", allValue.toString());
    }

    /**
     * The ALL value hashes to 0 because both {@code _included} and {@code _ordered}
     * are null, contributing nothing to the hash.
     */
    @Test
    public void testAllHashCodeIsZero() {
        assertEquals(0, allValue.hashCode());
    }
}
