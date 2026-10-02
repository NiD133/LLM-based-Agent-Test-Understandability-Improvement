package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnlyExtraLast {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    /**
     * Asserts that two CharSequences are equal, including their lengths in the failure message for easier debugging.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)} so subclasses can override substitution behaviour.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // shortest key and value.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal key and value.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that a template containing only an unknown variable reference followed by a literal character
     * is returned unchanged — i.e. unresolvable variables are preserved as-is.
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraLast() throws IOException {
        final String expected = "${U}.";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }
}
