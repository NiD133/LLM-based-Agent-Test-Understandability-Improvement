package com.fasterxml.jackson.annotation;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the "mutant factory" methods on {@link JsonIgnoreProperties.Value}.
 * Each {@code withXxx()} / {@code withoutXxx()} method is expected to return a
 * new Value derived from the default (empty) instance, with exactly one setting
 * changed.
 */
public class JsonIgnorePropertiesTest_testMutantFactories extends AnnotationTestUtil {

    /** Default starting point: no ignored properties and all flags at their defaults. */
    private final JsonIgnoreProperties.Value defaults = JsonIgnoreProperties.Value.empty();

    @Test
    public void withIgnoredAddsTheGivenPropertyNames() {
        // Varargs overload: two names -> two ignored properties.
        assertEquals(2, defaults.withIgnored("a", "b").getIgnored().size());

        // Set overload: a single-element set -> one ignored property.
        assertEquals(1, defaults.withIgnored(Collections.singleton("x")).getIgnored().size());

        // A null set is treated as "no ignored properties".
        assertEquals(0, defaults.withIgnored((Set<String>) null).getIgnored().size());
    }

    @Test
    public void withAndWithoutIgnoreUnknownTogglesTheFlag() {
        assertTrue(defaults.withIgnoreUnknown().getIgnoreUnknown());
        assertFalse(defaults.withoutIgnoreUnknown().getIgnoreUnknown());
    }

    @Test
    public void withAndWithoutAllowGettersTogglesTheFlag() {
        assertTrue(defaults.withAllowGetters().getAllowGetters());
        assertFalse(defaults.withoutAllowGetters().getAllowGetters());
    }

    @Test
    public void withAndWithoutAllowSettersTogglesTheFlag() {
        assertTrue(defaults.withAllowSetters().getAllowSetters());
        assertFalse(defaults.withoutAllowSetters().getAllowSetters());
    }

    @Test
    public void withAndWithoutMergeTogglesTheFlag() {
        assertTrue(defaults.withMerge().getMerge());
        assertFalse(defaults.withoutMerge().getMerge());
    }
}
