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
 * Tests escaping in {@link StringSubstitutor}.
 *
 * <p>An escaped variable reference {@code "$${name}"} must be unescaped to the
 * literal {@code "${name}"} instead of being substituted with the variable's
 * value. This is checked across every {@code replace}/{@code replaceIn}
 * overload.</p>
 */
public class StringSubstitutorTest_testReplaceVariablesCount1Escaping2To1 {

    /** Variable map shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Asserts that every {@code replace}/{@code replaceIn} overload converts the
     * escaped {@code template} into {@code expected}, leaving the variable
     * reference as a literal rather than resolving it.
     *
     * @param expected the unescaped literal the template should produce
     * @param template the escaped template to feed to the substitutor
     */
    private void assertEscapedToLiteral(final String expected, final String template) {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads return a new String; the input is not modified.
        assertEquals(expected, substitutor.replace(template));
        assertEquals(expected, substitutor.replace(template.toCharArray()));
        assertEquals(expected, substitutor.replace(new StringBuffer(template)));
        assertEquals(expected, substitutor.replace(new StringBuilder(template)));
        assertEquals(expected, substitutor.replace(new TextStringBuilder(template)));
        // An arbitrary Object is substituted via its toString().
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));

        // replaceIn(...) overloads edit the buffer in place and return true on change.
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

    /**
     * Tests escaping: {@code "$${...}"} collapses two leading {@code $} into one,
     * yielding the literal {@code "${...}"}.
     */
    @Test
    void testReplaceVariablesCount1Escaping2To1() throws IOException {
        assertEscapedToLiteral("${a}", "$${a}");
        assertEscapedToLiteral("${animal}", "$${animal}");
    }
}
