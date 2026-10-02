package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testB650 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that a broad set of phonetically similar names beginning with 'B'
     * all encode to the Soundex code "B650". Soundex B650 represents names whose
     * consonant skeleton matches B-R/L-N/M (codes 6-5-0), covering many spelling
     * variants of surnames like Brown, Brain, Bruno, Byron, etc.
     */
    @Test
    void testB650() throws EncoderException {
        final String expectedCode = "B650";

        // Each of these surnames encodes to B650 under the US-English Soundex mapping:
        //   B -> B (first letter kept)
        //   R/L -> 6
        //   N/M -> 5
        //   trailing vowels/ignored letters -> 0
        final String[] namesEncodingToB650 = {
            // Bar- / Bir- / Boo- / Bor- variants
            "BARHAM", "BARONE", "BARRON",
            "BERNA", "BIRNEY", "BIRNIE",
            "BOOROM", "BOREN", "BORN",
            // Bou- / Bow- variants
            "BOURN", "BOURNE", "BOWRON",
            // Bra- / Bre- / Bri- variants
            "BRAIN", "BRAME", "BRANN", "BRAUN",
            "BREEN", "BRIEN",
            // Bri- / Bro- variants
            "BRIM", "BRIMM", "BRINN", "BRION",
            "BROOM", "BROOME",
            // Brown / Brow- variants
            "BROWN", "BROWNE",
            // Bru- variants
            "BRUEN", "BRUHN", "BRUIN", "BRUMM", "BRUN", "BRUNO",
            // Bry- / Bur- / By- variants
            "BRYAN", "BURIAN",
            "BURN", "BURNEY",
            "BYRAM", "BYRNE", "BYRON", "BYRUM"
        };

        checkEncodingVariations(expectedCode, namesEncodingToB650);
    }
}
