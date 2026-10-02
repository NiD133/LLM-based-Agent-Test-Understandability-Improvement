package com.fasterxml.jackson.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class JsonIgnorePropertiesTest_testSimpleMerge extends AnnotationTestUtil {

    private static final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testSimpleMerge() {
        JsonIgnoreProperties.Value base = EMPTY.withIgnoreUnknown().withAllowGetters();
        JsonIgnoreProperties.Value mergingOverride = EMPTY.withMerge().withIgnored("a");
        JsonIgnoreProperties.Value replacingOverride = EMPTY.withoutMerge();

        JsonIgnoreProperties.Value merged = base.withOverrides(mergingOverride);
        assertEquals(Collections.singleton("a"), merged.getIgnored());
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());

        JsonIgnoreProperties.Value replaced = JsonIgnoreProperties.Value.merge(base, replacingOverride);
        assertEquals(Collections.emptySet(), replaced.getIgnored());
        assertFalse(replaced.getIgnoreUnknown());
        assertFalse(replaced.getAllowGetters());
        assertFalse(replaced.getAllowSetters());

        assertEquals(replacingOverride, replaced);
        assertSame(replacingOverride, replacingOverride.withOverrides(null));
        assertSame(replacingOverride, replacingOverride.withOverrides(EMPTY));
    }
}
