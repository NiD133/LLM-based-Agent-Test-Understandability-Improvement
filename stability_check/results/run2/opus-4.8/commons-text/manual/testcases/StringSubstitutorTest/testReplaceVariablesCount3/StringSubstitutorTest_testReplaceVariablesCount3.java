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
 * Verifies that {@link StringSubstitutor} expands a template that repeats the
 * same variable three times, across every input type it accepts
 * (String, char[], StringBuffer, StringBuilder, TextStringBuilder and Object),
 * as well as the in-place {@code replaceIn} variants.
 */
public class StringSubstitutorTest_testReplaceVariablesCount3 {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable name to value mappings shared by every substitution below. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys and values.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Tests that a template referencing the same variable three times is fully
     * expanded, regardless of the concrete input type passed to the substitutor.
     */
    @Test
    void testReplaceVariablesCount3() throws IOException {
        assertReplacesTo("121", "${a}${b}${a}");
        assertReplacesTo("112211", "${aa}${bb}${aa}");
        assertReplacesTo(ANIMAL + ANIMAL + ANIMAL, "${animal}${animal}${animal}");
        assertReplacesTo(TARGET + TARGET + TARGET, "${target}${target}${target}");
    }

    /**
     * Asserts that {@code template} expands to {@code expectedResult} for every
     * input type and in-place variant supported by {@link StringSubstitutor}.
     */
    private void assertReplacesTo(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // Replace, returning a new String, for each accepted input type.
        assertEquals(expectedResult, substitutor.replace(template),
            () -> String.format("Index of difference: %,d",
                StringUtils.indexOfDifference(expectedResult, substitutor.replace(template))));
        assertEquals(expectedResult, substitutor.replace(template.toCharArray()));
        assertEquals(expectedResult, substitutor.replace(new StringBuffer(template)));
        assertEquals(expectedResult, substitutor.replace(new StringBuilder(template)));
        assertEquals(expectedResult, substitutor.replace(new TextStringBuilder(template)));
        // An arbitrary Object whose toString() yields the template.
        assertEquals(expectedResult, substitutor.replace(new MutableObject<>(template)));

        // Replace in place; replaceIn returns true when a substitution occurred.
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(expectedResult, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
    }
}
