package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnly {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    /**
     * Asserts two CharSequences are equal, reporting their lengths on failure for easier debugging.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)} so subclasses can override the replacement strategy.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // shortest key and value pairs used to exercise ambiguous-prefix matching
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal key and value pairs
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that a variable whose key is not present in the value map is left
     * as-is (i.e. the substitutor does not remove or corrupt unknown placeholders).
     */
    @Test
    void testReplaceUnknownShortestKeyOnly() throws IOException {
        // "${U}" has no entry in the values map, so it must survive unchanged
        final String expected = "${U}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }
}
