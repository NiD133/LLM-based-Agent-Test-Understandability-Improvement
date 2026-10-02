package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testPunctuationOnly extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A string composed entirely of punctuation characters (period, comma, hyphen) contains no
     * alphabetic content. The encoder strips these characters during cleaning, leaving an empty
     * result.
     */
    @Test
    final void testPunctuationOnly() {
        String punctuationOnlyInput = ".,-";
        String expectedEncoding = "";

        assertEquals(expectedEncoding, getStringEncoder().encode(punctuationOnlyInput));
    }
}
