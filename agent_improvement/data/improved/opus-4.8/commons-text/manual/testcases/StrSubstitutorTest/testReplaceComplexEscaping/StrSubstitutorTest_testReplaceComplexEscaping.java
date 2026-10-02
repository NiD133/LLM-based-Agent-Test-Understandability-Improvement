package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor}'s handling of escaped variable markers.
 *
 * <p>The substitutor recognizes {@code ${name}} as a variable reference. A doubled
 * prefix ({@code $$}) escapes the marker so that a literal {@code ${...}} survives in
 * the output instead of being interpreted as a variable.</p>
 */
public class StrSubstitutorTest_testReplaceComplexEscaping {

    /** Variable name to value mappings shared by every assertion in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests complex escaping where an escaped marker ({@code $$}) and a real variable
     * reference are nested together, both with and without a default-value delimiter.
     */
    @Test
    void testReplaceComplexEscaping() {
        // "$${${animal}}" -> the inner ${animal} resolves, the escaped $$ collapses to
        // a literal ${...}, producing "${quick brown fox}".
        assertReplacedEverywhere(
                "The ${quick brown fox} jumps over the lazy dog.",
                "The $${${animal}} jumps over the ${target}.");

        // Same idea, but the nested reference uses the ":-" default-value syntax. The
        // variable is undefined, so its default ("1234567890") is used before the
        // escaped marker collapses to a literal "${1234567890}".
        assertReplacedEverywhere(
                "The ${quick brown fox} jumps over the lazy dog. ${1234567890}.",
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.");
    }

    /**
     * Asserts that substituting {@code template} yields {@code expectedResult} across every
     * input representation that {@link StrSubstitutor} accepts.
     *
     * <p>Two families of methods are exercised:</p>
     * <ul>
     *   <li>{@code replace(...)} overloads that return a new resolved string, and</li>
     *   <li>{@code replaceIn(...)} overloads that resolve a mutable buffer in place.</li>
     * </ul>
     *
     * <p>Each is checked both on the full template and on an inner substring (offset 1,
     * length - 2), since the template's first and last characters carry no variable markers.</p>
     */
    private void assertReplacedEverywhere(final String expectedResult, final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        // Dropping the first and last template characters drops the matching characters of
        // the result, because neither end participates in any substitution.
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertReplaceReturnsResolvedCopy(sub, expectedResult, expectedSubstringResult, template);
        assertReplaceInResolvesBufferInPlace(sub, expectedResult, template);
    }

    /**
     * Verifies the {@code replace(...)} overloads, each of which returns a freshly resolved
     * string and leaves the source untouched. Every supported source type is covered:
     * String, char[], StringBuffer, StringBuilder, StrBuilder and an arbitrary Object
     * (resolved via its {@code toString()}).
     */
    private void assertReplaceReturnsResolvedCopy(final StrSubstitutor sub, final String expectedResult,
            final String expectedSubstringResult, final String template) {
        // Source: String
        assertEquals(expectedResult, sub.replace(template));
        assertEquals(expectedSubstringResult, sub.replace(template, 1, template.length() - 2));

        // Source: char[]
        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        assertEquals(expectedSubstringResult, sub.replace(chars, 1, chars.length - 2));

        // Source: StringBuffer
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, sub.replace(buffer));
        assertEquals(expectedSubstringResult, sub.replace(buffer, 1, buffer.length() - 2));

        // Source: StringBuilder
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, sub.replace(builder));
        assertEquals(expectedSubstringResult, sub.replace(builder, 1, builder.length() - 2));

        // Source: StrBuilder
        final StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expectedResult, sub.replace(strBuilder));
        assertEquals(expectedSubstringResult, sub.replace(strBuilder, 1, strBuilder.length() - 2));

        // Source: arbitrary Object (toString() returns the template)
        final MutableObject<String> obj = new MutableObject<>(template);
        assertEquals(expectedResult, sub.replace(obj));
    }

    /**
     * Verifies the {@code replaceIn(...)} overloads, which resolve a mutable buffer in place
     * and return {@code true} when at least one substitution was made.
     *
     * <p>The range-limited overloads start at offset 1 with length - 2; the characters outside
     * that range are left as-is, so the full expected result is still produced because the
     * untouched ends contain no markers.</p>
     */
    private void assertReplaceInResolvesBufferInPlace(final StrSubstitutor sub, final String expectedResult,
            final String template) {
        // Target: StringBuffer
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());

        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(expectedResult, buffer.toString());

        // Target: StringBuilder
        StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(expectedResult, builder.toString());

        // Target: StrBuilder
        StrBuilder strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
        assertEquals(expectedResult, strBuilder.toString());
    }
}
