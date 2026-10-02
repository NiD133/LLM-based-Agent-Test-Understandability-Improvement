package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceEmptyKeyWithDefault {

    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String EMPTY_KEY_DEFAULT_TEMPLATE = "The ${:-animal} jumps over the ${target}.";
    private static final String EMPTY_KEY_DEFAULT_RESULT = "The animal jumps over the lazy dog.";

    protected Map<String, String> values;

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buffer));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buffer, 1, buffer.length() - 2));
        }

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        TextStringBuilder textBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(textBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseStringValueIsTheTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(objectWhoseStringValueIsTheTemplate));

        buffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buffer), replaceTemplate);
        assertEquals(expectedResult, buffer.toString());
        if (substring) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }

        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        textBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
        if (substring) {
            textBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(textBuilder, 1, textBuilder.length() - 2));
            assertEquals(expectedResult, textBuilder.toString());
        }
    }

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
        values.put("animal", "quick brown fox");
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests replacement when the variable name is empty and a default value is provided.
     */
    @Test
    void testReplaceEmptyKeyWithDefault() throws IOException {
        doReplace(EMPTY_KEY_DEFAULT_RESULT, EMPTY_KEY_DEFAULT_TEMPLATE, true);
    }
}
