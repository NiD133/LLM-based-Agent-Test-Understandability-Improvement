package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceEmptyKeyShortest {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String EMPTY_VARIABLE_EXPRESSION = "${}";

    protected Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // shortest key and value.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal key and value.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
        assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));

        final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(replaceTemplate, builder.toString());
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    /**
     * Tests when no variable name.
     */
    @Test
    void testReplaceEmptyKeyShortest() throws IOException {
        doNotReplace(EMPTY_VARIABLE_EXPRESSION);
    }
}
