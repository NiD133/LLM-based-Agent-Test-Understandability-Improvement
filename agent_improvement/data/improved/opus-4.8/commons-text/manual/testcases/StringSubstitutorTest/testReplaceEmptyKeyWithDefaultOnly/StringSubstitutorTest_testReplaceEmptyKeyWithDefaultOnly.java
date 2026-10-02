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
 * Tests {@link StringSubstitutor} variable replacement when a template uses an
 * empty variable name together with a default value, e.g. {@code ${:-animal}}.
 *
 * <p>Because the variable name is empty (and therefore never present in the
 * lookup map), the substitutor must fall back to the supplied default value.
 * The expectation is verified across every {@code replace(...)} /
 * {@code replaceIn(...)} input overload to confirm consistent behaviour.</p>
 */
public class StringSubstitutorTest_testReplaceEmptyKeyWithDefaultOnly {

    /** Template with an empty variable name and the default value "animal". */
    private static final String TEMPLATE_WITH_EMPTY_KEY = "${:-animal}";

    /** The default value is used because the empty key is not in the lookup map. */
    private static final String EXPECTED_RESULT = "animal";

    /** Lookup values for the substitutor; none of them match the empty key. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceEmptyKeyWithDefaultOnly() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) variants: each returns the resolved default value as a new String.
        assertEquals(EXPECTED_RESULT, substitutor.replace(TEMPLATE_WITH_EMPTY_KEY));
        assertEquals(EXPECTED_RESULT, substitutor.replace(TEMPLATE_WITH_EMPTY_KEY.toCharArray()));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new StringBuffer(TEMPLATE_WITH_EMPTY_KEY)));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new StringBuilder(TEMPLATE_WITH_EMPTY_KEY)));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new TextStringBuilder(TEMPLATE_WITH_EMPTY_KEY)));
        // replace(Object) substitutes the value of the object's toString().
        assertEquals(EXPECTED_RESULT, substitutor.replace(new MutableObject<>(TEMPLATE_WITH_EMPTY_KEY)));

        // replaceIn(...) variants: mutate the buffer in place and report that a change was made.
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE_WITH_EMPTY_KEY);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(EXPECTED_RESULT, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE_WITH_EMPTY_KEY);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(EXPECTED_RESULT, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(TEMPLATE_WITH_EMPTY_KEY);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(EXPECTED_RESULT, textStringBuilder.toString());
    }
}
