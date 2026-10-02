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
 * Tests that {@link StringSubstitutor} replaces every occurrence of a variable
 * in a template, using each of the supported input types.
 */
public class StringSubstitutorTest_testReplaceVariablesCount3 {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable name to value mapping shared by every replacement in this test. */
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
     * Asserts that {@code template} expands to {@code expectedResult} through every
     * {@link StringSubstitutor} replace overload (String, char[], StringBuffer,
     * StringBuilder, TextStringBuilder and Object) as well as the in-place
     * {@code replaceIn} overloads.
     */
    private void assertReplacedEverywhere(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // Overloads that return the replaced text and leave the input untouched.
        assertEquals(expectedResult, sub.replace(template));
        assertEquals(expectedResult, sub.replace(template.toCharArray()));
        assertEquals(expectedResult, sub.replace(new StringBuffer(template)));
        assertEquals(expectedResult, sub.replace(new StringBuilder(template)));
        assertEquals(expectedResult, sub.replace(new TextStringBuilder(template)));
        // The Object overload reads the template from the argument's toString().
        assertEquals(expectedResult, sub.replace(new MutableObject<>(template)));

        // Overloads that replace in place and report whether anything changed.
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
    }

    /**
     * Tests that a variable repeated three times in a template is replaced on each occurrence.
     */
    @Test
    void testReplaceVariablesCount3() throws IOException {
        assertReplacedEverywhere("121", "${a}${b}${a}");
        assertReplacedEverywhere("112211", "${aa}${bb}${aa}");
        assertReplacedEverywhere(ANIMAL + ANIMAL + ANIMAL, "${animal}${animal}${animal}");
        assertReplacedEverywhere(TARGET + TARGET + TARGET, "${target}${target}${target}");
    }
}
