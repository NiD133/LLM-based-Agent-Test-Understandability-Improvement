package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_SMITH_to_SMTH {

    @Test
    final void testGetEncoding_SMITH_to_SMTH() {
        final String name = "Smith";
        final String expectedEncoding = "SMTH";

        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertEquals(expectedEncoding, encoder.encode(name));
    }
}
