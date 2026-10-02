package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomStringGeneratorTest_testSelectFromCharVarargs3 {

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testSelectFromCharVarargs3(final boolean accumulate) {
        final String selectedCharacters = "abcde";

        final RandomStringGenerator generator = RandomStringGenerator.builder()
            .setAccumulate(accumulate)
            .selectFrom('a', 'b', 'c', 'd', 'e')
            .selectFrom('a', 'b', 'c', 'd')
            .selectFrom('a', 'b', 'c')
            .selectFrom('a', 'b')
            .selectFrom(null)
            .selectFrom()
            .get();

        final String randomText = generator.generate(10);
        for (final char generatedChar : randomText.toCharArray()) {
            assertEquals(accumulate, selectedCharacters.indexOf(generatedChar) != -1);
        }
    }
}
