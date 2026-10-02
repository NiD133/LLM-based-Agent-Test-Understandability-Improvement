package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testB650 {

    private static final String EXPECTED_SOUNDEX_CODE = "B650";

    private static final String[] NAMES_ENCODING_AS_B650 = {
            "BARHAM",
            "BARONE",
            "BARRON",
            "BERNA",
            "BIRNEY",
            "BIRNIE",
            "BOOROM",
            "BOREN",
            "BORN",
            "BOURN",
            "BOURNE",
            "BOWRON",
            "BRAIN",
            "BRAME",
            "BRANN",
            "BRAUN",
            "BREEN",
            "BRIEN",
            "BRIM",
            "BRIMM",
            "BRINN",
            "BRION",
            "BROOM",
            "BROOME",
            "BROWN",
            "BROWNE",
            "BRUEN",
            "BRUHN",
            "BRUIN",
            "BRUMM",
            "BRUN",
            "BRUNO",
            "BRYAN",
            "BURIAN",
            "BURN",
            "BURNEY",
            "BYRAM",
            "BYRNE",
            "BYRON",
            "BYRUM"
    };

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testB650() throws EncoderException {
        checkEncodingVariations(EXPECTED_SOUNDEX_CODE, NAMES_ENCODING_AS_B650);
    }

    private void checkEncodingVariations(final String expectedEncoding, final String... sourceNames) throws EncoderException {
        final Soundex soundex = createStringEncoder();
        for (final String sourceName : sourceNames) {
            assertEquals(expectedEncoding, soundex.encode(sourceName));
        }
    }
}
