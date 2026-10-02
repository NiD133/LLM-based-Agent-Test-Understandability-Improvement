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
 * Tests that {@link StringSubstitutor} replaces two non-adjacent variables in a template.
 *
 * <p>Each case is verified against every {@code replace}/{@code replaceIn} input flavor
 * ({@code String}, {@code char[]}, {@code StringBuffer}, {@code StringBuilder},
 * {@code TextStringBuilder} and {@code Object}) so the behavior is consistent regardless of
 * how the template is supplied.</p>
 */
public class StringSubstitutorTest_testReplaceVariablesCount2NonAdjacent {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /** Variable name -> replacement value, shared by every assertion in a test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Word keys/values.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Asserts that substituting variables in {@code template} yields {@code expected}, checking
     * the result through every supported input type accepted by {@link StringSubstitutor}.
     */
    private void assertReplacedThroughEveryInputType(final String expected, final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // replace(...) overloads: return the substituted text, leaving the input untouched.
        assertEquals(expected, sub.replace(template));
        assertEquals(expected, sub.replace(template.toCharArray()));
        assertEquals(expected, sub.replace(new StringBuffer(template)));
        assertEquals(expected, sub.replace(new StringBuilder(template)));
        assertEquals(expected, sub.replace(new TextStringBuilder(template)));
        // Object overload substitutes the value's toString().
        assertEquals(expected, sub.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads: substitute in place and report whether anything changed.
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(stringBuffer), template);
        assertEquals(expected, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textStringBuilder));
        assertEquals(expected, textStringBuilder.toString());
    }

    /**
     * Tests replacing two non-adjacent variables separated by a literal space.
     */
    @Test
    void testReplaceVariablesCount2NonAdjacent() throws IOException {
        assertReplacedThroughEveryInputType("1 2", "${a} ${b}");
        assertReplacedThroughEveryInputType("11 22", "${aa} ${bb}");
        assertReplacedThroughEveryInputType(ANIMAL + " " + ANIMAL, "${animal} ${animal}");
        assertReplacedThroughEveryInputType(ANIMAL + " " + ANIMAL, "${animal} ${animal}");
        assertReplacedThroughEveryInputType(ANIMAL + " " + ANIMAL, "${animal} ${animal}");
    }
}
