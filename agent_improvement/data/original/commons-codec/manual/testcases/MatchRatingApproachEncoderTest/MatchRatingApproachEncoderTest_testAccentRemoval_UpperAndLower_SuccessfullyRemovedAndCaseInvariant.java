package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant() {
        assertEquals("AeiOuu", getStringEncoder().removeAccents("ÁeíÓuu"));
    }
}
