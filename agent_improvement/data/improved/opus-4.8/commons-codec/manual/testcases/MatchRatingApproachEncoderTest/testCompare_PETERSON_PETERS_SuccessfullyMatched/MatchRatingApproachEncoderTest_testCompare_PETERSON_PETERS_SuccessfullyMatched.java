package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats the phonetically similar names "Peterson" and "Peters" as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_PETERSON_PETERS_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    @DisplayName("isEncodeEquals matches the similar names 'Peterson' and 'Peters'")
    final void testCompare_PETERSON_PETERS_SuccessfullyMatched() {
        // Given two names that the Match Rating Approach should consider homophonous
        final String firstName = "Peterson";
        final String secondName = "Peters";

        // When comparing the two names
        final boolean namesAreEncodedEqual =
                getStringEncoder().isEncodeEquals(firstName, secondName);

        // Then the encoder reports them as a match
        assertTrue(namesAreEncodedEqual,
                "Expected 'Peterson' and 'Peters' to be matched by the Match Rating Approach");
    }
}
