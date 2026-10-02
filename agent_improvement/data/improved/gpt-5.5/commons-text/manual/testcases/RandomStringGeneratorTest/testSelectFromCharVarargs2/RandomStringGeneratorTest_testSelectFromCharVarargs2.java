package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomStringGeneratorTest_testSelectFromCharVarargs2 {

    private static final String EXPECTED_CHARACTERS = "abcde";
    private static final int GENERATED_LENGTH = 10;

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testSelectFromCharVarargs2(final boolean accumulate) {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(accumulate)
                .selectFrom()
                .selectFrom(null)
                .selectFrom('a', 'b')
                .selectFrom('a', 'b', 'c')
                .selectFrom('a', 'b', 'c', 'd')
                .selectFrom('a', 'b', 'c', 'd', 'e')
                .get();

        final String randomText = generator.generate(GENERATED_LENGTH);
        for (final char generatedCharacter : randomText.toCharArray()) {
            assertTrue(EXPECTED_CHARACTERS.indexOf(generatedCharacter) != -1);
        }
    }
}
