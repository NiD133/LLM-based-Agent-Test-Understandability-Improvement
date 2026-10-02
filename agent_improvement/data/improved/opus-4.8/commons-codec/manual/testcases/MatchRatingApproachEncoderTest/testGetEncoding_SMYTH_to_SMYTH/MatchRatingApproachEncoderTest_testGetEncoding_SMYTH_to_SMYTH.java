package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} encodes the name "Smyth"
 * to the Match Rating Approach code "SMYTH".
 * <p>
 * "Smyth" has 5 characters, so encoding leaves it essentially unchanged:
 * uppercasing yields "SMYTH", removing non-leading vowels keeps it as "SMYTH"
 * (there are no vowels to drop), there are no double consonants to collapse,
 * and since the name is not longer than 6 characters it is returned in full.
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_SMYTH_to_SMYTH
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_SMYTH_to_SMYTH() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String encoded = encoder.encode("Smyth");

        assertEquals("SMYTH", encoded);
    }
}
