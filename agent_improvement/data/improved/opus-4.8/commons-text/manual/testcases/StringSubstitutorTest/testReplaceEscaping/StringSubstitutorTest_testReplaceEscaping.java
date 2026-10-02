package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that a doubled variable prefix ({@code $$}) escapes variable substitution in
 * {@link StringSubstitutor}, leaving the literal {@code ${...}} expression in the output.
 */
public class StringSubstitutorTest_testReplaceEscaping {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /** Variable values shared by every substitution performed in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values to exercise prefix/suffix matching on minimal expressions.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Tests escaping.
     */
    @Test
    void testReplaceEscaping() throws IOException {
        // "$$" escapes the prefix, so "$${animal}" stays literal while "${target}" is substituted.
        assertReplacedEverywhere("The ${animal} jumps over the lazy dog.", "The $${animal} jumps over the ${target}.", true);
        // A fully escaped expression is emitted verbatim.
        assertReplacedEverywhere("${a}", "$${a}", false);
        // Escaping also works for nested expressions.
        assertReplacedEverywhere("${a${a}}", "$${a$${a}}", false);
        assertReplacedEverywhere("${a${a${a}}}", "$${a$${a$${a}}}", false);
    }

    /**
     * Asserts that substituting {@code template} yields {@code expectedResult} through every input
     * type {@link StringSubstitutor} supports (String, char[], StringBuffer, StringBuilder,
     * TextStringBuilder and Object), plus the in-place {@code replaceIn} variants.
     *
     * @param expectedResult the expected output of a full-template substitution.
     * @param template       the template containing escaped/real variable expressions.
     * @param substring      when {@code true}, also verifies the offset/length overloads that
     *                       substitute only the inner portion of the template.
     */
    private void assertReplacedEverywhere(final String expectedResult, final String template, final boolean substring)
            throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);
        // For the offset/length overloads, the first and last characters are skipped.
        final String expectedInnerResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // replace using String
        final String actual = sub.replace(template);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedInnerResult, sub.replace(template, 1, template.length() - 2));
        }

        // replace using char[]
        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedInnerResult, sub.replace(chars, 1, chars.length - 2));
        }

        // replace using StringBuffer
        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, sub.replace(buffer));
        if (substring) {
            assertEquals(expectedInnerResult, sub.replace(buffer, 1, buffer.length() - 2));
        }

        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedInnerResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // replace using TextStringBuilder
        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expectedResult, sub.replace(textBuilder));
        if (substring) {
            assertEquals(expectedInnerResult, sub.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        // replace using Object (its toString() returns the template)
        final MutableObject<String> object = new MutableObject<>(template);
        assertEquals(expectedResult, sub.replace(object));

        // replaceIn StringBuffer (in-place)
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer), template);
        assertEquals(expectedResult, buffer.toString());
        if (substring) {
            buffer = new StringBuffer(template);
            assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
            // The untouched remainder leaves the full result intact.
            assertEquals(expectedResult, buffer.toString());
        }

        // replaceIn StringBuilder (in-place)
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(template);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // The untouched remainder leaves the full result intact.
            assertEquals(expectedResult, builder.toString());
        }

        // replaceIn TextStringBuilder (in-place)
        textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
        if (substring) {
            textBuilder = new TextStringBuilder(template);
            assertTrue(sub.replaceIn(textBuilder, 1, textBuilder.length() - 2));
            // The untouched remainder leaves the full result intact.
            assertEquals(expectedResult, textBuilder.toString());
        }
    }
}
