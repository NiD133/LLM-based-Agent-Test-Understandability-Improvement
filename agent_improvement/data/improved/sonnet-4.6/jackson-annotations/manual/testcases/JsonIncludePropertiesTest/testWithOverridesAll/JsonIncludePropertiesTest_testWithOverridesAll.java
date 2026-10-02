package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that overriding a restricted Value with ALL (no restriction)
 * leaves the original property inclusion set unchanged, because ALL has
 * a null included set which acts as "undefined" and does not narrow it.
 */
public class JsonIncludePropertiesTest_testWithOverridesAll extends AnnotationTestUtil {

    // A class restricted to only "foo" and "bar" properties
    @JsonIncludeProperties(value = {"foo", "bar"})
    private static final class Bogus {}

    // ALL means "no restriction" – getIncluded() returns null
    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }

    @Test
    public void testWithOverridesAll() {
        // Build a Value that restricts inclusion to {"foo", "bar"}
        JsonIncludeProperties.Value restricted = JsonIncludeProperties.Value.from(
                Bogus.class.getAnnotation(JsonIncludeProperties.class));

        // Overriding with ALL (undefined restriction) should be a no-op:
        // when the override has no constraint (null included), withOverrides returns the original.
        JsonIncludeProperties.Value result = restricted.withOverrides(ALL);

        Set<String> included = result.getIncluded();
        assertEquals(2, included.size());
        assertEquals(_set("foo", "bar"), included);
    }
}
