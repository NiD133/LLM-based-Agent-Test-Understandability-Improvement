package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testFactories extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    // Verify that factory methods with "no-op" arguments all return the canonical EMPTY singleton
    @Test
    public void testEmptyFactoryMethodsReturnSameInstance() {
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoreUnknown(false));
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties());
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties(Collections.<String>emptySet()));
    }

    // Verify that forIgnoredProperties with property names produces a Value with those names recorded
    @Test
    public void testForIgnoredPropertiesStoresGivenPropertyNames() {
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        assertEquals(_set("a", "b"), value.getIgnored());
    }

    // withAllowGetters() means getters are NOT ignored: ignored list applies only to deserialization,
    // so findIgnoredForSerialization() returns empty and findIgnoredForDeserialization() returns the full set
    @Test
    public void testWithAllowGetters_ignoredPropertiesApplyOnlyToDeserialization() {
        JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        JsonIgnoreProperties.Value allowGetters = base.withAllowGetters();

        assertTrue(allowGetters.getAllowGetters());
        assertFalse(allowGetters.getAllowSetters());
        assertEquals(_set("a", "b"), allowGetters.getIgnored());
        assertEquals(_set("a", "b"), allowGetters.findIgnoredForDeserialization());
        assertEquals(_set(), allowGetters.findIgnoredForSerialization());
    }

    // withAllowSetters() means setters are NOT ignored: ignored list applies only to serialization,
    // so findIgnoredForDeserialization() returns empty and findIgnoredForSerialization() returns the full set
    @Test
    public void testWithAllowSetters_ignoredPropertiesApplyOnlyToSerialization() {
        JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        JsonIgnoreProperties.Value allowSetters = base.withAllowSetters();

        assertFalse(allowSetters.getAllowGetters());
        assertTrue(allowSetters.getAllowSetters());
        assertEquals(_set("a", "b"), allowSetters.getIgnored());
        assertEquals(_set(), allowSetters.findIgnoredForDeserialization());
        assertEquals(_set("a", "b"), allowSetters.findIgnoredForSerialization());
    }
}
