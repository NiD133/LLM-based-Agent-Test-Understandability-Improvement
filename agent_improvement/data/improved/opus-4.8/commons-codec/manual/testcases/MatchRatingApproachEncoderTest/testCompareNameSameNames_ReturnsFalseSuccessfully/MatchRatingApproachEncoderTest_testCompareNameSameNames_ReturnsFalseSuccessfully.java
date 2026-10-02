package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}.
 */
public class MatchRatingApproachEncoderTest_testCompareNameSameNames_ReturnsFalseSuccessfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the two names are identical, {@code isEncodeEquals} short-circuits via its
     * case-insensitive equality check and reports the names as homophonous.
     */
    @Test
    final void identicalNamesAreConsideredEqual() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesAreEqual = encoder.isEncodeEquals("John", "John");

        assertTrue(namesAreEqual, "Identical names should be reported as equal");
    }
}
