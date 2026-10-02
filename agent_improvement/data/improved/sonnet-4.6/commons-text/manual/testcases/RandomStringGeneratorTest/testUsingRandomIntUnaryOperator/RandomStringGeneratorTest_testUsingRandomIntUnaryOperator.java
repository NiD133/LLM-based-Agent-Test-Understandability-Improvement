package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntUnaryOperator;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testUsingRandomIntUnaryOperator {

    @Test
    void testUsingRandomIntUnaryOperator() {
        // A deterministic operator that always returns 'a', regardless of input.
        // This verifies that usingRandom(IntUnaryOperator) plugs the custom RNG into
        // character selection so every generated code point comes from the operator.
        final char expectedChar = 'a';
        final IntUnaryOperator alwaysReturnA = n -> expectedChar;

        final String generated = RandomStringGenerator.builder()
                .usingRandom(alwaysReturnA)
                .get()
                .generate(10);

        for (final char c : generated.toCharArray()) {
            assertEquals(expectedChar, c);
        }
    }
}
