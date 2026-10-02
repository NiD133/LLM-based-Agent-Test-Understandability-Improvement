package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_ShortNames_AL_ED_WorksButNoMatch {

    private static final String SHORT_NAME_AL = "Al";
    private static final String SHORT_NAME_ED = "Ed";

    @Test
    final void doesNotMatchDifferentShortNamesAlAndEd() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertFalse(encoder.isEncodeEquals(SHORT_NAME_AL, SHORT_NAME_ED));
    }
}
