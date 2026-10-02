package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_LongSurnames_OMUIRCHEARTAIGH_OMIREADHAIGH_SuccessfulMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The MRA algorithm strips accents, punctuation, and spaces before phonetic encoding,
     * so the accented Irish form "Ó 'Muircheartaigh" and the anglicised form "o'muireadhaigh"
     * should resolve to the same encoding and be considered a match.
     */
    @Test
    @DisplayName("MRA encodeEquals: Irish surname variants 'o'muireadhaigh' and 'Ó 'Muircheartaigh' are phonetically equivalent")
    final void testCompare_LongSurnames_OMUIRCHEARTAIGH_OMIREADHAIGH_SuccessfulMatch() {
        String anglicisedForm = "o'muireadhaigh";
        String irishForm = "Ó 'Muircheartaigh ";

        assertTrue(getStringEncoder().isEncodeEquals(anglicisedForm, irishForm));
    }
}
