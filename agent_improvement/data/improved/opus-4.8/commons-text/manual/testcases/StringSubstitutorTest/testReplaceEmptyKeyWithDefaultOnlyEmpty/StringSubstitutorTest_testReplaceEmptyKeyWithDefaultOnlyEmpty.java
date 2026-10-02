package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} with a template that has no variable name but does supply a
 * default, i.e. {@code "${:-}"}.
 *
 * <p>The {@code ${...}} syntax allows a default after {@code :-}. Here both the variable name and
 * the default are empty, so the whole expression resolves to the empty string regardless of the
 * configured lookup values.</p>
 */
public class StringSubstitutorTest_testReplaceEmptyKeyWithDefaultOnlyEmpty {

    /** Template: an empty variable name with an empty default value. */
    private static final String EMPTY_KEY_WITH_EMPTY_DEFAULT = "${:-}";

    /** Expected result: the empty default. */
    private static final String EXPECTED_RESULT = "";

    /** Lookup values shared by every substitutor; none of them are referenced by the template. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys and values.
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
     * The empty-key-with-empty-default template must resolve to the empty string through every
     * supported input type and through both the value-returning {@code replace} methods and the
     * in-place {@code replaceIn} methods.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyEmpty() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) returns the substituted value for each supported input type.
        assertEquals(EXPECTED_RESULT, substitutor.replace(EMPTY_KEY_WITH_EMPTY_DEFAULT));
        assertEquals(EXPECTED_RESULT, substitutor.replace(EMPTY_KEY_WITH_EMPTY_DEFAULT.toCharArray()));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new StringBuffer(EMPTY_KEY_WITH_EMPTY_DEFAULT)));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new StringBuilder(EMPTY_KEY_WITH_EMPTY_DEFAULT)));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new TextStringBuilder(EMPTY_KEY_WITH_EMPTY_DEFAULT)));
        // An arbitrary Object is substituted via its toString().
        assertEquals(EXPECTED_RESULT, substitutor.replace(new MutableObject<>(EMPTY_KEY_WITH_EMPTY_DEFAULT)));

        // replaceIn(...) edits the buffer in place, returns true when it changed, and yields the
        // same result for each mutable buffer type.
        final StringBuffer stringBuffer = new StringBuffer(EMPTY_KEY_WITH_EMPTY_DEFAULT);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(EXPECTED_RESULT, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(EMPTY_KEY_WITH_EMPTY_DEFAULT);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(EXPECTED_RESULT, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(EMPTY_KEY_WITH_EMPTY_DEFAULT);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(EXPECTED_RESULT, textStringBuilder.toString());
    }
}
