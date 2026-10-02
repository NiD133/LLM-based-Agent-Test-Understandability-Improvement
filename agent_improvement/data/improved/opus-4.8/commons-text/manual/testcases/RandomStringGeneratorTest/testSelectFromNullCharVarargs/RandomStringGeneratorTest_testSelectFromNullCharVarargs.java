package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link Builder#selectFrom(char...)} reacts to a {@code null} argument.
 *
 * <p>Per the contract, passing {@code null} reverts the builder to its default behavior of
 * allowing any character, so the generated string is no longer restricted to a fixed
 * character set.</p>
 */
public class RandomStringGeneratorTest_testSelectFromNullCharVarargs {

    /** Number of code points every generated string in this test should contain. */
    private static final int EXPECTED_LENGTH = 5;

    /** Counts the number of Unicode code points in the given string. */
    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    /**
     * Asserts that {@code text} has the expected length and that every character is a valid
     * Unicode code point, i.e. it was produced by the default "allow any character" behavior.
     */
    private static void assertUnrestrictedText(final String text) {
        assertEquals(EXPECTED_LENGTH, codePointLength(text));
        for (final char c : text.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }

    @Test
    void testSelectFromNullCharVarargs() {
        // selectFrom(null) on a fresh builder keeps the default "any character" behavior.
        RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(null).get();
        assertUnrestrictedText(generator.generate(EXPECTED_LENGTH));

        // selectFrom('a') restricts the output to the single character 'a'.
        final Builder builder = RandomStringGenerator.builder().selectFrom('a');
        generator = builder.get();
        String restrictedText = generator.generate(EXPECTED_LENGTH);
        for (final char c : restrictedText.toCharArray()) {
            assertEquals('a', c);
        }

        // Calling selectFrom(null) on the same builder resets it back to "any character".
        generator = builder.selectFrom(null).get();
        assertUnrestrictedText(generator.generate(EXPECTED_LENGTH));
    }
}
