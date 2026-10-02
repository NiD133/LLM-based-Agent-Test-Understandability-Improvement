package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceUnknownKeyOnlyExtraLast {

    private static final String UNRESOLVED_PERSON_PLACEHOLDER = "${person}.";

    private Map<String, String> values;

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

    @Test
    void testReplaceUnknownKeyOnlyExtraLast() throws IOException {
        assertEqualsCharSeq(
                UNRESOLVED_PERSON_PLACEHOLDER,
                replace(new StringSubstitutor(values), UNRESOLVED_PERSON_PLACEHOLDER));
    }

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(
                expected,
                actual,
                () -> String.format(
                        "expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected),
                        StringUtils.length(actual)));
    }

    private String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
