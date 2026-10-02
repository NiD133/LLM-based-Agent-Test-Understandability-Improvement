package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats the two forenames "Sean" and "John" as a phonetic match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Forenames_SEAN_JOHN_MatchExpected
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Forenames_SEAN_JOHN_MatchExpected() {
        // Arrange: two forenames expected to be homophonous under the
        // Match Rating Approach algorithm.
        final String firstForename = "Sean";
        final String secondForename = "John";

        // Act
        final boolean namesAreEquivalent =
                getStringEncoder().isEncodeEquals(firstForename, secondForename);

        // Assert: the encoder should report the names as a match.
        assertTrue(namesAreEquivalent,
                "Expected '" + firstForename + "' and '" + secondForename
                        + "' to be encoded as a phonetic match");
    }
}
