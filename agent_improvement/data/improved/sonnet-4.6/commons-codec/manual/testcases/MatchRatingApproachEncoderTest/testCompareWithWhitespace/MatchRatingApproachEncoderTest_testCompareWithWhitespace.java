package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals} strips leading and trailing
 * whitespace before comparing two names, so "Brian" and "Bryan" remain phonetically equivalent
 * regardless of surrounding spaces.
 */
public class MatchRatingApproachEncoderTest_testCompareWithWhitespace extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompareWithWhitespace() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        // Baseline: both names without whitespace are phonetically equivalent
        assertTrue(encoder.isEncodeEquals("Brian", "Bryan"),
                "\"Brian\" and \"Bryan\" should be phonetically equivalent");

        // Leading whitespace on the first name should not affect the result
        assertTrue(encoder.isEncodeEquals(" Brian", "Bryan"),
                "Leading space on first name should be ignored");

        // Trailing whitespace on the first name should not affect the result
        assertTrue(encoder.isEncodeEquals("Brian ", "Bryan"),
                "Trailing space on first name should be ignored");

        // Both leading and trailing whitespace on the first name should not affect the result
        assertTrue(encoder.isEncodeEquals(" Brian ", "Bryan"),
                "Leading and trailing spaces on first name should be ignored");

        // Leading whitespace on the second name should not affect the result
        assertTrue(encoder.isEncodeEquals("Brian", " Bryan"),
                "Leading space on second name should be ignored");

        // Trailing whitespace on the second name should not affect the result
        assertTrue(encoder.isEncodeEquals("Brian", "Bryan "),
                "Trailing space on second name should be ignored");

        // Both leading and trailing whitespace on the second name should not affect the result
        assertTrue(encoder.isEncodeEquals("Brian", " Bryan "),
                "Leading and trailing spaces on second name should be ignored");
    }
}
