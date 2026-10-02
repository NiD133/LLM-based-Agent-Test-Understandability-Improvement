package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceAdjacentAtEnd {

    protected Map<String, String> values;

    /**
     * Asserts equality of two CharSequences, reporting their lengths on failure
     * to aid diagnosis of large-string mismatches.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // short single/double/triple-letter keys used by sibling tests
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // descriptive keys used by the classic template tests
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that two adjacent variable references placed at the end of the
     * template string — with no separator between them — are each resolved
     * independently and their values are concatenated directly.
     *
     * Template : "Amount is ${code}${amount}"
     * Expected : "Amount is GBP12.50"
     */
    @Test
    void testReplaceAdjacentAtEnd() throws IOException {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEqualsCharSeq("Amount is GBP12.50", replace(sub, "Amount is ${code}${amount}"));
    }
}
