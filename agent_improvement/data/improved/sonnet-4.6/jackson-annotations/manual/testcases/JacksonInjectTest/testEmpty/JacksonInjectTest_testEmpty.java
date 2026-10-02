package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JacksonInject.Value#empty()}, verifying that the canonical
 * "empty" value has null id, null useInput, and delegates willUseInput() to
 * the caller-supplied default. Also verifies that construct() with all-null
 * (or empty-string id) returns the same EMPTY singleton.
 */
public class JacksonInjectTest_testEmpty extends AnnotationTestUtil {

    // The canonical empty value — no id, no useInput override, no optional override.
    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testEmpty_idIsNull() {
        assertNull(EMPTY.getId());
    }

    @Test
    public void testEmpty_useInputIsNull() {
        // null means "no explicit override" — the caller's default governs willUseInput()
        assertNull(EMPTY.getUseInput());
    }

    @Test
    public void testEmpty_willUseInput_delegatesToCallerDefault() {
        // When _useInput is null, willUseInput() returns whatever the caller passes in.
        assertTrue(EMPTY.willUseInput(true));
        assertFalse(EMPTY.willUseInput(false));
    }

    @Test
    public void testEmpty_constructWithAllNulls_returnsSameEmptySingleton() {
        assertSame(EMPTY, JacksonInject.Value.construct(null, null, null));
    }

    @Test
    public void testEmpty_constructWithEmptyStringId_coercedToNullAndReturnsSameEmptySingleton() {
        // An empty-string id is treated the same as a null id, so the result is still EMPTY.
        assertSame(EMPTY, JacksonInject.Value.construct("", null, null));
    }
}
