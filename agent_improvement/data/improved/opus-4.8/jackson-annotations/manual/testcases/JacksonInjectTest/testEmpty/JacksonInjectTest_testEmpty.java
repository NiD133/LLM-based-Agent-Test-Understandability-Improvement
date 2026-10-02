package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the behaviour of the shared "empty" {@link JacksonInject.Value}
 * instance returned by {@link JacksonInject.Value#empty()}.
 */
public class JacksonInjectTest_testEmpty extends AnnotationTestUtil {

    private final JacksonInject.Value emptyValue = JacksonInject.Value.empty();

    @Test
    public void testEmpty() {
        // An empty Value carries no configured id or useInput flag.
        assertNull(emptyValue.getId());
        assertNull(emptyValue.getUseInput());

        // With no explicit useInput, willUseInput simply echoes the caller's default.
        assertTrue(emptyValue.willUseInput(true));
        assertFalse(emptyValue.willUseInput(false));

        // Constructing a Value from all-null arguments yields the same shared EMPTY instance.
        assertSame(emptyValue, JacksonInject.Value.construct(null, null, null));

        // An empty-string id is coerced to null, so it also collapses to the shared EMPTY instance.
        assertSame(emptyValue, JacksonInject.Value.construct("", null, null));
    }
}
