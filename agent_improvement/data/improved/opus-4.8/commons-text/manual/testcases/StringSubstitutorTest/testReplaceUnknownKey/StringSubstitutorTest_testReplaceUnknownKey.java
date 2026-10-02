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
 * Tests that {@link StringSubstitutor} leaves a placeholder for an unknown key untouched
 * while still resolving the known keys around it.
 */
public class StringSubstitutorTest_testReplaceUnknownKey {

    /** {@code ${person}} has no entry in {@link #values}; {@code ${target}} does. */
    private static final String TEMPLATE = "The ${person} jumps over the ${target}.";

    /** Expected result: {@code ${person}} stays verbatim, {@code ${target}} becomes "lazy dog". */
    private static final String EXPECTED = "The ${person} jumps over the lazy dog.";

    /** Variable values shared by all substitutor instances created in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values that exercise overlapping prefixes.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values used by the template.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceUnknownKey() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // Substituting only the inner portion of the template leaves the same full result,
        // because the trimmed-off outer characters contain no placeholders.
        final String expectedInnerResult = EXPECTED.substring(1, EXPECTED.length() - 1);

        // --- replace(...) overloads return a new String, leaving the source unchanged ---

        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(expectedInnerResult, substitutor.replace(TEMPLATE, 1, TEMPLATE.length() - 2));

        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, substitutor.replace(chars));
        assertEquals(expectedInnerResult, substitutor.replace(chars, 1, chars.length - 2));

        StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(buffer));
        assertEquals(expectedInnerResult, substitutor.replace(buffer, 1, buffer.length() - 2));

        StringBuilder builder = new StringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(builder));
        assertEquals(expectedInnerResult, substitutor.replace(builder, 1, builder.length() - 2));

        TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(textBuilder));
        assertEquals(expectedInnerResult, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));

        // replace(Object) uses the object's toString(), which here returns the template.
        assertEquals(EXPECTED, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // --- replaceIn(...) overloads mutate the source in place and report whether anything changed ---

        buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(EXPECTED, buffer.toString());
        // Replacing only the inner range still yields the full result; the untouched ends carry over.
        buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(EXPECTED, buffer.toString());

        builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(EXPECTED, builder.toString());
        builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(EXPECTED, builder.toString());

        textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(EXPECTED, textBuilder.toString());
        textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
        assertEquals(EXPECTED, textBuilder.toString());
    }
}
