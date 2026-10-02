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
 * Tests that {@link StringSubstitutor} resolves the two-character key {@code ${aa}}
 * to its value {@code "11"} across every supported input type.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize2 {

    /** Value bound to the key {@code "aa"} exercised by this test. */
    private static final String EXPECTED_RESULT = "11";

    /** Template containing the single variable {@code ${aa}} to resolve. */
    private static final String TEMPLATE = "${aa}";

    /** Variable name to value mappings shared by the substitutor under test. */
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
     * Asserts that the substitutor turns {@link #TEMPLATE} into {@link #EXPECTED_RESULT}
     * regardless of which input representation the template is supplied in.
     */
    private void assertReplacesTemplateForEveryInputType(final StringSubstitutor sub) throws IOException {
        // Inputs that return the replaced text as a new String.
        assertEquals(EXPECTED_RESULT, sub.replace(TEMPLATE));
        assertEquals(EXPECTED_RESULT, sub.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED_RESULT, sub.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED_RESULT, sub.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED_RESULT, sub.replace(new TextStringBuilder(TEMPLATE)));
        // An arbitrary Object whose toString() yields the template.
        assertEquals(EXPECTED_RESULT, sub.replace(new MutableObject<>(TEMPLATE)));

        // Inputs replaced in place; replaceIn reports true when it changed the buffer.
        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(buffer), TEMPLATE);
        assertEquals(EXPECTED_RESULT, buffer.toString());

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(builder));
        assertEquals(EXPECTED_RESULT, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(EXPECTED_RESULT, textBuilder.toString());
    }

    /**
     * Tests simple key replace for a two-character key.
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        assertReplacesTemplateForEveryInputType(new StringSubstitutor(values));
    }
}
