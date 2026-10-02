package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testZeroWidthWrapOnRegex {

    /**
     * A zero-width regex (e.g. a lookahead like {@code (?=a)}) matches without consuming
     * any characters. Feeding such a pattern to {@code wrap} could cause an infinite loop
     * if the implementation does not advance past a zero-length match.
     * This test asserts that the method terminates within 2 seconds and returns a non-null result.
     */
    @Test
    void testZeroWidthWrapOnRegex() {
        assertTimeout(Duration.ofSeconds(2),
                () -> assertNotNull(WordUtils.wrap("abcdef", 3, "\n", false, "(?=a)")));
    }
}
