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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest_testReplaceRecursive {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    private static final String ANIMAL_VARIABLE = "animal";
    private static final String CRITTER_VARIABLE = "critter";
    private static final String CRITTER_COLOR_VARIABLE = "critterColor";
    private static final String CRITTER_SPEED_VARIABLE = "critterSpeed";
    private static final String CRITTER_TYPE_VARIABLE = "critterType";
    private static final String PET_CHARACTERISTIC_VARIABLE = "petCharacteristic";
    private static final String PET_VARIABLE = "pet";
    private static final String TARGET_VARIABLE = "target";

    protected Map<String, String> values;

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        assertReplacementAcrossSupportedInputs(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void assertReplacementAcrossSupportedInputs(final StringSubstitutor substitutor, final String expectedResult,
        final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        final String actual = replace(substitutor, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        StringBuffer stringBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(stringBuffer));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(stringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        TextStringBuilder textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(textStringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(textStringBuilder, 1, textStringBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringIsTheTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringIsTheTemplate));

        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(stringBuffer), replaceTemplate);
        assertEquals(expectedResult, stringBuffer.toString());
        if (substring) {
            stringBuffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expectedResult, stringBuffer.toString());
        }

        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());
        }

        textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
        if (substring) {
            textStringBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(textStringBuilder, 1, textStringBuilder.length() - 2));
            assertEquals(expectedResult, textStringBuilder.toString());
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
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put(ANIMAL_VARIABLE, ACTUAL_ANIMAL);
        values.put(TARGET_VARIABLE, ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceRecursive() throws IOException {
        values.put(ANIMAL_VARIABLE, "${critter}");
        values.put(TARGET_VARIABLE, "${pet}");
        values.put(PET_VARIABLE, "${petCharacteristic} dog");
        values.put(PET_CHARACTERISTIC_VARIABLE, "lazy");
        values.put(CRITTER_VARIABLE, "${critterSpeed} ${critterColor} ${critterType}");
        values.put(CRITTER_SPEED_VARIABLE, "quick");
        values.put(CRITTER_COLOR_VARIABLE, "brown");
        values.put(CRITTER_TYPE_VARIABLE, "fox");

        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);

        values.put(PET_VARIABLE, "${petCharacteristicUnknown:-lazy} dog");
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);
    }
}
