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
 * Verifies that an empty variable name combined with a default value, e.g. {@code ${:-animal}},
 * is substituted by the default value across every {@code replace}/{@code replaceIn} overload of
 * {@link StringSubstitutor}.
 */
public class StringSubstitutorTest_testReplaceEmptyKeyWithDefault {

    private static final String ANIMAL_VALUE = "quick brown fox";

    private static final String TARGET_VALUE = "lazy dog";

    /** Variable values shared by every substitution overload under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values that exercise variable-name matching corner cases.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Keys used by the templates below.
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    /**
     * When a variable expression has no name but supplies a default (e.g. {@code ${:-animal}}),
     * the default text is used. Here {@code ${:-animal}} resolves to the literal "animal" and
     * {@code ${target}} resolves to "lazy dog".
     */
    @Test
    void testReplaceEmptyKeyWithDefault() throws IOException {
        final String template = "The ${:-animal} jumps over the ${target}.";
        final String expected = "The animal jumps over the lazy dog.";

        assertReplacedEverywhere(new StringSubstitutor(values), template, expected);
    }

    /**
     * Asserts that {@code substitutor} turns {@code template} into {@code expected} through every
     * way {@link StringSubstitutor} can perform substitution: the {@code replace} overloads that
     * return a new value, and the {@code replaceIn} overloads that mutate a buffer in place.
     *
     * <p>Because the template here contains variables, an offset/length substitution that trims the
     * leading and trailing character still yields the full {@code expected} result, since the
     * trimmed characters are plain text and are left untouched.</p>
     */
    private void assertReplacedEverywhere(final StringSubstitutor substitutor, final String template,
            final String expected) throws IOException {
        // The substring covering the template without its first and last character.
        final String expectedSubstring = expected.substring(1, expected.length() - 1);

        assertReplaceReturnsExpected(substitutor, template, expected, expectedSubstring);
        assertReplaceInMutatesBuffers(substitutor, template, expected);
    }

    /** Checks the {@code replace(...)} overloads that return the substituted value. */
    private void assertReplaceReturnsExpected(final StringSubstitutor substitutor, final String template,
            final String expected, final String expectedSubstring) {
        // replace using String
        final String actual = substitutor.replace(template);
        assertEquals(expected, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expected, actual)));
        assertEquals(expectedSubstring, substitutor.replace(template, 1, template.length() - 2));

        // replace using char[]
        final char[] chars = template.toCharArray();
        assertEquals(expected, substitutor.replace(chars));
        assertEquals(expectedSubstring, substitutor.replace(chars, 1, chars.length - 2));

        // replace using StringBuffer
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, substitutor.replace(buffer));
        assertEquals(expectedSubstring, substitutor.replace(buffer, 1, buffer.length() - 2));

        // replace using StringBuilder
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, substitutor.replace(builder));
        assertEquals(expectedSubstring, substitutor.replace(builder, 1, builder.length() - 2));

        // replace using TextStringBuilder
        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expected, substitutor.replace(textBuilder));
        assertEquals(expectedSubstring, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));

        // replace using Object (its toString returns the template)
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));
    }

    /** Checks the {@code replaceIn(...)} overloads that mutate the supplied buffer in place. */
    private void assertReplaceInMutatesBuffers(final StringSubstitutor substitutor, final String template,
            final String expected) {
        // replace in StringBuffer
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(expected, buffer.toString());
        buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
        // Full result expected: the untouched leading/trailing text is plain text.
        assertEquals(expected, buffer.toString());

        // replace in StringBuilder
        StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expected, builder.toString());
        builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(expected, builder.toString());

        // replace in TextStringBuilder
        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expected, textBuilder.toString());
        textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
        assertEquals(expected, textBuilder.toString());
    }
}
