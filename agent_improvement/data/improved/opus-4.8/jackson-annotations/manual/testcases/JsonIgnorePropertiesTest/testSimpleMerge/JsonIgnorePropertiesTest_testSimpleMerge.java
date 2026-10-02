package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies how {@link JsonIgnoreProperties.Value} instances are combined,
 * which depends on whether the "overrides" instance has merging enabled.
 */
public class JsonIgnorePropertiesTest_testSimpleMerge extends AnnotationTestUtil {

    /** Shared starting point: the default value with no settings applied. */
    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testSimpleMerge() {
        // Base settings: ignore unknown properties and allow getters.
        JsonIgnoreProperties.Value base = EMPTY.withIgnoreUnknown().withAllowGetters();

        // Overrides that opt INTO merging and add an ignored property "a".
        JsonIgnoreProperties.Value mergingOverrides = EMPTY.withMerge().withIgnored("a");

        // Overrides that opt OUT of merging (so they replace rather than combine).
        JsonIgnoreProperties.Value replacingOverrides = EMPTY.withoutMerge();

        // --- Case 1: merging enabled -> result is the union of both instances ---
        JsonIgnoreProperties.Value merged = base.withOverrides(mergingOverrides);
        assertEquals(Collections.singleton("a"), merged.getIgnored(), "ignored set should be the union");
        assertTrue(merged.getIgnoreUnknown(), "ignoreUnknown carried over from base");
        assertTrue(merged.getAllowGetters(), "allowGetters carried over from base");
        assertFalse(merged.getAllowSetters(), "allowSetters was never enabled on either side");

        // --- Case 2: merging disabled -> overrides simply replace the base ---
        JsonIgnoreProperties.Value replaced = JsonIgnoreProperties.Value.merge(base, replacingOverrides);
        assertEquals(Collections.emptySet(), replaced.getIgnored(), "base ignored set is discarded");
        assertFalse(replaced.getIgnoreUnknown(), "base ignoreUnknown is discarded");
        assertFalse(replaced.getAllowGetters(), "base allowGetters is discarded");
        assertFalse(replaced.getAllowSetters());
        // Non-merging overrides are used verbatim, so the result equals the overrides instance.
        assertEquals(replacingOverrides, replaced);

        // Applying empty/null overrides is a no-op and returns the same instance.
        assertSame(replacingOverrides, replacingOverrides.withOverrides(null));
        assertSame(replacingOverrides, replacingOverrides.withOverrides(EMPTY));
    }
}
