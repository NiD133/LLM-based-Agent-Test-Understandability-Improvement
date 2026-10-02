package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testZeroLength {

    @Test
    void testZeroLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        assertEquals("", generator.generate(0));
    }
}
