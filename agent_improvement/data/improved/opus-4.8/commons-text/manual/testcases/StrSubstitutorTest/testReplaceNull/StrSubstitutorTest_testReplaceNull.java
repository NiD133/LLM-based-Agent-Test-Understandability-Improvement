package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that every {@code replace} / {@code replaceIn} overload of
 * {@link StrSubstitutor} tolerates a {@code null} input.
 */
public class StrSubstitutorTest_testReplaceNull {

    /** Substitution values; their content is irrelevant since the inputs below are all null. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests that a null template/buffer is handled gracefully by all overloads:
     * the {@code replace(...)} methods return {@code null}, and the
     * {@code replaceIn(...)} methods report that nothing was changed.
     */
    @Test
    void testReplaceNull() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace(...) overloads on a null input all return null
        assertNull(sub.replace((String) null));
        assertNull(sub.replace((String) null, 0, 100));
        assertNull(sub.replace((char[]) null));
        assertNull(sub.replace((char[]) null, 0, 100));
        assertNull(sub.replace((StringBuffer) null));
        assertNull(sub.replace((StringBuffer) null, 0, 100));
        assertNull(sub.replace((StrBuilder) null));
        assertNull(sub.replace((StrBuilder) null, 0, 100));
        assertNull(sub.replace((Object) null));

        // replaceIn(...) overloads on a null buffer report "no replacement made"
        assertFalse(sub.replaceIn((StringBuffer) null));
        assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
        assertFalse(sub.replaceIn((StrBuilder) null));
        assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
    }
}
