package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testFactories extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    private Set<String> expectedIgnoredProperties(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testFactories() {
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoreUnknown(false));
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties());
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties(Collections.<String>emptySet()));

        JsonIgnoreProperties.Value ignoredProperties =
                JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        assertEquals(expectedIgnoredProperties("a", "b"), ignoredProperties.getIgnored());

        JsonIgnoreProperties.Value allowGetters = ignoredProperties.withAllowGetters();
        assertTrue(allowGetters.getAllowGetters());
        assertFalse(allowGetters.getAllowSetters());
        assertEquals(expectedIgnoredProperties("a", "b"), allowGetters.getIgnored());
        assertEquals(expectedIgnoredProperties("a", "b"), allowGetters.findIgnoredForDeserialization());
        assertEquals(expectedIgnoredProperties(), allowGetters.findIgnoredForSerialization());

        JsonIgnoreProperties.Value allowSetters = ignoredProperties.withAllowSetters();
        assertFalse(allowSetters.getAllowGetters());
        assertTrue(allowSetters.getAllowSetters());
        assertEquals(expectedIgnoredProperties("a", "b"), allowSetters.getIgnored());
        assertEquals(expectedIgnoredProperties(), allowSetters.findIgnoredForDeserialization());
        assertEquals(expectedIgnoredProperties("a", "b"), allowSetters.findIgnoredForSerialization());
    }
}
