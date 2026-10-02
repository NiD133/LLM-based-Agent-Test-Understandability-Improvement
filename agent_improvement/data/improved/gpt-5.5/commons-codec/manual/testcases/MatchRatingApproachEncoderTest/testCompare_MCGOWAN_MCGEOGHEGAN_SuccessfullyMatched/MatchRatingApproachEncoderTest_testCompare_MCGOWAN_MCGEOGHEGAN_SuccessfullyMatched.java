package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_MCGOWAN_MCGEOGHEGAN_SuccessfullyMatched {

    @Test
    final void testCompare_MCGOWAN_MCGEOGHEGAN_SuccessfullyMatched() {
        final String name = "McGowan";
        final String matchingVariant = "Mc Geoghegan";

        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final boolean namesMatch = encoder.isEncodeEquals(name, matchingVariant);

        assertTrue(namesMatch);
    }
}
