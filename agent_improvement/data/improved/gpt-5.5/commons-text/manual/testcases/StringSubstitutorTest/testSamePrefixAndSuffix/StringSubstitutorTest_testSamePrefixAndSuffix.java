package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testSamePrefixAndSuffix {

    private static final String DELIMITER = "@";

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("greeting", "Hello");
        map.put(" there ", "XXX");
        map.put("name", "commons");

        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi @name@!", map, DELIMITER, DELIMITER));
        assertEqualsCharSeq("Hello there commons!",
                StringSubstitutor.replace("@greeting@ there @name@!", map, DELIMITER, DELIMITER));
    }
}
