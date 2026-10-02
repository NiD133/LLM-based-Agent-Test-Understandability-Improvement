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
 * Tests escape handling in {@link StringSubstitutor}.
 *
 * <p>The default escape character is {@code '$'}. A run of five {@code '$'} immediately
 * before a {@code "${...}"} expression collapses to four {@code '$'}, and because the
 * expression ends up escaped it is left as a literal rather than being substituted.</p>
 */
public class StringSubstitutorTest_testReplaceVariablesCount1Escaping5To4 {

    private static final String ANIMAL_VALUE = "quick brown fox";

    private static final String TARGET_VALUE = "lazy dog";

    /** Variable values shared by every substitution performed in this test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // shortest keys and values
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal keys and values
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceVariablesCount1Escaping5To4() throws IOException {
        // "$$$$$" collapses to "$$$$" and the trailing expression stays escaped (literal).
        assertSubstitutes("$$$${a}", "$$$$${a}");
        assertSubstitutes("$$$${animal}", "$$$$${animal}");
    }

    /**
     * Asserts that {@code template} substitutes to {@code expected} no matter which input
     * representation is handed to the substitutor.
     *
     * <p>It exercises every {@code replace(...)} overload (String, char[], StringBuffer,
     * StringBuilder, TextStringBuilder and arbitrary Object) plus the in-place
     * {@code replaceIn(...)} variants, all driven by a single substitutor instance just as
     * the production callers would use them.</p>
     */
    private void assertSubstitutes(final String expected, final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // replace(...) overloads that return a freshly built String
        final String fromString = sub.replace(template);
        assertEquals(expected, fromString,
            () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expected, fromString)));
        assertEquals(expected, sub.replace(template.toCharArray()));
        assertEquals(expected, sub.replace(new StringBuffer(template)));
        assertEquals(expected, sub.replace(new StringBuilder(template)));
        assertEquals(expected, sub.replace(new TextStringBuilder(template)));
        // Object overload: MutableObject.toString() yields the template back.
        assertEquals(expected, sub.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads that mutate the supplied buffer in place
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
}
