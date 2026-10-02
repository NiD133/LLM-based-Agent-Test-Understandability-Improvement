package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testZeroLength {

    /**
     * Requesting zero code points from a default generator should yield an empty string.
     */
    @Test
    void testZeroLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        final String generated = generator.generate(0);

        assertEquals("", generated);
    }
}
