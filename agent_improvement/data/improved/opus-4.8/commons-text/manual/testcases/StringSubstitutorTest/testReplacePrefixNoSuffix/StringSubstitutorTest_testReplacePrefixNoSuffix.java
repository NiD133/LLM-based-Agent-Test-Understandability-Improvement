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
 * Tests {@link StringSubstitutor} when a template contains a variable prefix ("${")
 * that is never closed by a suffix ("}").
 *
 * <p>The malformed expression {@code ${animal} is left verbatim, while the well-formed
 * {@code ${target}} occurrences are still resolved. The same expectation is verified
 * across every {@code replace} / {@code replaceIn} overload (String, char[], StringBuffer,
 * StringBuilder, TextStringBuilder and Object), including the offset/length "substring"
 * variants.</p>
 */
public class StringSubstitutorTest_testReplacePrefixNoSuffix {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /**
     * Template whose first "${animal" has no closing brace, so it is left untouched,
     * while both "${target}" references are resolved.
     */
    private static final String TEMPLATE = "The ${animal jumps over the ${target} ${target}.";

    /** Expected result of substituting {@link #TEMPLATE}. */
    private static final String EXPECTED = "The ${animal jumps over the ${target} lazy dog.";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    @Test
    void testReplacePrefixNoSuffix() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // The offset/length overloads substitute only the inner region [1, length-2];
        // here that region produces the full result minus its first and last character.
        final String expectedInner = EXPECTED.substring(1, EXPECTED.length() - 1);

        // replace(String)
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(expectedInner, substitutor.replace(TEMPLATE, 1, TEMPLATE.length() - 2));

        // replace(char[])
        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, substitutor.replace(chars));
        assertEquals(expectedInner, substitutor.replace(chars, 1, chars.length - 2));

        // replace(StringBuffer)
        StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(buffer));
        assertEquals(expectedInner, substitutor.replace(buffer, 1, buffer.length() - 2));

        // replace(StringBuilder) -> resolved via replace(CharSequence)
        StringBuilder builder = new StringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(builder));
        assertEquals(expectedInner, substitutor.replace(builder, 1, builder.length() - 2));

        // replace(TextStringBuilder)
        TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(textBuilder));
        assertEquals(expectedInner, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));

        // replace(Object) -- toString() returns the template
        final MutableObject<String> object = new MutableObject<>(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(object));

        // replaceIn(StringBuffer): full template, then inner region (remainder untouched)
        buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(EXPECTED, buffer.toString());
        buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(EXPECTED, buffer.toString());

        // replaceIn(StringBuilder)
        builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(EXPECTED, builder.toString());
        builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(EXPECTED, builder.toString());

        // replaceIn(TextStringBuilder)
        textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(EXPECTED, textBuilder.toString());
        textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
        assertEquals(EXPECTED, textBuilder.toString());
    }
}
