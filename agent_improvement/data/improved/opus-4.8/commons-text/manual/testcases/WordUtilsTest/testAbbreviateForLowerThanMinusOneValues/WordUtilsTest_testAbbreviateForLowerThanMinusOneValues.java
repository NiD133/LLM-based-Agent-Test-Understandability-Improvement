package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#abbreviate(String, int, int, String)} when the {@code upper}
 * limit is set to a value below the only allowed negative value (-1).
 *
 * <p>The contract states the {@code upper} limit must be {@code >= -1} (use -1 for
 * "no limit"). Any value lower than -1 is invalid and must be rejected.</p>
 */
public class WordUtilsTest_testAbbreviateForLowerThanMinusOneValues {

    @Test
    void testAbbreviateForLowerThanMinusOneValues() {
        final String text = "01 23 45 67 89";
        final int lowerLimit = 9;
        final int invalidUpperLimit = -10; // below the minimum allowed value of -1

        assertThrows(IllegalArgumentException.class,
                () -> WordUtils.abbreviate(text, lowerLimit, invalidUpperLimit, null));
    }
}
