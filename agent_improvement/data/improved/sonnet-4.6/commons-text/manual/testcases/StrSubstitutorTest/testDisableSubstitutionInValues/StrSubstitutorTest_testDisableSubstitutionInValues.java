package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testDisableSubstitutionInValues {

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that when substitution inside values is disabled, variable references
     * embedded in map values are treated as literal strings and are NOT recursively
     * resolved. For example, if "animal" maps to "${critter}", the result keeps
     * "${critter}" verbatim rather than following the chain to "quick brown fox".
     */
    @Test
    void testDisableSubstitutionInValues() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setDisableSubstitutionInValues(true);

        // Values contain variable references that would normally be resolved recursively,
        // but with substitution in values disabled, they remain as-is.
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");

        // The template variables (${animal}, ${target}) are resolved one level deep,
        // but the variable references inside their values (${critter}, ${pet}) are kept literal.
        final String template = "The ${animal} jumps over the ${target}.";
        final String expected = "The ${critter} jumps over the ${pet}.";

        assertReplacementEquals(sub, expected, template);
    }

    /**
     * Asserts that the given substitutor produces {@code expected} when replacing
     * variables in {@code template}, using every supported input type. Also asserts
     * that the same result is produced when only a substring (all but the first and
     * last character) of the template is processed.
     */
    private void assertReplacementEquals(final StrSubstitutor sub,
                                         final String expected,
                                         final String template) {
        final String expectedSubstring = expected.substring(1, expected.length() - 1);

        // replace(String) and its substring variant
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedSubstring, sub.replace(template, 1, template.length() - 2));

        // replace(char[]) and its substring variant
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedSubstring, sub.replace(chars, 1, chars.length - 2));

        // replace(StringBuffer) and its substring variant
        StringBuffer buf = new StringBuffer(template);
        assertEquals(expected, sub.replace(buf));
        assertEquals(expectedSubstring, sub.replace(buf, 1, buf.length() - 2));

        // replace(StringBuilder) and its substring variant
        StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedSubstring, sub.replace(builder, 1, builder.length() - 2));

        // replace(StrBuilder) and its substring variant
        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilder));
        assertEquals(expectedSubstring, sub.replace(strBuilder, 1, strBuilder.length() - 2));

        // replace(Object) — the object's toString() returns the template
        final MutableObject<String> obj = new MutableObject<>(template);
        assertEquals(expected, sub.replace(obj));

        // replaceIn(StringBuffer): mutates the buffer in place
        buf = new StringBuffer(template);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expected, buf.toString());

        // replaceIn(StringBuffer, offset, length): replaces only the substring region;
        // characters outside the region are left untouched, so the full buffer still
        // equals expected (the surrounding chars happened to match already).
        buf = new StringBuffer(template);
        assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
        assertEquals(expected, buf.toString());

        // replaceIn(StringBuilder): mutates the builder in place
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expected, builder.toString());

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(expected, builder.toString());

        // replaceIn(StrBuilder): mutates the StrBuilder in place
        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expected, strBuilder.toString());

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
        assertEquals(expected, strBuilder.toString());
    }
}
