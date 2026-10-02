package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link RandomStringGenerator.Builder#selectFrom(char...)}.
 */
public class RandomStringGeneratorTest_testSelectFromCharVarargs {

    /**
     * When the generator is restricted to a set of characters via
     * {@code selectFrom('a', 'b', 'c')}, every character of the generated string
     * must come from that allowed set.
     */
    @Test
    void testSelectFromCharVarargs() {
        final String allowedChars = "abc";

        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .selectFrom('a', 'b', 'c')
                .get();

        final String generated = generator.generate(5);

        for (final char generatedChar : generated.toCharArray()) {
            assertTrue(allowedChars.indexOf(generatedChar) != -1,
                    "Generated character '" + generatedChar + "' is not in the allowed set \"" + allowedChars + "\"");
        }
    }
}
