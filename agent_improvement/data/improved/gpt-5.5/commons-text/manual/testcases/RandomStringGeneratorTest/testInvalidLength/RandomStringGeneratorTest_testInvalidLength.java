package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testInvalidLength {

    @Test
    void testInvalidLength() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().get().generate(-1));
    }
}
