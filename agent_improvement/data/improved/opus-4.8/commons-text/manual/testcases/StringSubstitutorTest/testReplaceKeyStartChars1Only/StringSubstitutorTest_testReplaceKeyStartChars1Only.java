package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a lone variable-start character is left untouched by the substitutor.
 *
 * <p>The default variable syntax is {@code "${...}"}. A complete prefix is therefore
 * {@code "${"}; the first character on its own ({@code "$"}) is an incomplete prefix and
 * must not be treated as the beginning of a variable. Replacing such a template should
 * return the template unchanged.</p>
 */
public class StringSubstitutorTest_testReplaceKeyStartChars1Only {

    /** Lookup values available to the substitutor; none of these keys appear in the template. */
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
     * The first character of the variable prefix ({@code "$"} from {@code "${"}) is not a
     * complete variable start, so the substitutor should return it verbatim.
     */
    @Test
    void testReplaceKeyStartChars1Only() throws IOException {
        final String firstPrefixChar = StringSubstitutor.DEFAULT_VAR_START.substring(0, 1);

        final StringSubstitutor substitutor = new StringSubstitutor(buildValues());
        final String result = substitutor.replace(firstPrefixChar);

        assertEquals(firstPrefixChar, result);
    }
}
