package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromEmptyCharVarargs {

    @Test
    void testSelectFromEmptyCharVarargs() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom().get();
        final String generatedText = generator.generate(5);

        for (final char character : generatedText.toCharArray()) {
            assertTrue(character >= Character.MIN_CODE_POINT && character <= Character.MAX_CODE_POINT);
        }
    }
}
