package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} treats a single-letter input as
 * trivial: the encoder short-circuits on names of length one and returns an empty
 * string ("Not Input, Not Output").
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_One_Letter_to_Nothing
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_One_Letter_to_Nothing() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String encoded = encoder.encode("E");

        assertEquals("", encoded, "A single-letter name should encode to an empty string");
    }
}
