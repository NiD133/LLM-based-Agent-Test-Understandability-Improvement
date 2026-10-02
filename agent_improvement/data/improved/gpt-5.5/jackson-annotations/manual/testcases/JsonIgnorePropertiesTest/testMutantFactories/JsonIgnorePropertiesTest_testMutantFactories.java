package com.fasterxml.jackson.annotation;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonIgnorePropertiesTest_testMutantFactories extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testMutantFactories() {
        JsonIgnoreProperties.Value withTwoIgnoredProperties = EMPTY.withIgnored("a", "b");
        assertEquals(2, withTwoIgnoredProperties.getIgnored().size());

        JsonIgnoreProperties.Value withOneIgnoredProperty = EMPTY.withIgnored(Collections.singleton("x"));
        assertEquals(1, withOneIgnoredProperty.getIgnored().size());

        JsonIgnoreProperties.Value withNullIgnoredProperties = EMPTY.withIgnored((Set<String>) null);
        assertEquals(0, withNullIgnoredProperties.getIgnored().size());

        assertTrue(EMPTY.withIgnoreUnknown().getIgnoreUnknown());
        assertFalse(EMPTY.withoutIgnoreUnknown().getIgnoreUnknown());

        assertTrue(EMPTY.withAllowGetters().getAllowGetters());
        assertFalse(EMPTY.withoutAllowGetters().getAllowGetters());

        assertTrue(EMPTY.withAllowSetters().getAllowSetters());
        assertFalse(EMPTY.withoutAllowSetters().getAllowSetters());

        assertTrue(EMPTY.withMerge().getMerge());
        assertFalse(EMPTY.withoutMerge().getMerge());
    }
}
