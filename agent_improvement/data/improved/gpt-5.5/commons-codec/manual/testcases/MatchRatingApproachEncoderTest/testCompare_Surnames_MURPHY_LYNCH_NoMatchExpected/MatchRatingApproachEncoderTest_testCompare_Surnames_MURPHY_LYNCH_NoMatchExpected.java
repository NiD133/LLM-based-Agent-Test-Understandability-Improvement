package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surnames_MURPHY_LYNCH_NoMatchExpected {

    @Test
    final void testCompare_Surnames_MURPHY_LYNCH_NoMatchExpected() {
        final String firstSurname = "Murphy";
        final String secondSurname = "Lynch";
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        final boolean surnamesAreEncodedAsEqual = encoder.isEncodeEquals(firstSurname, secondSurname);

        assertFalse(surnamesAreEncodedAsEqual);
    }
}
