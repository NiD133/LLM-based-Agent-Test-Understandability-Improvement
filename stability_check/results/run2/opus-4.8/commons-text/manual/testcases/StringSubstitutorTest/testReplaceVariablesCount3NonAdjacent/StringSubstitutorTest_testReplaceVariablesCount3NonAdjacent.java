package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} replaces a variable that occurs three times, non-adjacently,
 * within a template, and that it does so consistently across every {@code replace}/{@code replaceIn}
 * overload (String, char[], StringBuffer, StringBuilder, TextStringBuilder and Object).
 */
public class StringSubstitutorTest_testReplaceVariablesCount3NonAdjacent {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Variable name to value mappings shared by every substitution in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // shortest keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal keys and values.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests simple key replace where a single variable appears three times, separated by other content.
     */
    @Test
    void testReplaceVariablesCount3NonAdjacent() throws IOException {
        assertReplaces("1 2 1", "${a} ${b} ${a}");
        assertReplaces("11 22 11", "${aa} ${bb} ${aa}");
        assertReplaces(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal} ${animal}");
        assertReplaces(ACTUAL_TARGET + " " + ACTUAL_TARGET + " " + ACTUAL_TARGET, "${target} ${target} ${target}");
    }

    /**
     * Asserts that substituting the variables in {@code template} yields {@code expectedResult}, checking
     * every {@code replace} overload as well as the in-place {@code replaceIn} overloads.
     */
    private void assertReplaces(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // replace(...) overloads: each reads the template and returns a new, fully substituted String.
        assertReplaceResult(expectedResult, sub.replace(template));
        assertReplaceResult(expectedResult, sub.replace(template.toCharArray()));
        assertReplaceResult(expectedResult, sub.replace(new StringBuffer(template)));
        assertReplaceResult(expectedResult, sub.replace(new StringBuilder(template)));
        assertReplaceResult(expectedResult, sub.replace(new TextStringBuilder(template)));
        // An arbitrary Object is substituted via its toString().
        assertReplaceResult(expectedResult, sub.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads: each substitutes in place, returns true when it changed the buffer,
        // and leaves the buffer holding the fully substituted result.
        assertReplacesInPlace(sub, expectedResult, new StringBuffer(template));
        assertReplacesInPlace(sub, expectedResult, new StringBuilder(template));
        assertReplacesInPlace(sub, expectedResult, new TextStringBuilder(template));
    }

    private void assertReplaceResult(final String expectedResult, final String actual) {
        assertEquals(expectedResult, actual,
            () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
    }

    private void assertReplacesInPlace(final StringSubstitutor sub, final String expectedResult, final StringBuffer buffer) {
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
    }

    private void assertReplacesInPlace(final StringSubstitutor sub, final String expectedResult, final StringBuilder builder) {
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
    }

    private void assertReplacesInPlace(final StringSubstitutor sub, final String expectedResult, final TextStringBuilder builder) {
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
    }
}
