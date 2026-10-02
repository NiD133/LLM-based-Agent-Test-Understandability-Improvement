package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnly {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String UNKNOWN_VARIABLE_TEMPLATE = "${U}";

    private Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(
            expected,
            actual,
            () -> String.format(
                "expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected),
                StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

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
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    /**
     * Unknown variables are left unchanged when undefined-variable exceptions are disabled.
     */
    @Test
    void testReplaceUnknownShortestKeyOnly() throws IOException {
        assertEqualsCharSeq(UNKNOWN_VARIABLE_TEMPLATE, replace(new StringSubstitutor(values), UNKNOWN_VARIABLE_TEMPLATE));
    }
}
