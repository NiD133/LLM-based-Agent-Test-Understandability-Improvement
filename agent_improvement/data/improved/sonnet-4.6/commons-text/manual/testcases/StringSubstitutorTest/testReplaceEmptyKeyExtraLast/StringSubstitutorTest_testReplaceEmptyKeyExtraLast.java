package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceEmptyKeyExtraLast {

    /** Empty variable expression: the prefix and suffix with no key between them. */
    private static final String EMPTY_EXPR = "${}";

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
     * For subclasses to override.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests that an empty variable expression followed by a trailing character is left unchanged,
     * because an empty key has no mapping and cannot be substituted.
     * Input:  "${}."; Expected output: "${}.".
     */
    @Test
    void testReplaceEmptyKeyExtraLast() throws IOException {
        final String templateWithTrailingChar = EMPTY_EXPR + ".";
        assertEqualsCharSeq(templateWithTrailingChar, replace(new StringSubstitutor(values), templateWithTrailingChar));
    }
}
