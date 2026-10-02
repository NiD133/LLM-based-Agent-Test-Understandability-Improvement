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
 * Tests {@link StringSubstitutor}'s handling of complex escaping.
 *
 * <p>The escape character is {@code $}. Writing it twice ({@code $$}) emits a single literal
 * {@code $} into the output without triggering substitution. Combined with the default
 * {@code ${...}} variable syntax this lets a template produce literal {@code ${...}} text while
 * still substituting nested variables. For example, the template {@code $${${a}}} first resolves
 * the inner variable {@code ${a}} to its value {@code 1}, then the leading {@code $$} escape turns
 * the surrounding {@code ${...}} into the literal text {@code ${1}}.</p>
 */
public class StringSubstitutorTest_testReplaceComplexEscaping {

    private static final String ANIMAL_VALUE = "quick brown fox";

    private static final String TARGET_VALUE = "lazy dog";

    /** Variable name to value mappings shared by every substitution in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Single-character keys/values and short repeated variants, used to verify escaping
        // is independent of variable name/value length.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Longer, more realistic variables.
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    /**
     * Tests complex escaping: each template is expected to resolve to the given literal result.
     *
     * <p>The {@code substring} flag indicates that the same substitution should additionally be
     * verified when applied to the inner part of the template (the template minus its first and
     * last characters), exercising the offset/length overloads of {@code replace}.</p>
     */
    @Test
    void testReplaceComplexEscaping() throws IOException {
        // "$$" escapes the leading "$" so the outer "${...}" survives as literal text,
        // while the inner variable is still substituted.
        assertReplaces("${1}", "$${${a}}", false);
        assertReplaces("${11}", "$${${aa}}", false);
        assertReplaces("${111}", "$${${aaa}}", false);
        assertReplaces("${quick brown fox}", "$${${animal}}", false);
        assertReplaces("The ${quick brown fox} jumps over the lazy dog.",
                "The $${${animal}} jumps over the ${target}.", true);

        // Both "$" characters of the inner "${...}" are escaped, so nothing inside is substituted
        // and the whole inner expression is emitted literally.
        assertReplaces("${${a}}", "$${$${a}}", false);
        assertReplaces("${${aa}}", "$${$${aa}}", false);
        assertReplaces("${${aaa}}", "$${$${aaa}}", false);
        assertReplaces("${${animal}}", "$${$${animal}}", false);

        // Surrounding literal text (leading/trailing dots) is preserved verbatim.
        assertReplaces(".${${animal}}", ".$${$${animal}}", false);
        assertReplaces("${${animal}}.", "$${$${animal}}.", false);
        assertReplaces(".${${animal}}.", ".$${$${animal}}.", false);
        assertReplaces("The ${${animal}} jumps over the lazy dog.",
                "The $${$${animal}} jumps over the ${target}.", true);

        // The "-" default-value syntax: "undefined.number" is unknown, so its default "1234567890"
        // is used, then escaped into literal "${1234567890}".
        assertReplaces("The ${quick brown fox} jumps over the lazy dog. ${1234567890}.",
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.", true);
    }

    /**
     * Substitutes {@code template} with a {@link StringSubstitutor} backed by {@link #values} and
     * asserts the result equals {@code expectedResult} across every {@code replace}/{@code replaceIn}
     * overload {@link StringSubstitutor} offers.
     *
     * @param expectedResult the expected substitution result for the full template
     * @param template       the template to substitute
     * @param substring      whether to additionally verify the offset/length overloads against the
     *                       template's inner portion (first and last characters removed)
     */
    private void assertReplaces(final String expectedResult, final String template, final boolean substring)
            throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);
        // When substring is requested, the inner portion of the template is expected to produce the
        // inner portion of the result (first and last characters dropped).
        final String expectedInnerResult =
                substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // replace(String)
        final String actual = substitutor.replace(template);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedInnerResult, substitutor.replace(template, 1, template.length() - 2));
        }

        // replace(char[])
        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedInnerResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        // replace(StringBuffer)
        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (substring) {
            assertEquals(expectedInnerResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }

        // replace(StringBuilder)
        StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (substring) {
            assertEquals(expectedInnerResult, substitutor.replace(builder, 1, builder.length() - 2));
        }

        // replace(TextStringBuilder)
        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(textBuilder));
        if (substring) {
            assertEquals(expectedInnerResult, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        // replace(Object) - the object's toString() returns the template.
        final MutableObject<String> templateObject = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(templateObject));

        // replaceIn(StringBuffer) - in-place substitution.
        buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(expectedResult, buffer.toString());
        if (substring) {
            buffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            // The untouched remainder makes this equal the full result.
            assertEquals(expectedResult, buffer.toString());
        }

        // replaceIn(StringBuilder) - in-place substitution.
        builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            // The untouched remainder makes this equal the full result.
            assertEquals(expectedResult, builder.toString());
        }

        // replaceIn(TextStringBuilder) - in-place substitution.
        textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
        if (substring) {
            textBuilder = new TextStringBuilder(template);
            assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
            // The untouched remainder makes this equal the full result.
            assertEquals(expectedResult, textBuilder.toString());
        }
    }
}
