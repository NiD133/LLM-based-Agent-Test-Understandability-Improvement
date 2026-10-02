package com.fasterxml.jackson.annotation;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how {@link JsonIncludeProperties.Value#withOverrides} merges the
 * "included properties" set when the override declares an <em>empty</em> set.
 * <p>
 * Per the merge contract, the resulting included set is the intersection of the
 * base set and the override set. Intersecting any set with an empty set yields
 * an empty set, so no properties should remain included.
 */
public class JsonIncludePropertiesTest_testWithOverridesEmpty extends AnnotationTestUtil {

    /** Helper type whose annotation provides the base set of included properties. */
    @JsonIncludeProperties({"foo", "bar"})
    private final static class Bogus {
    }

    @Test
    public void testWithOverridesEmpty() {
        // Base value: the included properties declared on the Bogus class' annotation.
        JsonIncludeProperties.Value base = JsonIncludeProperties.Value.from(
                Bogus.class.getAnnotation(JsonIncludeProperties.class));

        // Override with an explicit empty include set ("ordered" = false).
        JsonIncludeProperties.Value emptyOverride =
                new JsonIncludeProperties.Value(Collections.<String>emptySet(), false);
        JsonIncludeProperties.Value merged = base.withOverrides(emptyOverride);

        // Intersection with an empty set leaves no included properties.
        Set<String> included = merged.getIncluded();
        assertEquals(0, included.size());
    }
}
