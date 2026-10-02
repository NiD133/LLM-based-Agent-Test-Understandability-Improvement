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

public class StringSubstitutorTest_testReplaceSimpleKeySize1 {

    private static final String SIMPLE_KEY_TEMPLATE = "${a}";
    private static final String SIMPLE_KEY_VALUE = "1";

    protected Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("a", SIMPLE_KEY_VALUE);
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSimpleKeySize1() throws IOException {
        assertTemplateReplacesAcrossAllSupportedInputs(SIMPLE_KEY_VALUE, SIMPLE_KEY_TEMPLATE);
    }

    private void assertTemplateReplacesAcrossAllSupportedInputs(final String expectedResult, final String template)
        throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        assertStringInputReplacement(substitutor, expectedResult, template);
        assertCharArrayInputReplacement(substitutor, expectedResult, template);
        assertStringBufferInputReplacement(substitutor, expectedResult, template);
        assertStringBuilderInputReplacement(substitutor, expectedResult, template);
        assertTextStringBuilderInputReplacement(substitutor, expectedResult, template);
        assertObjectInputReplacement(substitutor, expectedResult, template);
        assertMutableBufferReplacement(substitutor, expectedResult, template);
    }

    private void assertStringInputReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) throws IOException {
        final String actual = replace(substitutor, template);
        assertEquals(expectedResult, actual,
            () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
    }

    private void assertCharArrayInputReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) {
        assertEquals(expectedResult, substitutor.replace(template.toCharArray()));
    }

    private void assertStringBufferInputReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) {
        assertEquals(expectedResult, substitutor.replace(new StringBuffer(template)));
    }

    private void assertStringBuilderInputReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) {
        assertEquals(expectedResult, substitutor.replace(new StringBuilder(template)));
    }

    private void assertTextStringBuilderInputReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) {
        assertEquals(expectedResult, substitutor.replace(new TextStringBuilder(template)));
    }

    private void assertObjectInputReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) {
        assertEquals(expectedResult, substitutor.replace(new MutableObject<>(template)));
    }

    private void assertMutableBufferReplacement(
        final StringSubstitutor substitutor, final String expectedResult, final String template) {
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), template);
        assertEquals(expectedResult, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
