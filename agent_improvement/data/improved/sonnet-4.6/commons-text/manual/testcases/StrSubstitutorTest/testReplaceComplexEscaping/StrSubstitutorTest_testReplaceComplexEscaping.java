package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceComplexEscaping {

    private Map<String, String> values;

    /**
     * Exercises all overloads of replace/replaceIn for the given template, asserting
     * that every overload returns {@code expectedResult}. When {@code substring} is
     * true, also verifies that operating on an interior slice of the template produces
     * the matching interior slice of the expected result.
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) {
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);
        // replace using String
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }
        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }
        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }
        // replace using StrBuilder
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }
        // replace using object (toString returns template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));
        // replace in StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, buf.toString());
        }
        // replace in StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, builder.toString());
        }
        // replace in StrBuilder
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, bld.toString());
        }
    }

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
     * Tests complex escaping where {@code $$} is the escape sequence that prevents
     * the following {@code ${...}} from being substituted, emitting a literal
     * {@code ${...}} in the output instead.
     *
     * <p>Escaping rules used here:
     * <ul>
     *   <li>{@code $${X}} → literal {@code ${X}} (the {@code $$} escapes the delimiter)</li>
     *   <li>{@code ${animal}} → {@code "quick brown fox"}</li>
     *   <li>{@code ${target}} → {@code "lazy dog"}</li>
     *   <li>{@code ${undefined.number:-1234567890}} → default value {@code "1234567890"}</li>
     * </ul>
     */
    @Test
    void testReplaceComplexEscaping() {
        // $${${animal}} → first ${animal} resolves to "quick brown fox",
        // then $$ escapes the surrounding ${...}, producing the literal "${quick brown fox}".
        String templateWithEscapedResolution = "The $${${animal}} jumps over the ${target}.";
        String expectedWithEscapedResolution = "The ${quick brown fox} jumps over the lazy dog.";
        doTestReplace(expectedWithEscapedResolution, templateWithEscapedResolution, true);

        // Same pattern plus $${${undefined.number:-1234567890}}:
        // the inner variable is undefined so the default "1234567890" is used,
        // then $$ escapes the surrounding ${...}, producing the literal "${1234567890}".
        String templateWithDefaultAndEscape =
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.";
        String expectedWithDefaultAndEscape =
                "The ${quick brown fox} jumps over the lazy dog. ${1234567890}.";
        doTestReplace(expectedWithDefaultAndEscape, templateWithDefaultAndEscape, true);
    }
}
