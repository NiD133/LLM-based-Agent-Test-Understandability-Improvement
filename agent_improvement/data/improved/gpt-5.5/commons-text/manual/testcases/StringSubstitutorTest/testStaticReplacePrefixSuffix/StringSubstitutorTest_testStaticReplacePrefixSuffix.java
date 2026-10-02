package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplacePrefixSuffix {

    private static final String TEMPLATE = "Hi <name>!";
    private static final String EXPECTED = "Hi commons!";
    private static final String VARIABLE_PREFIX = "<";
    private static final String VARIABLE_SUFFIX = ">";

    private static void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests static replacement with custom prefix and suffix strings.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        assertEqualsCharSeq(EXPECTED, StringSubstitutor.replace(TEMPLATE, map, VARIABLE_PREFIX, VARIABLE_SUFFIX));
    }
}
