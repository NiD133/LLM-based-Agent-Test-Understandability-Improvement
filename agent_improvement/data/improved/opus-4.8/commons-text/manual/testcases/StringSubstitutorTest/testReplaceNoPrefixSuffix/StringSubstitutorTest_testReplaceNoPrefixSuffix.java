package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} replacement when a template contains a variable
 * suffix ("}") without a matching prefix ("${").
 */
public class StringSubstitutorTest_testReplaceNoPrefixSuffix {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /** Variable name to value mappings shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values, kept to mirror the original shared fixture.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // The two variables actually exercised by this test.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * A bare "}" suffix with no matching "${" prefix is not a variable, so "animal}"
     * is left untouched while the well-formed "${target}" is still substituted.
     */
    @Test
    void testReplaceNoPrefixSuffix() throws IOException {
        final String template = "The animal} jumps over the ${target}.";
        final String expected = "The animal} jumps over the lazy dog.";

        assertReplaceAcrossAllInputTypes(new StringSubstitutor(values), expected, template);
    }

    /**
     * Asserts that {@code substitutor} produces {@code expected} from {@code template}
     * across every {@code replace}/{@code replaceIn} overload, including the
     * offset/length variants which operate on a sub-range of the input.
     */
    private void assertReplaceAcrossAllInputTypes(final StringSubstitutor substitutor,
            final String expected, final String template) throws IOException {

        // The offset/length overloads skip the first and last characters of the input.
        // Because the substituted region is unchanged here, the trimmed input yields the
        // full result minus its first and last characters.
        final String expectedTrimmed = expected.substring(1, expected.length() - 1);

        // --- replace(...) overloads return a new String ---

        final String fromString = substitutor.replace(template);
        assertEquals(expected, fromString,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expected, fromString)));
        assertEquals(expectedTrimmed, substitutor.replace(template, 1, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertEquals(expected, substitutor.replace(chars));
        assertEquals(expectedTrimmed, substitutor.replace(chars, 1, chars.length - 2));

        final StringBuffer stringBuffer = new StringBuffer(template);
        assertEquals(expected, substitutor.replace(stringBuffer));
        assertEquals(expectedTrimmed, substitutor.replace(stringBuffer, 1, stringBuffer.length() - 2));

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expected, substitutor.replace(stringBuilder));
        assertEquals(expectedTrimmed, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertEquals(expected, substitutor.replace(textStringBuilder));
        assertEquals(expectedTrimmed, substitutor.replace(textStringBuilder, 1, textStringBuilder.length() - 2));

        // replace(Object) substitutes the object's toString() value.
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));

        // --- replaceIn(...) overloads mutate the buffer in place and report whether it changed ---

        assertReplaceInBuffers(substitutor, expected, template);
    }

    /**
     * Asserts the in-place {@code replaceIn} overloads. For the offset/length variants the
     * untouched first/last characters mean the buffer still ends up holding the full result.
     */
    private void assertReplaceInBuffers(final StringSubstitutor substitutor,
            final String expected, final String template) {

        StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), template);
        assertEquals(expected, stringBuffer.toString());
        stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
        assertEquals(expected, stringBuffer.toString());

        StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());
        stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
        assertEquals(expected, stringBuilder.toString());

        TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expected, textStringBuilder.toString());
        textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder, 1, textStringBuilder.length() - 2));
        assertEquals(expected, textStringBuilder.toString());
    }
}
