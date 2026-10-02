package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}.
 *
 * <p>The Match Rating Approach is a phonetic algorithm: two names are considered
 * homophonous when their cleaned, de-voweled encodings are close enough. Before the
 * comparison, {@code isEncodeEquals} normalizes each input by upper-casing it, stripping
 * accents, removing punctuation, and removing spaces. This test verifies that two names
 * differing only by such "noise" are still recognized as a phonetic match.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_COLM_COLIN_WithAccentsAndSymbolsAndSpaces_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Cólm.   " (accented vowel, trailing dot and spaces) and "C-olín" (embedded hyphen,
     * accented vowel) both normalize to the same phonetic encoding, so they should be
     * reported as a match.
     */
    @Test
    final void colmAndColinMatchDespiteAccentsSymbolsAndSpaces() {
        final String nameWithAccentAndTrailingSymbols = "Cólm.   ";
        final String nameWithHyphenAndAccent = "C-olín";

        final boolean namesAreHomophonous =
                getStringEncoder().isEncodeEquals(nameWithAccentAndTrailingSymbols, nameWithHyphenAndAccent);

        assertTrue(namesAreHomophonous,
                "Names that differ only by accents, symbols, and spaces should match");
    }
}
