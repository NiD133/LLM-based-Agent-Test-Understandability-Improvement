package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceNoPrefixNoSuffix {

    private Map<String, String> values;

    /**
     * Exercises all overloads of {@code replace} and {@code replaceIn} with the given
     * template, asserting that every overload produces {@code expectedResult}.
     *
     * <p>When {@code testSubstring} is {@code true}, each overload is also called with a
     * window that trims one character from each end of the template; the expected result for
     * those calls is the same {@code expectedResult} with its first and last characters
     * removed.</p>
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean testSubstring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, testSubstring);
    }

    private void doTestReplace(final StrSubstitutor sub, final String expectedResult,
                               final String replaceTemplate, final boolean testSubstring) {
        // The short result trims the first and last characters so substring calls can be verified.
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(String) ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (testSubstring) {
            assertEquals(expectedSubstringResult,
                    sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) ---
        final char[] templateChars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(templateChars));
        if (testSubstring) {
            assertEquals(expectedSubstringResult,
                    sub.replace(templateChars, 1, templateChars.length - 2));
        }

        // --- replace(StringBuffer) ---
        StringBuffer stringBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuffer));
        if (testSubstring) {
            assertEquals(expectedSubstringResult,
                    sub.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        // --- replace(StringBuilder) ---
        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuilder));
        if (testSubstring) {
            assertEquals(expectedSubstringResult,
                    sub.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        // --- replace(StrBuilder) ---
        StrBuilder strBuilder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(strBuilder));
        if (testSubstring) {
            assertEquals(expectedSubstringResult,
                    sub.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        // --- replace(Object) — toString() of the MutableObject returns the template ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) — mutates the buffer in place ---
        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuffer));
        assertEquals(expectedResult, stringBuffer.toString());
        if (testSubstring) {
            stringBuffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            // Characters outside the specified window are untouched, so the full
            // buffer matches expectedResult (not just the windowed portion).
            assertEquals(expectedResult, stringBuffer.toString());
        }

        // --- replaceIn(StringBuilder) — mutates the builder in place ---
        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (testSubstring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());
        }

        // --- replaceIn(StrBuilder) — mutates the builder in place ---
        strBuilder = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
        if (testSubstring) {
            strBuilder = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
            assertEquals(expectedResult, strBuilder.toString());
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Verifies that plain text (i.e., text without the {@code ${...}} prefix/suffix notation)
     * is left unchanged, while variables that ARE wrapped in {@code ${...}} are resolved.
     *
     * <p>In the template {@code "The animal jumps over the ${target}."}, the word {@code animal}
     * appears as literal text — it has no surrounding {@code ${}} markers — so it is NOT
     * substituted. Only {@code ${target}} is resolved (to {@code "lazy dog"} from the values map),
     * producing {@code "The animal jumps over the lazy dog."}.</p>
     */
    @Test
    void testReplaceNoPrefixNoSuffix() {
        doTestReplace("The animal jumps over the lazy dog.", "The animal jumps over the ${target}.", true);
    }
}
