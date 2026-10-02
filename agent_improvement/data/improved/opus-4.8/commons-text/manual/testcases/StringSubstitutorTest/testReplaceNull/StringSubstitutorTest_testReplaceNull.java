package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StringSubstitutor} behaves when a {@code null} input is passed to its
 * various {@code replace} / {@code replaceIn} overloads.
 *
 * <p>Expected contract for a {@code null} input:</p>
 * <ul>
 *   <li>every {@code replace(...)} overload returns {@code null}; and</li>
 *   <li>every {@code replaceIn(...)} overload reports "nothing replaced" by returning {@code false}.</li>
 * </ul>
 */
public class StringSubstitutorTest_testReplaceNull {

    /**
     * Variable values for the substitutor. Their actual content is irrelevant here, because every
     * input under test is {@code null} and therefore never parsed for variables.
     */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceNull() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads: a null input yields a null result, regardless of source type or bounds.
        assertNull(substitutor.replace((String) null));
        assertNull(substitutor.replace((String) null, 0, 100));
        assertNull(substitutor.replace((char[]) null));
        assertNull(substitutor.replace((char[]) null, 0, 100));
        assertNull(substitutor.replace((StringBuffer) null));
        assertNull(substitutor.replace((StringBuffer) null, 0, 100));
        assertNull(substitutor.replace((TextStringBuilder) null));
        assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
        assertNull(substitutor.replace((Object) null));

        // replaceIn(...) overloads: a null target means nothing is modified, so each returns false.
        assertFalse(substitutor.replaceIn((StringBuffer) null));
        assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
        assertFalse(substitutor.replaceIn((TextStringBuilder) null));
        assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
    }
}
