package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testZeroWidthWrapOnRegex {

    @Test
    void testZeroWidthWrapOnRegex() {
        assertTimeout(Duration.ofSeconds(2), () -> {
            assertNotNull(WordUtils.wrap("abcdef", 3, "\n", false, "(?=a)"));
        });
    }
}
