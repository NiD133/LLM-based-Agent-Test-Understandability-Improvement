package org.apache.commons.codec.language;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a family of similar-sounding surnames all encode to the same
 * Soundex code, {@code "B650"}.
 *
 * <p>A Soundex code keeps the first letter of the word and then appends up to
 * three digits derived from the following consonants. For every name below the
 * algorithm produces:</p>
 * <ul>
 *   <li>{@code B} &ndash; the retained first letter,</li>
 *   <li>{@code 6} &ndash; the code for {@code R},</li>
 *   <li>{@code 5} &ndash; the code for {@code N} or {@code M},</li>
 *   <li>{@code 0} &ndash; right-padding because no further consonant follows.</li>
 * </ul>
 */
public class SoundexTest_testB650 extends AbstractStringEncoderTest<Soundex> {

    /** The single Soundex code that every name variation under test must produce. */
    private static final String EXPECTED_SOUNDEX_CODE = "B650";

    /**
     * Surname spellings that should all collapse to {@link #EXPECTED_SOUNDEX_CODE}.
     * Grouped only for readability; the order does not affect the assertion.
     */
    private static final String[] NAMES_ENCODING_TO_B650 = {
        "BARHAM", "BARONE", "BARRON", "BERNA", "BIRNEY", "BIRNIE", "BOOROM",
        "BOREN", "BORN", "BOURN", "BOURNE", "BOWRON", "BRAIN", "BRAME",
        "BRANN", "BRAUN", "BREEN", "BRIEN", "BRIM", "BRIMM", "BRINN",
        "BRION", "BROOM", "BROOME", "BROWN", "BROWNE", "BRUEN", "BRUHN",
        "BRUIN", "BRUMM", "BRUN", "BRUNO", "BRYAN", "BURIAN", "BURN",
        "BURNEY", "BYRAM", "BYRNE", "BYRON", "BYRUM"
    };

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testB650() throws EncoderException {
        checkEncodingVariations(EXPECTED_SOUNDEX_CODE, NAMES_ENCODING_TO_B650);
    }
}
