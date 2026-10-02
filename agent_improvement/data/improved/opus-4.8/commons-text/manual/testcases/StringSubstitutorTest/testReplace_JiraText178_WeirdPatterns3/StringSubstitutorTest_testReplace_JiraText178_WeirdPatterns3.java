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
 * Reproduces the weird boundary pattern from JIRA TEXT-178.
 *
 * <p>The template {@code "$${${a}"} mixes an escaped variable opener ({@code "$$"} -&gt; {@code "$"})
 * with what looks like a variable reference ({@code "${a}"}). Because the inner expression is never
 * closed (there is no matching {@code "}"} after {@code "${a}"} once the leading {@code "$$"} is
 * consumed), it is left untouched, yielding {@code "${${a}"} rather than {@code "$${1"} or
 * {@code "${1"}.</p>
 */
public class StringSubstitutorTest_testReplace_JiraText178_WeirdPatterns3 {

    /** Template under test: escaped opener followed by an unclosed variable reference. */
    private static final String TEMPLATE = "$${${a}";

    /** Expected interpolation result: {@code "$$"} collapses to {@code "$"}, the rest is verbatim. */
    private static final String EXPECTED = "${${a}";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        // The substitutor has a value for "a", but the unclosed expression prevents its use.
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

    @Test
    void testReplace_JiraText178_WeirdPatterns3() throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // Every replace(...) overload returns the interpolated text as a new String.
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(EXPECTED, sub.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, sub.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new TextStringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new MutableObject<>(TEMPLATE)));

        // Every replaceIn(...) overload interpolates in place and reports that it changed the buffer.
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(stringBuffer));
        assertEquals(EXPECTED, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(EXPECTED, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(textStringBuilder));
        assertEquals(EXPECTED, textStringBuilder.toString());
    }
}
