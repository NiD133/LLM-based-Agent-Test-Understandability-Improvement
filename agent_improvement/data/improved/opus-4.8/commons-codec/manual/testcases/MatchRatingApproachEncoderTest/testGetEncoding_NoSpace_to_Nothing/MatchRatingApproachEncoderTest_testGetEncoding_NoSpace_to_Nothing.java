package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder} treats an empty input string as
 * a trivial case and returns an empty string (rather than producing any phonetic code).
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_NoSpace_to_Nothing
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_NoSpace_to_Nothing() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String encodedEmptyString = encoder.encode("");

        assertEquals("", encodedEmptyString, "Encoding an empty string should yield an empty string");
    }
}
