package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#replace(String)} leaves a variable
 * expression untouched when its key is not present in the lookup map.
 */
public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnlyExtraFirst {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /**
     * Builds the lookup map shared by the substitutor. None of these keys match
     * the unknown variable used in the test; they only establish a realistic,
     * non-empty lookup context.
     */
    private static Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        // Short overlapping keys.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
        return values;
    }

    /**
     * The variable {@code ${U}} has no matching key in the lookup map, so the
     * template (including the leading literal ".") must be returned verbatim.
     */
    @Test
    void replaceLeavesUnknownVariableUnchanged() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(newValues());
        final String templateWithUnknownKey = ".${U}";

        final String result = substitutor.replace(templateWithUnknownKey);

        assertEquals(templateWithUnknownKey, result);
    }
}
