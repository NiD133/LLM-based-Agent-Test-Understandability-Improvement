package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest_testReplaceToIdentical {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    private static final String IDENTICAL_RESULT = "The ${animal} jumps.";

    protected Map<String, String> values;

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
        if (replaceTemplate == null) {
            assertNull(replace(substitutor, (String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((TextStringBuilder) null));
            assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
            return;
        }

        assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
        final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(replaceTemplate, builder.toString());
    }

    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedSubstringResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedSubstringResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedSubstringResult, sub.replace(chars, 1, chars.length - 2));
        }

        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buffer));
        if (substring) {
            assertEquals(expectedSubstringResult, sub.replace(buffer, 1, buffer.length() - 2));
        }

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedSubstringResult, sub.replace(builder, 1, builder.length() - 2));
        }

        TextStringBuilder textBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(textBuilder));
        if (substring) {
            assertEquals(expectedSubstringResult, sub.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        final MutableObject<String> objectWithTemplateText = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(objectWithTemplateText));

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
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceToIdentical() throws IOException {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");
        doReplace(IDENTICAL_RESULT, IDENTICAL_RESULT, true);
    }
}
