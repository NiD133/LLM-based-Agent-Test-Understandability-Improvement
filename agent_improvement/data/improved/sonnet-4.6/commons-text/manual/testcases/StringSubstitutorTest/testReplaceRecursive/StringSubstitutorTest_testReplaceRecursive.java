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

public class StringSubstitutorTest_testReplaceRecursive {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate)
            throws IOException {
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

    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult = substring
                ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;
        // replace using String
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d",
                StringUtils.indexOfDifference(expectedResult, actual)));
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
        // replace using object whose toString() returns the template
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));
        // replace in StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, buf.toString());
        }
        // replace in StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, builder.toString());
        }
        // replace in TextStringBuilder
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, bld.toString());
        }
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

    /**
     * Tests that StringSubstitutor resolves multi-level variable chains recursively.
     *
     * <p>The template {@code "The ${animal} jumps over the ${target}."} is resolved through
     * the following chain of lookups:</p>
     * <pre>
     *   ${animal}            -> ${critter}
     *   ${critter}           -> ${critterSpeed} ${critterColor} ${critterType}
     *   ${critterSpeed}      -> "quick"
     *   ${critterColor}      -> "brown"
     *   ${critterType}       -> "fox"
     *
     *   ${target}            -> ${pet}
     *   ${pet}               -> ${petCharacteristic} dog
     *   ${petCharacteristic} -> "lazy"
     * </pre>
     * <p>All chains eventually resolve to: {@value #CLASSIC_RESULT}</p>
     *
     * <p>A second pass verifies the same outcome when the leaf variable uses a default-value
     * expression ({@code ${petCharacteristicUnknown:-lazy}}) whose key is absent from the map.</p>
     */
    @Test
    void testReplaceRecursive() throws IOException {
        // Set up a multi-level variable chain for ${animal}
        values.put("animal", "${critter}");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");

        // Set up a multi-level variable chain for ${target}
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");

        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);

        // Same result when the leaf uses a default value because "petCharacteristicUnknown" is absent
        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);
    }
}
