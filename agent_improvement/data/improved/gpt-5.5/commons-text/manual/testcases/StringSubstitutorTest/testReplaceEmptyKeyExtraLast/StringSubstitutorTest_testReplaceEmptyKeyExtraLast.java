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

    private static final String EMPTY_VARIABLE_EXPRESSION = "${}";
    private static final String TEXT_AFTER_EXPRESSION = ".";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
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
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceEmptyKeyExtraLast() throws IOException {
        final String templateWithEmptyVariableName = EMPTY_VARIABLE_EXPRESSION + TEXT_AFTER_EXPRESSION;

        assertEqualsCharSeq(templateWithEmptyVariableName,
                replace(new StringSubstitutor(values), templateWithEmptyVariableName));
    }
}
