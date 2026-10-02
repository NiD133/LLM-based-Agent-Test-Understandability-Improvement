package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that overriding an annotation-derived {@link JsonIncludeProperties.Value}
 * with the "include all" value leaves the original included properties unchanged.
 *
 * <p>By contract, {@code withOverrides(all())} is a no-op: overriding with an
 * "undefined" value (one whose {@code getIncluded()} returns {@code null}) returns
 * the original value as-is.
 */
public class JsonIncludePropertiesTest_testWithOverridesAll extends AnnotationTestUtil {

    /** Fixture annotated to include exactly the properties "foo" and "bar". */
    @JsonIncludeProperties(value = {"foo", "bar"})
    private static final class FooBarBean {
    }

    /** The "include all properties" value, used here as the override. */
    private final JsonIncludeProperties.Value includeAll = JsonIncludeProperties.Value.all();

    @Test
    public void testWithOverridesAll() {
        // Start from the value declared on the fixture: include {foo, bar}.
        JsonIncludeProperties.Value fooBar = JsonIncludeProperties.Value.from(
                FooBarBean.class.getAnnotation(JsonIncludeProperties.class));

        // Overriding with "include all" should not narrow the set; foo and bar remain.
        JsonIncludeProperties.Value result = fooBar.withOverrides(includeAll);

        Set<String> included = result.getIncluded();
        assertEquals(2, included.size());
        assertEquals(asSet("foo", "bar"), included);
    }

    private Set<String> asSet(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }
}
