package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateMinMaxLength {

    private static int countCodePoints(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    @DisplayName("generate(minLength, maxLength) produces a string whose code-point count is within [minLength, maxLength]")
    void testGenerateMinMaxLength() {
        final int minLength = 0;
        final int maxLength = 3;
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        final String result = generator.generate(minLength, maxLength);
        final int actualLength = countCodePoints(result);

        assertTrue(actualLength >= minLength,
                "Generated string length " + actualLength + " is less than minLength " + minLength);
        assertTrue(actualLength <= maxLength,
                "Generated string length " + actualLength + " exceeds maxLength " + maxLength);
    }
}
