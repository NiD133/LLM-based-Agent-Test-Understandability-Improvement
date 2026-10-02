package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests NYSIIS Rule 5: If the last character of the encoded key is 'S', remove it.
 *
 * <p>This rule applies after the main transcoding pass. For example, an input
 * that produces a trailing 'S' in the key should have that 'S' stripped.
 * Multiple trailing S characters (e.g., "SS") are also reduced because the
 * duplicate-collapse step (Rule 8) runs before Rule 5, leaving at most one 'S'
 * to remove.</p>
 */
public class NysiisTest_testRule5 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict (full-length) encoder used for Rule 5 verification. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Verifies that a single trailing 'S' is removed (Rule 5).
     *
     * <p>"XS" encodes to "X" because the transcoded key ends in 'S', which is
     * then stripped.</p>
     */
    @Test
    void testRule5_singleTrailingS_isRemoved() {
        assertEquals("X", fullNysiis.encode("XS"), "Trailing S in 'XS' should be removed");
    }

    /**
     * Verifies that duplicate 'S' characters are collapsed then removed (Rule 5).
     *
     * <p>"XSS" produces a key ending in "SS"; after duplicate-collapse (Rule 8)
     * it becomes "XS", and then Rule 5 strips the remaining 'S', yielding "X".</p>
     */
    @Test
    void testRule5_duplicateTrailingS_collapsedThenRemoved() {
        assertEquals("X", fullNysiis.encode("XSS"), "Duplicate trailing S in 'XSS' should collapse then be removed");
    }
}
