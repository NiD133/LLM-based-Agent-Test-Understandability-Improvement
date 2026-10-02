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
 * Tests {@link StringSubstitutor} when a template contains an empty variable
 * expression {@code ${}} (a variable with no name).
 *
 * <p>The expectation is that an empty expression is left untouched, while a
 * regular, resolvable variable in the same template is still replaced.</p>
 */
public class StringSubstitutorTest_testReplaceEmptyKey {

    /** Replacement value for the {@code target} variable. */
    private static final String TARGET_VALUE = "lazy dog";

    /** Template mixing an unnamed variable {@code ${}} with the {@code ${target}} variable. */
    private static final String TEMPLATE = "The ${} jumps over the ${target}.";

    /** Expected result: {@code ${}} survives, {@code ${target}} is resolved. */
    private static final String EXPECTED = "The ${} jumps over the lazy dog.";

    /** Variable name to value mappings shared by every replace overload. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values that the various templates in this suite may reference.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // The only mapping actually exercised by this test.
        values.put("animal", "quick brown fox");
        values.put("target", TARGET_VALUE);
    }

    /**
     * Verifies that {@code ${}} is preserved (and {@code ${target}} replaced) across
     * every {@code replace}/{@code replaceIn} overload, including the offset/length
     * "substring" variants.
     *
     * <p>The substring variants run over the template with its first and last
     * characters excluded. Because the empty expression and the {@code ${target}}
     * variable both sit strictly inside that range, the substring result is simply
     * the full result with its first and last characters trimmed.</p>
     */
    @Test
    void testReplaceEmptyKey() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);
        // Offset 1, length len-2 skips the leading 'T' and the trailing '.'.
        final String expectedSubstring = EXPECTED.substring(1, EXPECTED.length() - 1);

        // --- replace(...) overloads return a new String, leaving the input untouched ---

        // String input
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(expectedSubstring, substitutor.replace(TEMPLATE, 1, TEMPLATE.length() - 2));

        // char[] input
        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, substitutor.replace(chars));
        assertEquals(expectedSubstring, substitutor.replace(chars, 1, chars.length - 2));

        // StringBuffer input
        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(buffer));
        assertEquals(expectedSubstring, substitutor.replace(buffer, 1, buffer.length() - 2));

        // StringBuilder input
        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(stringBuilder));
        assertEquals(expectedSubstring, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));

        // TextStringBuilder input
        final TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(textBuilder));
        assertEquals(expectedSubstring, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));

        // Object input: replace uses the object's toString(), which yields the template
        final MutableObject<String> object = new MutableObject<>(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(object));

        // --- replaceIn(...) overloads mutate the buffer in place and report whether it changed ---

        // replaceIn(StringBuffer)
        StringBuffer inBuffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(inBuffer), TEMPLATE);
        assertEquals(EXPECTED, inBuffer.toString());
        // Substring variant: characters outside [1, len-2) are left as-is, so the
        // whole buffer still equals the full expected result.
        inBuffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(inBuffer, 1, inBuffer.length() - 2));
        assertEquals(EXPECTED, inBuffer.toString());

        // replaceIn(StringBuilder)
        StringBuilder inStringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(inStringBuilder));
        assertEquals(EXPECTED, inStringBuilder.toString());
        inStringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(inStringBuilder, 1, inStringBuilder.length() - 2));
        assertEquals(EXPECTED, inStringBuilder.toString());

        // replaceIn(TextStringBuilder)
        TextStringBuilder inTextBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(inTextBuilder));
        assertEquals(EXPECTED, inTextBuilder.toString());
        inTextBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(inTextBuilder, 1, inTextBuilder.length() - 2));
        assertEquals(EXPECTED, inTextBuilder.toString());
    }
}
