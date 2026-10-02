package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies how {@link JsonIncludeProperties.Value#withOverrides} merges two
 * "included properties" sets: the result must be the intersection of the
 * original set and the override set.
 */
public class JsonIncludePropertiesTest_testWithOverridesMerge extends AnnotationTestUtil {

    /** Sample type whose annotation includes the properties "foo" and "bar". */
    @JsonIncludeProperties({"foo", "bar"})
    private static final class FooAndBar { }

    /** Builds an ordered set from the given property names, for concise assertions. */
    private Set<String> setOf(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testWithOverridesMerge() {
        // Base value derived from the annotation: includes "foo" and "bar".
        JsonIncludeProperties.Value base = JsonIncludeProperties.Value.from(
                FooAndBar.class.getAnnotation(JsonIncludeProperties.class));

        // Override that only includes "foo".
        JsonIncludeProperties.Value override =
                new JsonIncludeProperties.Value(setOf("foo"), false);

        // Merging keeps only the properties present in both sets: {"foo", "bar"} ∩ {"foo"} = {"foo"}.
        JsonIncludeProperties.Value merged = base.withOverrides(override);

        Set<String> included = merged.getIncluded();
        assertEquals(1, included.size());
        assertEquals(setOf("foo"), included);
    }
}
