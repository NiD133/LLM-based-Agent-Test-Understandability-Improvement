package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceEmptyKeyOnly {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String EMPTY_VARIABLE_EXPRESSION = "${}";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();

        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");

        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @Test
    void testReplaceEmptyKeyOnly() throws IOException {
        assertEquals(
                EMPTY_VARIABLE_EXPRESSION,
                replace(new StringSubstitutor(values), EMPTY_VARIABLE_EXPRESSION));
    }
}
