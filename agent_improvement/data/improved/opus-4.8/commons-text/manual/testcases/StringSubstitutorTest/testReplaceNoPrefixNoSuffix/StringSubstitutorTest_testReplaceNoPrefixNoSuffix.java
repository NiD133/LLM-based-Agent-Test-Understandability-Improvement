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
 * Tests {@link StringSubstitutor#replace} across every accepted source type when the template
 * contains a bare word that is <em>not</em> wrapped in the {@code ${...}} variable syntax.
 * <p>
 * In {@code "The animal jumps over the ${target}."} the word {@code animal} has no prefix/suffix,
 * so it is left untouched while only {@code ${target}} is substituted.
 */
public class StringSubstitutorTest_testReplaceNoPrefixNoSuffix {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable values shared by every substitution in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values exercise overlapping-prefix handling.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values used by the template under test.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * A bare word ({@code animal}) without {@code ${...}} delimiters must be left as-is, while the
     * properly delimited {@code ${target}} is replaced. Substitution must behave identically across
     * all source types and must also honour an offset/length window.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() throws IOException {
        final String template = "The animal jumps over the ${target}.";
        final String expected = "The animal jumps over the lazy dog.";

        assertReplaceAcrossAllSourceTypes(new StringSubstitutor(values), template, expected);
    }

    /**
     * Verifies that substituting {@code template} yields {@code expected} for every source-type
     * overload of {@code replace}/{@code replaceIn}, including the offset/length variants.
     * <p>
     * For the windowed (offset/length) overloads the window is the template with its first and last
     * characters trimmed, so the expected result is correspondingly trimmed.
     */
    private void assertReplaceAcrossAllSourceTypes(final StringSubstitutor sub, final String template,
            final String expected) throws IOException {
        final String expectedWindow = expected.substring(1, expected.length() - 1);

        // replace(String)
        final String actual = sub.replace(template);
        assertEquals(expected, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expected, actual)));
        assertEquals(expectedWindow, sub.replace(template, 1, template.length() - 2));

        // replace(char[])
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedWindow, sub.replace(chars, 1, chars.length - 2));

        // replace(StringBuffer)
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(buffer));
        assertEquals(expectedWindow, sub.replace(buffer, 1, buffer.length() - 2));

        // replace(StringBuilder) (resolves to replace(CharSequence))
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedWindow, sub.replace(builder, 1, builder.length() - 2));

        // replace(TextStringBuilder)
        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expected, sub.replace(textBuilder));
        assertEquals(expectedWindow, sub.replace(textBuilder, 1, textBuilder.length() - 2));

        // replace(Object) - the object's toString() returns the template
        assertEquals(expected, sub.replace(new MutableObject<>(template)));

        // replaceIn(StringBuffer) mutates the buffer in place
        assertReplaceInStringBuffer(sub, template, expected);

        // replaceIn(StringBuilder) mutates the builder in place
        assertReplaceInStringBuilder(sub, template, expected);

        // replaceIn(TextStringBuilder) mutates the builder in place
        assertReplaceInTextStringBuilder(sub, template, expected);
    }

    private void assertReplaceInStringBuffer(final StringSubstitutor sub, final String template,
            final String expected) {
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer), template);
        assertEquals(expected, buffer.toString());

        // Windowed replaceIn still yields the full result: text outside the window is untouched.
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(expected, buffer.toString());
    }

    private void assertReplaceInStringBuilder(final StringSubstitutor sub, final String template,
            final String expected) {
        StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expected, builder.toString());

        // Windowed replaceIn still yields the full result: text outside the window is untouched.
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(expected, builder.toString());
    }

    private void assertReplaceInTextStringBuilder(final StringSubstitutor sub, final String template,
            final String expected) {
        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(expected, textBuilder.toString());

        // Windowed replaceIn still yields the full result: text outside the window is untouched.
        textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder, 1, textBuilder.length() - 2));
        assertEquals(expected, textBuilder.toString());
    }
}
