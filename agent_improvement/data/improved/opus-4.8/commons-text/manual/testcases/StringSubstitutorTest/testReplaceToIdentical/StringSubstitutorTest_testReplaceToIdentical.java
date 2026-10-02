package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor} can produce output that is textually
 * identical to its input, even though a substitution genuinely occurs.
 *
 * <p>The {@code animal} variable expands to {@code "$${${thing}}"}; the leading
 * {@code $$} is an escaped {@code $}, and {@code ${thing}} expands to
 * {@code "animal"}. The net result of replacing {@code "${animal}"} is therefore
 * the literal text {@code "${animal}"} again, so the whole template is replaced
 * onto an identical string.</p>
 */
public class StringSubstitutorTest_testReplaceToIdentical {

    private static final String ANIMAL_VALUE = "quick brown fox";

    private static final String TARGET_VALUE = "lazy dog";

    /** Variable name to value mappings shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Single-character keys/values: the shortest possible substitutions.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Ordinary keys/values.
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that replacement creates output identical to the input template.
     */
    @Test
    void testReplaceToIdentical() throws IOException {
        // "${animal}" -> "$${${thing}}" -> "$" escaped + "${animal}" -> "${animal}".
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");

        final String template = "The ${animal} jumps.";
        assertReplacedToIdentical(template);
    }

    /**
     * Asserts that substituting into {@code template} yields {@code template} unchanged,
     * exercising every {@link StringSubstitutor} input type and its substring variants.
     *
     * <p>Because the expected result equals the input, the "substring" forms (which only
     * replace within the inner range while leaving the surrounding characters untouched)
     * are also expected to reproduce the full template.</p>
     */
    private void assertReplacedToIdentical(final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);
        // The inner substring excludes the first and last character of the template.
        final String innerResult = template.substring(1, template.length() - 1);

        // --- replace(...) returning a new String, for each accepted input type ---
        assertReplaceEquals(template, sub.replace(template), "String");
        assertEquals(innerResult, sub.replace(template, 1, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertReplaceEquals(template, sub.replace(chars), "char[]");
        assertEquals(innerResult, sub.replace(chars, 1, chars.length - 2));

        StringBuffer buffer = new StringBuffer(template);
        assertReplaceEquals(template, sub.replace(buffer), "StringBuffer");
        assertEquals(innerResult, sub.replace(buffer, 1, buffer.length() - 2));

        StringBuilder builder = new StringBuilder(template);
        assertReplaceEquals(template, sub.replace(builder), "StringBuilder");
        assertEquals(innerResult, sub.replace(builder, 1, builder.length() - 2));

        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertReplaceEquals(template, sub.replace(textBuilder), "TextStringBuilder");
        assertEquals(innerResult, sub.replace(textBuilder, 1, textBuilder.length() - 2));

        // Arbitrary Object: substitution uses its toString(), which is the template.
        final MutableObject<String> object = new MutableObject<>(template);
        assertReplaceEquals(template, sub.replace(object), "Object");

        // --- replaceIn(...) mutating the supplied buffer in place ---
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer), template);
        assertEquals(template, buffer.toString());
        // Replacing only the inner range still yields the full template, since
        // the untouched surrounding characters already match.
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(template, buffer.toString());

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(template, builder.toString());
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(template, builder.toString());

        textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(template, textBuilder.toString());
        textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder, 1, textBuilder.length() - 2));
        assertEquals(template, textBuilder.toString());
    }

    /** Asserts replacement equality and, on failure, reports the input type and first differing index. */
    private void assertReplaceEquals(final String expected, final String actual, final String inputType) {
        assertEquals(expected, actual,
            () -> String.format("replace(%s): index of difference: %,d",
                inputType, StringUtils.indexOfDifference(expected, actual)));
    }
}
