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
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} behaviour when a variable has an empty key but specifies a
 * default value using the bash-style {@code :-} delimiter (e.g. {@code ${:-animal}} resolves to
 * the literal string "animal").
 */
public class StringSubstitutorTest_testReplaceEmptyKeyWithDefault {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d", StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Verifies that the given substitutor leaves {@code replaceTemplate} unchanged (i.e. no
     * substitutions are performed). When {@code replaceTemplate} is {@code null}, every overload
     * must return {@code null} / {@code false}.
     */
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
        } else {
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    /**
     * Verifies that substituting {@code replaceTemplate} with {@code sub} produces
     * {@code expectedResult} across every supported input type (String, char[], StringBuffer,
     * StringBuilder, TextStringBuilder, and Object). When {@code substring} is {@code true}, also
     * verifies that operating on a sub-range of the template produces the expected partial result.
     */
    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;
        // replace using String
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }
        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }
        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }
        // replace using TextStringBuilder
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }
        // replace using Object (toString() returns the template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));
        // replace in StringBuffer (in-place mutation)
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // remainder outside the sub-range is untouched, so full result still matches
            assertEquals(expectedResult, buf.toString());
        }
        // replace in StringBuilder (in-place mutation)
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // remainder outside the sub-range is untouched, so full result still matches
            assertEquals(expectedResult, builder.toString());
        }
        // replace in TextStringBuilder (in-place mutation)
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // remainder outside the sub-range is untouched, so full result still matches
            assertEquals(expectedResult, bld.toString());
        }
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)}. Subclasses may override to test
     * alternative replace entry points.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // single-character keys and values
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // descriptive keys used in the classic template
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that an empty variable key with a default value ({@code ${:-animal}}) resolves to
     * the default string "animal", while a normal variable ({@code ${target}}) resolves to its
     * mapped value "lazy dog".
     *
     * <p>Template: {@code "The ${:-animal} jumps over the ${target}."}
     * <br>Expected: {@code "The animal jumps over the lazy dog."}
     */
    @Test
    void testReplaceEmptyKeyWithDefault() throws IOException {
        doReplace("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.", true);
    }
}
