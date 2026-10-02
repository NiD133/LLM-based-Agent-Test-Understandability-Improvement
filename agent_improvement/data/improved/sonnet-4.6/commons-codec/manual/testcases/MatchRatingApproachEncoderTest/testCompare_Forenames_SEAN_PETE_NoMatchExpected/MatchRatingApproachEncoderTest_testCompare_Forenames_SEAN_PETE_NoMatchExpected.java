package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Forenames_SEAN_PETE_NoMatchExpected extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    // "Sean" encodes to "SN" and "Pete" encodes to "PT"; these share no
    // matching characters in the MRA comparison, so the similarity rating
    // falls below the minimum threshold and the names are not considered equivalent.
    @Test
    final void testCompare_Forenames_SEAN_PETE_NoMatchExpected() {
        assertFalse(getStringEncoder().isEncodeEquals("Sean", "Pete"));
    }
}
