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
 * Tests that {@link StringSubstitutor} resolves a simple 3-character variable key
 * (<code>${aaa}</code>) to its mapped value ("111") across every replace/replaceIn
 * overload it exposes.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize3 {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Variable name -> value pairs shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Keys of length 1, 2 and 3 so a longer key cannot be confused with a shorter one.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Human-readable keys.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * The variable {@code ${aaa}} must expand to its mapped value {@code "111"}.
     */
    @Test
    void testReplaceSimpleKeySize3() throws IOException {
        assertReplacesTo("111", "${aaa}");
    }

    /**
     * Asserts that {@code template} is substituted to {@code expectedResult} through every
     * public {@code replace}/{@code replaceIn} overload of {@link StringSubstitutor}, so the
     * behaviour is verified regardless of the input container type.
     */
    private void assertReplacesTo(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // replace(...) overloads return a new String, leaving the source untouched.
        assertReplaceReturns(sub, expectedResult, template);

        // replaceIn(...) overloads mutate the source in place and report that a change happened.
        assertReplaceInMutates(sub, expectedResult, template);
    }

    /** Verifies every {@code replace(...)} overload that returns the substituted String. */
    private void assertReplaceReturns(final StringSubstitutor sub, final String expectedResult, final String template)
            throws IOException {
        // String source.
        final String actual = sub.replace(template);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));

        // char[] source.
        assertEquals(expectedResult, sub.replace(template.toCharArray()));

        // StringBuffer source.
        assertEquals(expectedResult, sub.replace(new StringBuffer(template)));

        // StringBuilder source.
        assertEquals(expectedResult, sub.replace(new StringBuilder(template)));

        // TextStringBuilder source.
        assertEquals(expectedResult, sub.replace(new TextStringBuilder(template)));

        // Arbitrary Object source whose toString() yields the template.
        assertEquals(expectedResult, sub.replace(new MutableObject<>(template)));
    }

    /** Verifies every {@code replaceIn(...)} overload that substitutes the source in place. */
    private void assertReplaceInMutates(final StringSubstitutor sub, final String expectedResult, final String template) {
        // StringBuffer source.
        final StringBuffer buf = new StringBuffer(template);
        assertTrue(sub.replaceIn(buf), template);
        assertEquals(expectedResult, buf.toString());

        // StringBuilder source.
        final StringBuilder builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        // TextStringBuilder source.
        final TextStringBuilder bld = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
    }
}
