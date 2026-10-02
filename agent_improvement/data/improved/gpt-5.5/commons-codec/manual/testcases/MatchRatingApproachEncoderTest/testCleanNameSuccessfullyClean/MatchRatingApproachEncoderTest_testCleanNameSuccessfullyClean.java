package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCleanNameSuccessfullyClean {

    private static final String NAME_WITH_ACCENTED_LETTER_SPACES_AND_PUNCTUATION = "This-ís   a t.,es &t";
    private static final String CLEANED_NAME = "THISISATEST";

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testCleanNameSuccessfullyClean() {
        assertEquals(CLEANED_NAME,
                getStringEncoder().cleanName(NAME_WITH_ACCENTED_LETTER_SPACES_AND_PUNCTUATION));
    }
}
