package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} leaves an empty variable expression ({@code ${}})
 * untouched, even when it is immediately followed by extra trailing text.
 */
public class StringSubstitutorTest_testReplaceEmptyKeyExtraLast {

    /** An empty variable expression: a prefix and suffix with no variable name between them. */
    private static final String EMPTY_EXPR = "${}";

    /** Variable definitions available to the substitutor; none of them has an empty name. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys and values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Because {@code ${}} has no variable name, it cannot match any definition and must be
     * passed through verbatim. The trailing "." that follows it must also be preserved.
     */
    @Test
    void testReplaceEmptyKeyExtraLast() throws IOException {
        final String template = EMPTY_EXPR + ".";

        final String result = new StringSubstitutor(values).replace(template);

        assertEquals(template, result);
    }
}
