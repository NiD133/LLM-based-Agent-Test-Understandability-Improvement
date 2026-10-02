package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link MatchRatingApproachEncoder} handles a {@code null} input.
 *
 * <p>The encoder treats trivial input (NINO - "Nothing In, Nothing Out") as a
 * special case and short-circuits before running the actual algorithm. A
 * {@code null} name is one such trivial input, so the encoder is expected to
 * return an empty string rather than throwing.</p>
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_Null_to_Nothing
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void encodingNullReturnsEmptyString() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String encoded = encoder.encode((String) null);

        assertEquals("", encoded, "Encoding a null name should yield an empty string");
    }
}
