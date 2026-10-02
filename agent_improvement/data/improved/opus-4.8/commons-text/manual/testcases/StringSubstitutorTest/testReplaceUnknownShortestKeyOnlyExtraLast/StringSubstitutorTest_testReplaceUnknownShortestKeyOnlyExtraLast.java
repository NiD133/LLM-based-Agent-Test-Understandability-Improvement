package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#replace(String)} leaves a variable
 * expression untouched when its key is not present in the value map.
 */
public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnlyExtraLast {

    /** Replacement values keyed by variable name; note that "U" is intentionally absent. */
    private Map<String, String> buildValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return values;
    }

    /**
     * The template references the unknown key "U" and has one extra character
     * (".") after the closing brace. Because "U" has no mapping, the whole
     * expression must be returned verbatim.
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraLast() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(buildValues());

        final String template = "${U}.";
        final String result = substitutor.replace(template);

        assertEquals("${U}.", result);
    }
}
