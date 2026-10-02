package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateMinMaxLength {

    private static final int MIN_GENERATED_CODE_POINTS = 0;
    private static final int MAX_GENERATED_CODE_POINTS = 3;

    private static int codePointLength(final String value) {
        return value.codePointCount(0, value.length());
    }

    @Test
    void testGenerateMinMaxLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        final String generated = generator.generate(MIN_GENERATED_CODE_POINTS, MAX_GENERATED_CODE_POINTS);
        final int generatedCodePointLength = codePointLength(generated);

        assertTrue(generatedCodePointLength >= MIN_GENERATED_CODE_POINTS && generatedCodePointLength <= MAX_GENERATED_CODE_POINTS);
    }
}
