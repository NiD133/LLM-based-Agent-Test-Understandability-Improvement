package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceChangedMap {

    private static final String ANIMAL = "quick brown fox";
    private static final String ORIGINAL_TARGET = "lazy dog";
    private static final String UPDATED_TARGET = "moon";

    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String ORIGINAL_RESULT = "The quick brown fox jumps over the lazy dog.";
    private static final String UPDATED_RESULT = "The quick brown fox jumps over the moon.";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", ANIMAL);
        values.put("target", ORIGINAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests key replace changing map after initialization (not recommended).
     */
    @Test
    void testReplaceChangedMap() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        assertEqualsCharSeq(ORIGINAL_RESULT, replace(substitutor, TEMPLATE));

        values.put("target", UPDATED_TARGET);
        assertEqualsCharSeq(UPDATED_RESULT, replace(substitutor, TEMPLATE));
    }
}
