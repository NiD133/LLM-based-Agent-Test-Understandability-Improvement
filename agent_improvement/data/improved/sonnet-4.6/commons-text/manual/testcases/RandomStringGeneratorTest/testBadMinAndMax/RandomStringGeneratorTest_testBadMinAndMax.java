package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBadMinAndMax {

    @Test
    @DisplayName("withinRange should throw IllegalArgumentException when min > max")
    void testBadMinAndMax() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(2, 1));
    }
}
