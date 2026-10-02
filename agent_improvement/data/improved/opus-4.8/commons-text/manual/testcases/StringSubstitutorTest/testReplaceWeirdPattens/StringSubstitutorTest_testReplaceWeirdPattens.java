package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
 * Tests how {@link StringSubstitutor} handles "weird" templates whose {@code ${...}}
 * variable markers are empty, malformed, or nested in unusual ways.
 *
 * <p>Each template falls into one of two groups:</p>
 * <ul>
 *   <li><b>No-replace templates</b> &mdash; the substitutor must leave the text exactly as-is
 *       (see {@link #assertNotReplaced(String)}).</li>
 *   <li><b>Replace templates</b> &mdash; the substitutor must produce a specific result
 *       (see {@link #assertReplacedTo(String, String, boolean)}).</li>
 * </ul>
 *
 * <p>Every template is exercised through the full set of {@code replace}/{@code replaceIn}
 * overloads (String, char[], StringBuffer, StringBuilder, TextStringBuilder, Object) so that
 * all entry points stay consistent with one another.</p>
 */
public class StringSubstitutorTest_testReplaceWeirdPattens {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** The variable values shared by every substitutor created in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values that make overlapping and nested markers easy to reason about.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal-sized keys/values.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests interpolation with weird boundary patterns.
     */
    @Test
    void testReplaceWeirdPattens() throws IOException {
        // Empty input and empty/blank variable expressions: nothing to substitute.
        assertNotReplaced(StringUtils.EMPTY);
        assertNotReplaced("${}");
        assertNotReplaced("${ }");
        assertNotReplaced("${\t}");
        assertNotReplaced("${\n}");
        assertNotReplaced("${\b}");

        // Unbalanced or stray markers: not a complete, resolvable variable expression.
        assertNotReplaced("${");
        assertNotReplaced("$}");
        assertNotReplaced("$$}");
        assertNotReplaced("}");
        assertNotReplaced("${}$");
        assertNotReplaced("${}$$");
        assertNotReplaced("${${");

        // Nested empty/blank expressions: still nothing resolvable inside.
        assertNotReplaced("${${}}");
        assertNotReplaced("${$${}}");
        assertNotReplaced("${$$${}}");
        assertNotReplaced("${$$${$}}");
        assertNotReplaced("${${}}");
        assertNotReplaced("${${ }}");

        // Nested expressions wrapping a defined variable, but the outer marker is unbalanced
        // or escaped such that the whole thing stays unresolved.
        assertNotReplaced("${$${a}}");
        assertNotReplaced("${$$${a}}");
        assertNotReplaced("${${a}}");
        assertNotReplaced("${${${a}");
        assertNotReplaced("${ ${a}");
        assertNotReplaced("${ ${ ${a}");

        // Escaped outer marker ($$ -> $) leaves a literal "${...}" wrapper around the
        // resolved inner variable(s). The boolean flag drives the extra substring checks.
        assertReplacedTo("${1}", "$${${a}}", false);
        assertReplacedTo("${ 1}", "$${ ${a}}", false);
        assertReplacedTo("${12}", "$${${a}${b}}", false);
        assertReplacedTo("${ 1 2 }", "$${ ${a} ${b} }", false);
        assertReplacedTo("${${${a}2", "${${${a}${b}", false);
    }

    /**
     * Asserts that the template is returned unchanged by every {@code replace}/{@code replaceIn}
     * overload, i.e. it contains no resolvable variable.
     */
    private void assertNotReplaced(final String template) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // String overloads leave the text untouched.
        assertEquals(template, substitutor.replace(template));

        // In-place replacement reports "no change made" and leaves the buffer untouched.
        final TextStringBuilder buffer = new TextStringBuilder(template);
        assertFalse(substitutor.replaceIn(buffer));
        assertEquals(template, buffer.toString());
    }

    /**
     * Asserts that {@code template} is interpolated to {@code expected} through every
     * {@code replace}/{@code replaceIn} overload.
     *
     * @param expected        the fully interpolated result.
     * @param template        the template to interpolate.
     * @param checkSubstring  when {@code true}, also verifies the offset/length overloads on a
     *                        one-character-trimmed view of the template.
     */
    private void assertReplacedTo(final String expected, final String template,
            final boolean checkSubstring) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);
        // Result of interpolating the template with its first and last char dropped.
        final String expectedTrimmed =
                checkSubstring ? expected.substring(1, expected.length() - 1) : expected;

        // --- replace(...) overloads: produce a new String, leave the source intact ---

        assertEquals(expected, substitutor.replace(template));
        if (checkSubstring) {
            assertEquals(expectedTrimmed, substitutor.replace(template, 1, template.length() - 2));
        }

        final char[] chars = template.toCharArray();
        assertEquals(expected, substitutor.replace(chars));
        if (checkSubstring) {
            assertEquals(expectedTrimmed, substitutor.replace(chars, 1, chars.length - 2));
        }

        final StringBuffer stringBuffer = new StringBuffer(template);
        assertEquals(expected, substitutor.replace(stringBuffer));
        if (checkSubstring) {
            assertEquals(expectedTrimmed, substitutor.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expected, substitutor.replace(stringBuilder));
        if (checkSubstring) {
            assertEquals(expectedTrimmed, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expected, substitutor.replace(textBuilder));
        if (checkSubstring) {
            assertEquals(expectedTrimmed, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        // Arbitrary Object: substitutor interpolates its toString(), which is the template.
        final MutableObject<String> templateAsObject = new MutableObject<>(template);
        assertEquals(expected, substitutor.replace(templateAsObject));

        // --- replaceIn(...) overloads: mutate the buffer in place and report "changed" ---

        assertReplacedInPlace(substitutor, expected, template, checkSubstring);
    }

    /**
     * Verifies the in-place {@code replaceIn} overloads on the mutable buffer types.
     * Each call must mutate the buffer to {@code expected} and return {@code true}.
     * When {@code checkSubstring} is set, the offset/length variant is also checked; the buffer
     * still ends up equal to the full {@code expected} because the untouched remainder is
     * preserved.
     */
    private void assertReplacedInPlace(final StringSubstitutor substitutor, final String expected,
            final String template, final boolean checkSubstring) {
        StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), template);
        assertEquals(expected, stringBuffer.toString());
        if (checkSubstring) {
            stringBuffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expected, stringBuffer.toString());
        }

        StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());
        if (checkSubstring) {
            stringBuilder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expected, stringBuilder.toString());
        }

        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expected, textBuilder.toString());
        if (checkSubstring) {
            textBuilder = new TextStringBuilder(template);
            assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
            assertEquals(expected, textBuilder.toString());
        }
    }
}
