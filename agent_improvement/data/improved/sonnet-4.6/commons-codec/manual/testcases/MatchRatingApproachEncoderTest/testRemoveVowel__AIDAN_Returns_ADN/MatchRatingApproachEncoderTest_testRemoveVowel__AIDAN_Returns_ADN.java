package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that removeVowels strips interior vowels while preserving a vowel that begins the word.
 * MRA rule: delete all vowels unless the vowel begins the word.
 * "AIDAN" -> keep leading 'A', remove interior 'I' and 'A' -> "ADN"
 */
public class MatchRatingApproachEncoderTest_testRemoveVowel__AIDAN_Returns_ADN extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testRemoveVowel__AIDAN_Returns_ADN() {
        // "AIDAN": leading 'A' is kept, interior vowels 'I' and 'A' are removed, leaving "ADN"
        String result = getStringEncoder().removeVowels("AIDAN");
        assertEquals("ADN", result);
    }
}
