package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replace(Object, Map, String, String)} when the
 * variable prefix and suffix are the same delimiter (here {@code "@"}), e.g. the
 * template {@code "Hi @name@!"}.
 */
public class StringSubstitutorTest_testSamePrefixAndSuffix {

    /**
     * Asserts that two character sequences are equal, reporting their lengths if they differ.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    @Test
    void testSamePrefixAndSuffix() {
        // Variables are delimited by the same marker on both sides: @variable@.
        final Map<String, String> map = new HashMap<>();
        map.put("greeting", "Hello");
        map.put(" there ", "XXX");
        map.put("name", "commons");

        // A single variable surrounded by the shared "@" delimiter is resolved.
        assertEqualsCharSeq("Hi commons!",
            StringSubstitutor.replace("Hi @name@!", map, "@", "@"));

        // With two variables, the markers pair up greedily so the literal
        // " there " between them stays untouched (it is not treated as a key).
        assertEqualsCharSeq("Hello there commons!",
            StringSubstitutor.replace("@greeting@ there @name@!", map, "@", "@"));
    }
}
