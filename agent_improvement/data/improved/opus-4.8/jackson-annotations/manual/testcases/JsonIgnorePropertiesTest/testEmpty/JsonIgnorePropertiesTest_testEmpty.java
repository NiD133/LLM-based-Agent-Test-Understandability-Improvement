package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies the behaviour of the canonical "empty" {@link JsonIgnoreProperties.Value}
 * instance returned by {@link JsonIgnoreProperties.Value#empty()}.
 */
public class JsonIgnorePropertiesTest_testEmpty extends AnnotationTestUtil {

    /** The shared, default "empty" Value instance under test. */
    private final JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEmpty() {
        // Building a Value from a null annotation is allowed and must reuse
        // the very same shared EMPTY singleton (reference equality, not just equals()).
        assertSame(emptyValue, JsonIgnoreProperties.Value.from(null));

        // The empty Value carries no configuration:
        assertEquals(0, emptyValue.getIgnored().size(), "empty Value should ignore no properties");
        assertFalse(emptyValue.getAllowGetters(), "empty Value should not allow getters");
        assertFalse(emptyValue.getAllowSetters(), "empty Value should not allow setters");
    }
}
