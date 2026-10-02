package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromNullCharVarargs {

    private static final char SELECTED_CHARACTER = 'a';
    private static final int GENERATED_CODE_POINT_LENGTH = 5;

    private static int codePointLength(final String value) {
        return value.codePointCount(0, value.length());
    }

    private static void assertGeneratedLengthAndValidCodePoints(final String randomText) {
        assertEquals(GENERATED_CODE_POINT_LENGTH, codePointLength(randomText));
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }

    private static void assertContainsOnlySelectedCharacter(final String randomText) {
        for (final char c : randomText.toCharArray()) {
            assertEquals(SELECTED_CHARACTER, c);
        }
    }

    @Test
    void testSelectFromNullCharVarargs() {
        RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(null).get();
        String randomText = generator.generate(GENERATED_CODE_POINT_LENGTH);
        assertGeneratedLengthAndValidCodePoints(randomText);

        final Builder builder = RandomStringGenerator.builder().selectFrom(SELECTED_CHARACTER);
        generator = builder.get();
        randomText = generator.generate(GENERATED_CODE_POINT_LENGTH);
        assertContainsOnlySelectedCharacter(randomText);

        generator = builder.selectFrom(null).get();
        randomText = generator.generate(GENERATED_CODE_POINT_LENGTH);
        assertGeneratedLengthAndValidCodePoints(randomText);
    }
}
