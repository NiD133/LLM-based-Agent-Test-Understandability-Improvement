package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromCharVarargSize1 {

    private static final char ONLY_SELECTABLE_CHARACTER = 'a';

    private static final int GENERATED_LENGTH = 5;

    @Test
    void testSelectFromCharVarargSize1() {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .selectFrom(ONLY_SELECTABLE_CHARACTER)
                .get();

        final String generatedText = generator.generate(GENERATED_LENGTH);

        for (final char generatedCharacter : generatedText.toCharArray()) {
            assertEquals(ONLY_SELECTABLE_CHARACTER, generatedCharacter);
        }
    }
}
