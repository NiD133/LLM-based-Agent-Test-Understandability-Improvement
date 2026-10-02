package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link MatchRatingApproachEncoder} handles a single blank space.
 *
 * <p>The encoder treats a lone space as trivial input, so encoding it should
 * yield an empty string rather than any phonetic code.</p>
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_Space_to_Nothing
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_Space_to_Nothing() {
        final String singleSpace = " ";
        final String expectedEncoding = "";

        final String actualEncoding = getStringEncoder().encode(singleSpace);

        assertEquals(expectedEncoding, actualEncoding,
                "Encoding a single space should produce an empty string");
    }
}
