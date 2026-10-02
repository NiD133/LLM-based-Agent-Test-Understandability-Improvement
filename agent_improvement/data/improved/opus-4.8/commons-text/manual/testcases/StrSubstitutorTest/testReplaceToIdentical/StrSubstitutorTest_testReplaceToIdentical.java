package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StrSubstitutor} can produce output identical to its input.
 *
 * <p>The interesting case here is a self-referential substitution. With
 * {@code animal = "$${${thing}}"} and {@code thing = "animal"}, expanding
 * {@code ${animal}} yields {@code "$${animal}"}; the {@code $$} then collapses
 * to a literal {@code $}, giving back {@code "${animal}"}. So the template
 * {@code "The ${animal} jumps."} substitutes to itself.</p>
 */
public class StrSubstitutorTest_testReplaceToIdentical {

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests that replacement creates output identical to the input.
     */
    @Test
    void testReplaceToIdentical() {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");

        final String template = "The ${animal} jumps.";
        assertReplaceProducesIdenticalOutput(template);
    }

    /**
     * Asserts that substituting {@code template} yields {@code template} again,
     * exercising every {@code replace}/{@code replaceIn} overload of
     * {@link StrSubstitutor}: the full-text forms and the
     * offset/length (substring) forms over {@code String}, {@code char[]},
     * {@code StringBuffer}, {@code StringBuilder}, {@code StrBuilder} and
     * {@code Object} inputs.
     */
    private void assertReplaceProducesIdenticalOutput(final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        // Substituting characters 1..length-2 leaves the first and last char
        // untouched, so the substring result is the template without its ends.
        final String expectedSubstring = template.substring(1, template.length() - 1);

        // replace(...) returning a new String, from each supported source type.
        assertEquals(template, sub.replace(template));
        assertEquals(expectedSubstring, sub.replace(template, 1, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertEquals(template, sub.replace(chars));
        assertEquals(expectedSubstring, sub.replace(chars, 1, chars.length - 2));

        StringBuffer buffer = new StringBuffer(template);
        assertEquals(template, sub.replace(buffer));
        assertEquals(expectedSubstring, sub.replace(buffer, 1, buffer.length() - 2));

        StringBuilder builder = new StringBuilder(template);
        assertEquals(template, sub.replace(builder));
        assertEquals(expectedSubstring, sub.replace(builder, 1, builder.length() - 2));

        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(template, sub.replace(strBuilder));
        assertEquals(expectedSubstring, sub.replace(strBuilder, 1, strBuilder.length() - 2));

        // replace(Object) uses the object's toString(), which returns the template.
        final MutableObject<String> obj = new MutableObject<>(template);
        assertEquals(template, sub.replace(obj));

        // replaceIn(...) mutating the buffer in place and reporting whether it changed.
        // The substituted value equals the template, so the buffer ends up unchanged.
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(template, buffer.toString());
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(template, buffer.toString());

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(template, builder.toString());
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(template, builder.toString());

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(template, strBuilder.toString());
        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
        assertEquals(template, strBuilder.toString());
    }
}
