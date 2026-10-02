package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceEmptyKeyWithDefaultOnlyEmpty {

    private static final String EMPTY_KEY_WITH_EMPTY_DEFAULT = "${:-}";
    private static final String EXPECTED_EMPTY_REPLACEMENT = "";

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
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests an empty variable name that provides only an empty default value.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyEmpty() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        assertReplacesWithEmptyString(substitutor, EMPTY_KEY_WITH_EMPTY_DEFAULT);
        assertMutableInputsAreReplacedWithEmptyString(substitutor, EMPTY_KEY_WITH_EMPTY_DEFAULT);
    }

    private void assertReplacesWithEmptyString(final StringSubstitutor substitutor, final String template) throws IOException {
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, replace(substitutor, template));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, substitutor.replace(template.toCharArray()));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, substitutor.replace(new StringBuffer(template)));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, substitutor.replace(new StringBuilder(template)));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, substitutor.replace(new TextStringBuilder(template)));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, substitutor.replace(new MutableObject<>(template)));
    }

    private void assertMutableInputsAreReplacedWithEmptyString(final StringSubstitutor substitutor, final String template) {
        final StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, buffer.toString());

        final StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(EXPECTED_EMPTY_REPLACEMENT, textBuilder.toString());
    }

    /**
     * Preserves the original test's replacement hook for subclasses.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
