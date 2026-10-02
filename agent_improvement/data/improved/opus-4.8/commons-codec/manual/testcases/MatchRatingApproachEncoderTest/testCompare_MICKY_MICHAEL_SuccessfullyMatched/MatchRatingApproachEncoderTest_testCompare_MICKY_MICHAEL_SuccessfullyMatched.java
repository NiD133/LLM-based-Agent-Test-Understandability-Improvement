package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}.
 */
public class MatchRatingApproachEncoderTest_testCompare_MICKY_MICHAEL_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Micky" and "Michael" are phonetically similar enough that the Match Rating
     * Approach algorithm considers them a match, so {@code isEncodeEquals} returns {@code true}.
     */
    @Test
    final void testCompare_MICKY_MICHAEL_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Micky", "Michael");

        assertTrue(namesMatch, "'Micky' and 'Michael' should be matched as homophones");
    }
}
