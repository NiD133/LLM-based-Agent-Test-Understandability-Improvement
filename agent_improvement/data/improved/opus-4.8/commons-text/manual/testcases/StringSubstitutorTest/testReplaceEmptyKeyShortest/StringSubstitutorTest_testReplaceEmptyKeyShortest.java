package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} leaves an empty variable expression
 * ({@code ${}}, i.e. a variable with no name) untouched, even when the lookup
 * map is populated with very short keys such as {@code "a"} or {@code "b"}.
 */
public class StringSubstitutorTest_testReplaceEmptyKeyShortest {

    /** A variable expression with no variable name; nothing should match it. */
    private static final String EMPTY_VARIABLE_EXPRESSION = "${}";

    /** Lookup values supplied to the substitutor; none of these keys is empty. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Shortest possible keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys and values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * An empty variable name does not match any key, so the expression must be
     * returned verbatim and any buffer left unmodified.
     */
    @Test
    void testReplaceEmptyKeyShortest() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(String) returns the template unchanged.
        assertEquals(EMPTY_VARIABLE_EXPRESSION, substitutor.replace(EMPTY_VARIABLE_EXPRESSION));

        // replaceIn(...) reports "no replacement made" and leaves the buffer untouched.
        final TextStringBuilder builder = new TextStringBuilder(EMPTY_VARIABLE_EXPRESSION);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(EMPTY_VARIABLE_EXPRESSION, builder.toString());
    }
}
