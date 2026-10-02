package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareNameSameNames_ReturnsFalseSuccessfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that two identical names are considered a match by the MRA algorithm.
     * The MRA encoder short-circuits when name1.equalsIgnoreCase(name2), returning true immediately.
     */
    @Test
    final void testCompareNameSameNames_ReturnsFalseSuccessfully() {
        boolean identicalNamesMatch = getStringEncoder().isEncodeEquals("John", "John");
        assertTrue(identicalNamesMatch);
    }
}
