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
 * Tests how {@link StringSubstitutor} handles escaped variable prefixes.
 *
 * <p>The default escape character is {@code $}. A doubled {@code $$} collapses to a single
 * literal {@code $}, so a template that starts with three dollar signs ({@code $$$}) ends up
 * with two ({@code $$}) in the result. This is the "3 to 2" escaping case under test.</p>
 */
public class StringSubstitutorTest_testReplaceVariablesCount1Escaping3To2 {

    private static final String ANIMAL_VALUE = "quick brown fox";

    /** Variable values available to the substitutor; the keys used here are {@code a} and {@code animal}. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("animal", ANIMAL_VALUE);
    }

    @Test
    void testReplaceVariablesCount1Escaping3To2() throws IOException {
        // "$$$" -> "$$": the leading "$$" escapes to a single "$", and the trailing "${...}"
        // is left untouched (the escape consumed the prefix that would have triggered a lookup).
        assertReplacedThroughAllOverloads("$${a}", "$$${a}");
        assertReplacedThroughAllOverloads("$${animal}", "$$${animal}");
    }

    /**
     * Runs the substitution through every {@code replace}/{@code replaceIn} overload that accepts a
     * full (non-substring) input, asserting that each one produces {@code expected}.
     *
     * @param expected the text expected after substitution
     * @param template the input template containing escaped variable references
     */
    private void assertReplacedThroughAllOverloads(final String expected, final String template) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads: each reads a different input type and returns a new String.
        assertEquals(expected, substitutor.replace(template), "replace(String)");
        assertEquals(expected, substitutor.replace(template.toCharArray()), "replace(char[])");
        assertEquals(expected, substitutor.replace(new StringBuffer(template)), "replace(StringBuffer)");
        assertEquals(expected, substitutor.replace(new StringBuilder(template)), "replace(StringBuilder)");
        assertEquals(expected, substitutor.replace(new TextStringBuilder(template)), "replace(TextStringBuilder)");
        // replace(Object) uses the object's toString(), which returns the template.
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)), "replace(Object)");

        // replaceIn(...) overloads: each mutates the buffer in place and returns true when it changed it.
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), "replaceIn(StringBuffer)");
        assertEquals(expected, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder), "replaceIn(StringBuilder)");
        assertEquals(expected, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder), "replaceIn(TextStringBuilder)");
        assertEquals(expected, textStringBuilder.toString());
    }
}
