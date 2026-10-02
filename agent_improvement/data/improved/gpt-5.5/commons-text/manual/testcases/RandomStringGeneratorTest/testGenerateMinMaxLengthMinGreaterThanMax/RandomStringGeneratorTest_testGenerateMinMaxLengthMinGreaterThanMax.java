package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateMinMaxLengthMinGreaterThanMax {

    @Test
    void generateThrowsWhenMinimumLengthIsGreaterThanMaximumLength() {
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            final RandomStringGenerator generator = RandomStringGenerator.builder().get();
            generator.generate(1, 0);
        });
    }
}
