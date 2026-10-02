package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateTakingIntThrowsNullPointerException {

    private static final int GENERATED_LENGTH = 18;

    @Test
    void testGenerateTakingIntThrowsNullPointerException() {
        assertThrowsExactly(NullPointerException.class, () -> {
            final RandomStringGenerator.Builder generatorBuilder = RandomStringGenerator.builder();
            final CharacterPredicate[] predicatesWithNullEntries = new CharacterPredicate[2];

            generatorBuilder.filteredBy(predicatesWithNullEntries);
            final RandomStringGenerator generator = generatorBuilder.get();

            generator.generate(GENERATED_LENGTH);
        });
    }
}
