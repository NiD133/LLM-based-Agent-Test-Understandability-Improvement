package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Forenames_SEAN_JOHN_MatchExpected extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the MRA algorithm considers "Sean" and "John" to be phonetically equivalent.
     * Both names reduce to the same encoded form after vowel removal, double-consonant removal,
     * and character comparison — yielding a similarity rating at or above the minimum threshold.
     */
    @Test
    final void testCompare_Forenames_SEAN_JOHN_MatchExpected() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        boolean seannAndJohnArePhoneticallySimilar = encoder.isEncodeEquals("Sean", "John");
        assertTrue(seannAndJohnArePhoneticallySimilar);
    }
}
