package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} resolves templates that contain two
 * variables, checking that every {@code replace}/{@code replaceIn} overload
 * yields the same fully substituted result.
 */
public class StringSubstitutorTest_testReplaceVariablesCount2 {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable name to value mappings shared by every assertion. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("aa", "11");
        values.put("bb", "22");
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Each template references two variables; the expected result is their
     * resolved values concatenated in order.
     */
    @Test
    void testReplaceVariablesCount2() {
        assertResolvesToOnEveryOverload("1122", "${aa}${bb}");
        assertResolvesToOnEveryOverload(ANIMAL + ANIMAL, "${animal}${animal}");
        assertResolvesToOnEveryOverload(TARGET + TARGET, "${target}${target}");
        assertResolvesToOnEveryOverload(ANIMAL + TARGET, "${animal}${target}");
    }

    /**
     * Asserts that {@code template} resolves to {@code expected} through every
     * {@link StringSubstitutor} substitution overload.
     *
     * @param expected the fully substituted text
     * @param template the template containing {@code ${...}} variables
     */
    private void assertResolvesToOnEveryOverload(final String expected, final String template) {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // Overloads that return the result and leave their input unchanged.
        assertEquals(expected, substitutor.replace(template));
        assertEquals(expected, substitutor.replace(template.toCharArray()));
        assertEquals(expected, substitutor.replace(new StringBuffer(template)));
        assertEquals(expected, substitutor.replace(new StringBuilder(template)));
        assertEquals(expected, substitutor.replace(new TextStringBuilder(template)));
        // replace(Object) substitutes against the object's toString().
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));

        // Overloads that rewrite the buffer in place and report whether it changed.
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(expected, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expected, textStringBuilder.toString());
    }
}
