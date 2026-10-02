package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceKeyStartChars2Only {

    protected Map<String, String> values;

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

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Asserts that two CharSequences are equal, including their lengths in the failure message
     * to help diagnose mismatches.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }

    /**
     * Tests that a string containing only the first two characters of the variable start marker
     * (e.g. "${") is returned unchanged — an incomplete variable expression is not substituted.
     */
    @Test
    void testReplaceKeyStartChars2Only() throws IOException {
        // Take only the first 2 chars of DEFAULT_VAR_START (i.e. "${"), which is an incomplete
        // variable expression and should pass through the substitutor without modification.
        final String incompleteVarStart = StringSubstitutor.DEFAULT_VAR_START.substring(0, 2);

        final String result = replace(new StringSubstitutor(values), incompleteVarStart);

        assertEqualsCharSeq(incompleteVarStart, result);
    }
}
