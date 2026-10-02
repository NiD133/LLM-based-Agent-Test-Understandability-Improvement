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
 * Tests escaping behaviour of {@link StringSubstitutor}.
 *
 * <p>The default escape character is {@code $}, so a doubled {@code $$} collapses to a single
 * {@code $}. This test feeds templates that contain only escaped {@code $} characters in front of a
 * {@code ${...}} expression, so the expression itself is never treated as a variable and is left
 * untouched: {@code $$$$} (four dollars) collapses to {@code $$} (two dollars), turning the
 * "4 escapes" into "3 visible dollars".</p>
 */
public class StringSubstitutorTest_testReplaceVariablesCount1Escaping4To3 {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable definitions shared by every substitutor created in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values, including one that could be confused with a variable reference.
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

    @Test
    void testReplaceVariablesCount1Escaping4To3() throws IOException {
        // Four leading '$' escapes collapse to three visible '$'; the ${...} stays literal.
        assertSubstitution("$$${a}", "$$$${a}");
        assertSubstitution("$$${animal}", "$$$${animal}");
    }

    /**
     * Asserts that substituting {@code template} yields {@code expected} through every
     * {@code replace}/{@code replaceIn} overload {@link StringSubstitutor} offers, so the escaping
     * behaviour is verified consistently across all supported input types.
     */
    private void assertSubstitution(final String expected, final String template) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads return a new string and leave the input untouched.
        assertEquals(expected, substitutor.replace(template));
        assertEquals(expected, substitutor.replace(template.toCharArray()));
        assertEquals(expected, substitutor.replace(new StringBuffer(template)));
        assertEquals(expected, substitutor.replace(new StringBuilder(template)));
        assertEquals(expected, substitutor.replace(new TextStringBuilder(template)));
        // replace(Object) uses the object's toString(), which here is the template.
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads mutate the buffer in place and report whether anything changed.
        assertReplaceInPlace(substitutor, expected, new StringBuffer(template));
        assertReplaceInPlace(substitutor, expected, new StringBuilder(template));
        assertReplaceInPlace(substitutor, expected, new TextStringBuilder(template));
    }

    private void assertReplaceInPlace(final StringSubstitutor substitutor, final String expected,
            final StringBuffer buffer) {
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expected, buffer.toString());
    }

    private void assertReplaceInPlace(final StringSubstitutor substitutor, final String expected,
            final StringBuilder buffer) {
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expected, buffer.toString());
    }

    private void assertReplaceInPlace(final StringSubstitutor substitutor, final String expected,
            final TextStringBuilder buffer) {
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expected, buffer.toString());
    }
}
