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
 * Tests that {@link StringSubstitutor} replaces the same variable multiple times when its
 * occurrences are separated by other text ("non-adjacent"), across every text-input overload.
 */
public class StringSubstitutorTest_testReplaceVariablesCount3NonAdjacent {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Variable name to value mapping shared by all substitutors in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values that could tempt greedy or overlapping matching.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    /**
     * Tests that each variable is substituted at all three of its non-adjacent positions.
     */
    @Test
    void testReplaceVariablesCount3NonAdjacent() throws IOException {
        assertReplaced("1 2 1", "${a} ${b} ${a}");
        assertReplaced("11 22 11", "${aa} ${bb} ${aa}");
        assertReplaced(join(ACTUAL_ANIMAL, ACTUAL_ANIMAL, ACTUAL_ANIMAL), "${animal} ${animal} ${animal}");
        assertReplaced(join(ACTUAL_TARGET, ACTUAL_TARGET, ACTUAL_TARGET), "${target} ${target} ${target}");
    }

    private static String join(final String a, final String b, final String c) {
        return a + " " + b + " " + c;
    }

    /**
     * Asserts that substituting the given template produces the expected result for every
     * text-input overload of {@link StringSubstitutor}: {@code replace} of a String, char[],
     * StringBuffer, StringBuilder, TextStringBuilder and Object, plus the in-place
     * {@code replaceIn} overloads.
     */
    private void assertReplaced(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads: each returns a new, fully substituted string.
        assertReplaceResult(expectedResult, substitutor.replace(template));
        assertReplaceResult(expectedResult, substitutor.replace(template.toCharArray()));
        assertReplaceResult(expectedResult, substitutor.replace(new StringBuffer(template)));
        assertReplaceResult(expectedResult, substitutor.replace(new StringBuilder(template)));
        assertReplaceResult(expectedResult, substitutor.replace(new TextStringBuilder(template)));
        // Object overload substitutes template obtained via toString().
        assertReplaceResult(expectedResult, substitutor.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads: each mutates the buffer in place and reports that it changed.
        assertReplacedInPlace(expectedResult, template, new StringBuffer(template), substitutor);
        assertReplacedInPlace(expectedResult, template, new StringBuilder(template), substitutor);
        assertReplacedInPlace(expectedResult, template, new TextStringBuilder(template), substitutor);
    }

    private void assertReplaceResult(final String expectedResult, final String actual) {
        assertEquals(expectedResult, actual,
            () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
    }

    private void assertReplacedInPlace(final String expectedResult, final String template,
            final StringBuffer buffer, final StringSubstitutor substitutor) {
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(expectedResult, buffer.toString());
    }

    private void assertReplacedInPlace(final String expectedResult, final String template,
            final StringBuilder builder, final StringSubstitutor substitutor) {
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
    }

    private void assertReplacedInPlace(final String expectedResult, final String template,
            final TextStringBuilder builder, final StringSubstitutor substitutor) {
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
    }
}
