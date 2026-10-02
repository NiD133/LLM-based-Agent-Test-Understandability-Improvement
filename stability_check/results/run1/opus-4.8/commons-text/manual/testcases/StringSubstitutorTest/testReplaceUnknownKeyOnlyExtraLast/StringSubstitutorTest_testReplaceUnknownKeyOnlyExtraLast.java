package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link StringSubstitutor} handles a template that references a
 * variable which is not defined in the lookup values.
 */
public class StringSubstitutorTest_testReplaceUnknownKeyOnlyExtraLast {

    private static final String ANIMAL = "quick brown fox";

    private static final String TARGET = "lazy dog";

    /**
     * Lookup values passed to the substitutor. Note that no {@code "person"}
     * key is defined here, so any template referencing it is left untouched.
     */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // short keys and values
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal keys and values
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * An unknown variable reference must be left in place verbatim, including
     * any trailing characters that follow the variable expression.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraLast() throws IOException {
        final String templateWithUnknownKey = "${person}.";

        final String result = new StringSubstitutor(values).replace(templateWithUnknownKey);

        assertEquals(templateWithUnknownKey, result);
    }
}
