package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link StrSubstitutor#setDisableSubstitutionInValues(boolean)}.
 *
 * <p>When substitution in values is disabled, a variable is replaced by its raw
 * value exactly once. The value is <em>not</em> scanned again, so any
 * {@code ${...}} markers it contains are left untouched instead of being
 * resolved recursively.</p>
 */
public class StrSubstitutorTest_testDisableSubstitutionInValues {

    /** Variable name -&gt; value map backing the substitutor under test. */
    private Map<String, String> values;

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

    @Test
    void testDisableSubstitutionInValues() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setDisableSubstitutionInValues(true);

        // Redefine the variables so that every value is itself a variable
        // reference (or chain of references). With substitution-in-values
        // disabled, these nested references must survive verbatim.
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");

        // "${animal}" resolves to its raw value "${critter}" (not "quick brown fox"),
        // and "${target}" resolves to its raw value "${pet}" (not "lazy dog").
        final String template = "The ${animal} jumps over the ${target}.";
        final String expected = "The ${critter} jumps over the ${pet}.";
        assertReplacedThroughAllInputTypes(sub, expected, template);
    }

    /**
     * Asserts that {@code sub} turns {@code template} into {@code expected}, exercising
     * every {@code replace} / {@code replaceIn} overload offered by {@link StrSubstitutor}.
     *
     * <p>Each overload is checked twice: once over the whole template, and once over the
     * inner substring (offset 1, length-2) to confirm the bounded variants behave
     * identically. The expected substring result is the full result with its first and
     * last characters stripped, mirroring how the bounded call trims the template.</p>
     */
    private void assertReplacedThroughAllInputTypes(final StrSubstitutor sub,
            final String expected, final String template) {
        final String expectedSubstring = expected.substring(1, expected.length() - 1);

        // replace(...) returning a new String, from each supported input type.
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedSubstring, sub.replace(template, 1, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedSubstring, sub.replace(chars, 1, chars.length - 2));

        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(buffer));
        assertEquals(expectedSubstring, sub.replace(buffer, 1, buffer.length() - 2));

        StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedSubstring, sub.replace(builder, 1, builder.length() - 2));

        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilder));
        assertEquals(expectedSubstring, sub.replace(strBuilder, 1, strBuilder.length() - 2));

        // replace(Object): toString() yields the template.
        final MutableObject<String> templateHolder = new MutableObject<>(template);
        assertEquals(expected, sub.replace(templateHolder));

        // replaceIn(...) mutating the buffer in place and returning true when it changed.
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expected, buffer.toString());
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
        // The characters outside the [1, length-2] window are left as-is, so the
        // in-place result still equals the full expected string.
        assertEquals(expected, buffer.toString());

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expected, builder.toString());
        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(expected, builder.toString());

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expected, strBuilder.toString());
        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
        assertEquals(expected, strBuilder.toString());
    }
}
