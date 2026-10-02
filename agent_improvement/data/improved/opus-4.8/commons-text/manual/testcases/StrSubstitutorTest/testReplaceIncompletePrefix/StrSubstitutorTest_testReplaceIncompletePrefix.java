package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor} replaces complete variables while leaving
 * an incomplete variable prefix untouched, and that this behaviour is identical
 * across every supported source type and across both the whole-source and
 * substring (offset/length) overloads of {@code replace} / {@code replaceIn}.
 */
public class StrSubstitutorTest_testReplaceIncompletePrefix {

    /** Template containing one incomplete prefix ("{animal}") and one real variable ("${target}"). */
    private static final String TEMPLATE = "The {animal} jumps over the ${target}.";

    /** Expected output: "{animal}" is left as-is, "${target}" is resolved to "lazy dog". */
    private static final String EXPECTED = "The {animal} jumps over the lazy dog.";

    private StrSubstitutor sub;

    @BeforeEach
    public void setUp() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        sub = new StrSubstitutor(values);
    }

    @Test
    void testReplaceIncompletePrefix() {
        // The substring overloads operate on TEMPLATE without its first and last
        // character; the result is EXPECTED without its first and last character.
        final String partialTemplate = TEMPLATE.substring(1, TEMPLATE.length() - 1);
        final int offset = 1;
        final int length = TEMPLATE.length() - 2;
        final String expectedPartial = EXPECTED.substring(1, EXPECTED.length() - 1);

        // --- replace(...) returning a new String, for each source type ---
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(expectedPartial, sub.replace(TEMPLATE, offset, length));

        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, sub.replace(chars));
        assertEquals(expectedPartial, sub.replace(chars, offset, chars.length - 2));

        assertEquals(EXPECTED, sub.replace(new StringBuffer(TEMPLATE)));
        assertEquals(expectedPartial, sub.replace(new StringBuffer(TEMPLATE), offset, length));

        assertEquals(EXPECTED, sub.replace(new StringBuilder(TEMPLATE)));
        assertEquals(expectedPartial, sub.replace(new StringBuilder(TEMPLATE), offset, length));

        assertEquals(EXPECTED, sub.replace(new StrBuilder(TEMPLATE)));
        assertEquals(expectedPartial, sub.replace(new StrBuilder(TEMPLATE), offset, length));

        // Object source: its toString() supplies the template.
        assertEquals(EXPECTED, sub.replace(new MutableObject<>(TEMPLATE)));

        // --- replaceIn(...) mutating the source in place, for each mutable type ---
        // Whole-source replacement rewrites the buffer to EXPECTED and returns true.
        // The substring overload only touches [offset, offset+length); because the
        // partial region carries the resolvable variable, the buffer still ends up
        // equal to EXPECTED (the untouched first/last chars match EXPECTED already).
        final StringBuffer buf = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(buf));
        assertEquals(EXPECTED, buf.toString());
        final StringBuffer bufPartial = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(bufPartial, offset, length));
        assertEquals(EXPECTED, bufPartial.toString());

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(builder));
        assertEquals(EXPECTED, builder.toString());
        final StringBuilder builderPartial = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(builderPartial, offset, length));
        assertEquals(EXPECTED, builderPartial.toString());

        final StrBuilder bld = new StrBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(bld));
        assertEquals(EXPECTED, bld.toString());
        final StrBuilder bldPartial = new StrBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(bldPartial, offset, length));
        assertEquals(EXPECTED, bldPartial.toString());
    }
}
