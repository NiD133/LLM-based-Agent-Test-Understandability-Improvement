package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_ROSOCHOWACIEC_ROSOKHOVATSETS_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the phonetically similar Polish surname "ROSOCHOWACIEC" and
     * Ukrainian transliteration "ROSOKHOVATSETS" are considered equivalent by the
     * Match Rating Approach algorithm. The strings use spaces between phonetic units
     * (e.g., "ch", "ie", "ho", "ts") to represent multi-character sounds that the
     * algorithm normalises during encoding.
     */
    @Test
    final void testCompare_Surname_ROSOCHOWACIEC_ROSOKHOVATSETS_SuccessfullyMatched() {
        String rosochowaciec = "R o s o ch o w a c ie c";
        String rosokhovatsets = " R o s o k ho v a ts e ts";

        assertTrue(getStringEncoder().isEncodeEquals(rosochowaciec, rosokhovatsets));
    }
}
