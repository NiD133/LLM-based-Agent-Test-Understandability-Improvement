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

/**
 * Tests {@link StringSubstitutor}'s ability to resolve placeholders recursively,
 * i.e. when a substituted value itself contains further placeholders.
 */
public class StringSubstitutorTest_testReplaceRecursive {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    /** Template with two top-level placeholders. */
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** Fully resolved form of {@link #CLASSIC_TEMPLATE}. */
    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    /** Variable name to value mapping shared by a single test run; rebuilt before each test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // shortest possible keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal keys and values.
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that placeholders are resolved recursively: {@code animal} expands to
     * {@code ${critter}}, which in turn expands to {@code ${critterSpeed} ${critterColor} ${critterType}},
     * and so on until no placeholders remain. The second pass additionally exercises
     * the {@code ${name:-default}} fallback syntax.
     */
    @Test
    void testReplaceRecursive() throws IOException {
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");
        assertReplaces(CLASSIC_RESULT, CLASSIC_TEMPLATE);

        // Same expected result, but the lazy characteristic now comes from a default value.
        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        assertReplaces(CLASSIC_RESULT, CLASSIC_TEMPLATE);
    }

    /**
     * Asserts that a substitutor built from the current {@link #values} resolves
     * {@code template} to {@code expectedResult}, checking every {@code replace}/{@code replaceIn}
     * overload as well as the offset/length (substring) variants.
     */
    private void assertReplaces(final String expectedResult, final String template) throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // Resolving only the inner part of the template (dropping the first and last
        // character) must yield the expected result minus its first and last character.
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        // replace(...) returning a new String, for each supported source type.
        assertEquals(expectedResult, sub.replace(template),
            () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, sub.replace(template))));
        assertEquals(expectedSubstringResult, sub.replace(template, 1, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        assertEquals(expectedSubstringResult, sub.replace(chars, 1, chars.length - 2));

        final StringBuffer stringBuffer = new StringBuffer(template);
        assertEquals(expectedResult, sub.replace(stringBuffer));
        assertEquals(expectedSubstringResult, sub.replace(stringBuffer, 1, stringBuffer.length() - 2));

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expectedResult, sub.replace(stringBuilder));
        assertEquals(expectedSubstringResult, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));

        final TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expectedResult, sub.replace(textBuilder));
        assertEquals(expectedSubstringResult, sub.replace(textBuilder, 1, textBuilder.length() - 2));

        // replace(Object): the object's toString() returns the template.
        final MutableObject<String> obj = new MutableObject<>(template);
        assertEquals(expectedResult, sub.replace(obj));

        // replaceIn(...) mutating the source in place and reporting whether anything changed.
        assertReplacesInPlace(sub, expectedResult, template);
    }

    /**
     * Asserts the in-place {@code replaceIn} overloads (full and substring range) for every
     * mutable source type. For the substring variants the full expected result is still
     * expected, because the untouched remainder already holds the original characters.
     */
    private void assertReplacesInPlace(final StringSubstitutor sub, final String expectedResult, final String template) {
        StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(stringBuffer), template);
        assertEquals(expectedResult, stringBuffer.toString());
        stringBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
        assertEquals(expectedResult, stringBuffer.toString());

        StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        stringBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
        assertEquals(expectedResult, stringBuilder.toString());

        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
        textBuilder = new TextStringBuilder(template);
        assertTrue(sub.replaceIn(textBuilder, 1, textBuilder.length() - 2));
        assertEquals(expectedResult, textBuilder.toString());
    }
}
