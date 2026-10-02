package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator.Builder#withinRange(int, int)} confines every
 * generated code point to the configured inclusive range.
 */
public class RandomStringGeneratorTest_testWithinRange {

    /** Number of code points to generate; large enough to exercise the range thoroughly. */
    private static final int GENERATED_LENGTH = 5000;

    /** Lower bound (inclusive) of the allowed code-point range: the letter 'a'. */
    private static final int MIN_CODE_POINT = 'a';

    /** Upper bound (inclusive) of the allowed code-point range: the letter 'z'. */
    private static final int MAX_CODE_POINT = 'z';

    @Test
    void testWithinRange() {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange(MIN_CODE_POINT, MAX_CODE_POINT)
                .get();

        final String generated = generator.generate(GENERATED_LENGTH);

        // Walk the string code point by code point and assert each one falls inside the range.
        for (int offset = 0; offset < generated.length(); ) {
            final int codePoint = generated.codePointAt(offset);
            assertTrue(codePoint >= MIN_CODE_POINT && codePoint <= MAX_CODE_POINT,
                    "Code point " + codePoint + " is outside the range ["
                            + MIN_CODE_POINT + ", " + MAX_CODE_POINT + "]");
            offset += Character.charCount(codePoint);
        }
    }
}
