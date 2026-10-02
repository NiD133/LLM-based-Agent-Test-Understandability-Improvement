package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the static factory methods of {@link JsonIgnoreProperties.Value} and how the
 * "allow getters" / "allow setters" flags affect which properties are reported as ignored
 * for serialization versus deserialization.
 */
public class JsonIgnorePropertiesTest_testFactories extends AnnotationTestUtil {

    /** The canonical shared "no settings" instance that factory methods return when given empty input. */
    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    /** Builds an ordered set from the given names, matching the type returned by the Value getters. */
    private Set<String> setOf(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testFactories() {
        // Factory calls that carry no actual settings should all reuse the shared EMPTY instance.
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoreUnknown(false));
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties());
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties(Collections.<String>emptySet()));

        // A value built from two ignored property names exposes exactly those names.
        JsonIgnoreProperties.Value ignoringAandB = JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        assertEquals(setOf("a", "b"), ignoringAandB.getIgnored());

        // "allow getters": getters stay active, so the names are ignored only for deserialization.
        JsonIgnoreProperties.Value allowingGetters = ignoringAandB.withAllowGetters();
        assertTrue(allowingGetters.getAllowGetters());
        assertFalse(allowingGetters.getAllowSetters());
        assertEquals(setOf("a", "b"), allowingGetters.getIgnored());
        assertEquals(setOf("a", "b"), allowingGetters.findIgnoredForDeserialization());
        assertEquals(setOf(), allowingGetters.findIgnoredForSerialization());

        // "allow setters": setters stay active, so the names are ignored only for serialization.
        JsonIgnoreProperties.Value allowingSetters = ignoringAandB.withAllowSetters();
        assertFalse(allowingSetters.getAllowGetters());
        assertTrue(allowingSetters.getAllowSetters());
        assertEquals(setOf("a", "b"), allowingSetters.getIgnored());
        assertEquals(setOf(), allowingSetters.findIgnoredForDeserialization());
        assertEquals(setOf("a", "b"), allowingSetters.findIgnoredForSerialization());
    }
}
