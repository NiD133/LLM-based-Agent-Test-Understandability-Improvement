package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludePropertiesTest_testWithOverridesMerge extends AnnotationTestUtil {

    // Source for the base Value: annotated with both "foo" and "bar"
    @JsonIncludeProperties(value = {"foo", "bar"})
    private static final class WithFooAndBar {}

    private Set<String> setOf(String... names) {
        return new LinkedHashSet<>(Arrays.asList(names));
    }

    @Test
    public void testWithOverridesMerge() {
        // Base Value includes {"foo", "bar"}; the override restricts to only {"foo"}.
        // withOverrides computes the intersection, so only "foo" survives.
        JsonIncludeProperties.Value base =
            JsonIncludeProperties.Value.from(WithFooAndBar.class.getAnnotation(JsonIncludeProperties.class));
        JsonIncludeProperties.Value merged =
            base.withOverrides(new JsonIncludeProperties.Value(setOf("foo"), false));

        Set<String> included = merged.getIncluded();
        assertEquals(1, included.size());
        assertEquals(setOf("foo"), included);
    }
}
