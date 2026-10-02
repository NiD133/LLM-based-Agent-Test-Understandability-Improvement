package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplace_JiraText178_WeirdPatterns1 {

    protected Map<String, String> values;

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
        if (replaceTemplate == null) {
            assertNull(replace(substitutor, (String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((TextStringBuilder) null));
            assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // short single-character keys
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // descriptive keys
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that escaped dollar signs ($$) and malformed or nested variable expressions
     * are left unchanged by the substitutor (regression for JIRA TEXT-178).
     *
     * <p>The default variable prefix is "${" and suffix is "}". A leading "$$" escapes the
     * dollar, so "$${..." is not treated as a variable start. Patterns where the apparent
     * variable name contains "${" or is otherwise not a registered lookup key are also
     * returned verbatim.</p>
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns1() throws IOException {
        // Escaped dollar followed by an incomplete variable start — not a variable
        doNotReplace("$${");
        // Escaped dollar followed by an unclosed expression — not a variable
        doNotReplace("$${a");
        // Two dollars escape the first; the remaining "${" is an incomplete variable start
        doNotReplace("$$${");
        // Two dollars escape the first; the remaining "${a" is unclosed
        doNotReplace("$$${a");
        // Escaped dollar then an unclosed nested expression — still not a variable
        doNotReplace("$${${a");
        // "${a}" is the apparent variable name, but it is not a registered key
        doNotReplace("${${a}");
        // "$${a}" is the apparent variable name (after un-escaping), but it is not a registered key
        doNotReplace("${$${a}");
    }
}
