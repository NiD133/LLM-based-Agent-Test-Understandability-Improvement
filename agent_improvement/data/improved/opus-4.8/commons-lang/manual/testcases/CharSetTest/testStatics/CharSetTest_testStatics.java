package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Verifies that each predefined {@link CharSet} constant is backed by exactly the
 * character ranges its name implies.
 */
public class CharSetTest_testStatics extends AbstractLangTest {

    @Test
    void testStatics() {
        // EMPTY contains no ranges at all.
        final Set<CharRange> emptyRanges = CharSet.EMPTY.getCharRanges();
        assertEquals(0, emptyRanges.size());

        // ASCII_ALPHA ("a-zA-Z") combines the lower- and upper-case letter ranges.
        final Set<CharRange> alphaRanges = CharSet.ASCII_ALPHA.getCharRanges();
        assertEquals(2, alphaRanges.size());
        assertTrue(alphaRanges.contains(CharRange.isIn('a', 'z')));
        assertTrue(alphaRanges.contains(CharRange.isIn('A', 'Z')));

        // ASCII_ALPHA_LOWER ("a-z") holds only the lower-case letter range.
        final Set<CharRange> alphaLowerRanges = CharSet.ASCII_ALPHA_LOWER.getCharRanges();
        assertEquals(1, alphaLowerRanges.size());
        assertTrue(alphaLowerRanges.contains(CharRange.isIn('a', 'z')));

        // ASCII_ALPHA_UPPER ("A-Z") holds only the upper-case letter range.
        final Set<CharRange> alphaUpperRanges = CharSet.ASCII_ALPHA_UPPER.getCharRanges();
        assertEquals(1, alphaUpperRanges.size());
        assertTrue(alphaUpperRanges.contains(CharRange.isIn('A', 'Z')));

        // ASCII_NUMERIC ("0-9") holds only the digit range.
        final Set<CharRange> numericRanges = CharSet.ASCII_NUMERIC.getCharRanges();
        assertEquals(1, numericRanges.size());
        assertTrue(numericRanges.contains(CharRange.isIn('0', '9')));
    }
}
