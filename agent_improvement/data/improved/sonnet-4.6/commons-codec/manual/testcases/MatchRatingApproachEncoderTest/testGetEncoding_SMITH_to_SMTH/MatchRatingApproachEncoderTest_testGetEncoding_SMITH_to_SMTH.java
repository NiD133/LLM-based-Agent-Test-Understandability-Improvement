package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that the Match Rating Approach encoder correctly encodes "Smith" to "SMTH".
 *
 * The MRA algorithm steps for "Smith":
 *   1. Uppercase:             "SMITH"
 *   2. Remove interior vowels (I): "SMTH"
 *   3. No double consonants to collapse.
 *   4. Length <= 6, so the full string is returned: "SMTH"
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_SMITH_to_SMTH
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_SMITH_to_SMTH() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        // "Smith" → uppercase "SMITH" → remove interior vowel 'I' → "SMTH"
        assertEquals("SMTH", encoder.encode("Smith"));
    }
}
