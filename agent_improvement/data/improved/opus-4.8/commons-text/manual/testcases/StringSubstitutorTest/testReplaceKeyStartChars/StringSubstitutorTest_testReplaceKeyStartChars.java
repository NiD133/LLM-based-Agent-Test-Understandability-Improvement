package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} behaviour when a template contains the
 * variable-start sequence ({@code ${}) but no matching variable-end sequence.
 */
public class StringSubstitutorTest_testReplaceKeyStartChars {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Variable values made available to the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // shortest possible keys and values
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal keys and values
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    /**
     * A template that opens a variable ("${") but never closes it is not a
     * complete variable reference, so it must be returned unchanged.
     */
    @Test
    void testReplaceKeyStartChars() throws IOException {
        final String unterminatedVariable = StringSubstitutor.DEFAULT_VAR_START + "a";

        final String result = new StringSubstitutor(values).replace(unterminatedVariable);

        assertEquals(unterminatedVariable, result);
    }
}
