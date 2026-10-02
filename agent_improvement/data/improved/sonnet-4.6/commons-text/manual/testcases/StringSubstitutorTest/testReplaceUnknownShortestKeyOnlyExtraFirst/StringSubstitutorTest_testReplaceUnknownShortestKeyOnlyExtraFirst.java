package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that StringSubstitutor leaves variable expressions unchanged when
 * the key is not present in the value map, even when there is leading text
 * before the unknown variable (the "Extra First" case).
 */
public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnlyExtraFirst {

    /** Map of known variable keys to their replacement values. */
    protected Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // single-character keys
        values.put("a", "1");
        values.put("b", "2");
        // multi-character keys
        values.put("aa", "11");
        values.put("bb", "22");
        values.put("aaa", "111");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // natural-language keys used in classic substitution examples
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)} so that subclasses
     * can override the replacement mechanism (e.g. stream-based variants).
     */
    protected String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }

    /**
     * Asserts that two CharSequences are equal, including their lengths in the
     * failure message to aid debugging when character content differs subtly.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Verifies that a template containing leading literal text followed by an
     * unknown variable (key "U" is absent from the value map) is returned
     * verbatim — i.e. the substitutor does not corrupt or partially consume
     * the expression when no matching key exists.
     *
     * <p>Template: {@code .${U}}
     * <br>Expected result: {@code .${U}} (unchanged)</p>
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraFirst() throws IOException {
        // "U" is not in the values map, so the whole expression must be preserved.
        final String template = ".${U}";
        assertEqualsCharSeq(template, replace(new StringSubstitutor(values), template));
    }
}
