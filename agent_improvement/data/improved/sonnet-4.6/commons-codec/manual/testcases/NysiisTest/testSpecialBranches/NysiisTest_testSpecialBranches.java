package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testSpecialBranches extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testSpecialBranches() {
        // K -> C prefix transcoding; W -> previous vowel when prior char is a vowel
        assertEquals("CABWAC", getStringEncoder().encode("Kobwick"), "Problem with Kobwick");

        // K -> C prefix transcoding; H -> previous char when adjacent to non-vowel
        assertEquals("CACAR", getStringEncoder().encode("Kocher"), "Problem with Kocher");

        // S-C without following H (does not trigger SCH -> SSS); trailing A removal
        assertEquals("FASC", getStringEncoder().encode("Fesca"), "Problem with Fesca");

        // H -> previous char when adjacent to non-vowel (SH: H takes prev S); M -> N
        assertEquals("SAN", getStringEncoder().encode("Shom"), "Problem with Shom");

        // H -> previous char when adjacent to non-vowel; trailing A removal
        assertEquals("OL", getStringEncoder().encode("Ohlo"), "Problem with Ohlo");

        // H -> previous char when both neighbors are vowels (U-H-U: keep H as-is)
        assertEquals("UH", getStringEncoder().encode("Uhu"), "Problem with Uhu");

        // M -> N transcoding on a two-character word
        assertEquals("UN", getStringEncoder().encode("Um"), "Problem with Um");
    }
}
