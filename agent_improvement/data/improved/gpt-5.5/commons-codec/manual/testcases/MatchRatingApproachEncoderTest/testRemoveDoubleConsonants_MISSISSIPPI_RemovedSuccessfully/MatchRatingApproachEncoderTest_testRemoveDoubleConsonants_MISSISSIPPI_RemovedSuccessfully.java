package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveDoubleConsonants_MISSISSIPPI_RemovedSuccessfully {

    @Test
    final void testRemoveDoubleConsonants_MISSISSIPPI_RemovedSuccessfully() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final String nameWithRepeatedConsonants = "MISSISSIPPI";
        final String nameWithoutRepeatedConsonants = "MISISIPI";

        assertEquals(nameWithoutRepeatedConsonants,
                encoder.removeDoubleConsonants(nameWithRepeatedConsonants));
    }
}
