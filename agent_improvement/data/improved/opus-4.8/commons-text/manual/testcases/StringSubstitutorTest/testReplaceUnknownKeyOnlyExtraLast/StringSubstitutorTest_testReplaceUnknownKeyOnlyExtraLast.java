package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor#replace(String)} leaves a variable
 * expression untouched when its key is not present in the value map.
 */
public class StringSubstitutorTest_testReplaceUnknownKeyOnlyExtraLast {

    /** Animal value used by the well-known "animal" key. */
    private static final String ACTUAL_ANIMAL = "quick brown fox";

    /** Target value used by the well-known "target" key. */
    private static final String ACTUAL_TARGET = "lazy dog";

    /**
     * Builds the substitution map shared with the original test. None of these
     * keys is "person", so a {@code ${person}} expression must stay unresolved.
     */
    private Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
        return values;
    }

    /**
     * An expression whose key ("person") is unknown should be returned verbatim,
     * including the trailing literal text after it.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraLast() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(newValues());
        final String unresolvedExpression = "${person}.";

        final String actual = substitutor.replace(unresolvedExpression);

        assertEquals(unresolvedExpression, actual);
    }
}
