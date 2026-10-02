package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testDropBy {

    private final Nysiis fullNysiis = new Nysiis(false);

    private static String[] encoding(final String input, final String expected) {
        return new String[] { input, expected };
    }

    private void assertEncodings(final String[]... testValues) {
        for (final String[] testValue : testValues) {
            assertEquals(testValue[1], this.fullNysiis.encode(testValue[0]), "Problem with " + testValue[0]);
        }
    }

    /**
     * Tests data gathered from around the internet.
     *
     * @see <a href="https://www.dropby.com/NYSIISTextStrings.html">http://www.dropby.com/NYSIISTextStrings.html</a>
     */
    @Test
    void testDropBy() {
        assertFirstCharacterTranscodingExamples();
        assertLastCharacterTranscodingExamples();
        assertRemainingCharacterTranscodingExamples();
        assertTrailingCharacterCleanupExamples();
    }

    private void assertFirstCharacterTranscodingExamples() {
        assertEncodings(
                encoding("MACINTOSH", "MCANT"),
                // DropBy original: NNAT. Rule 4j should not add a second N when the last key character is already N.
                encoding("KNUTH", "NAT"),
                // DropBy original: C. O, E, and H are transcoded to A, and the remaining A is removed by rule 7.
                encoding("KOEHN", "CAN"),
                // DropBy original: FFALAP[SAN]. See the KNUTH note for the rule 4j difference.
                encoding("PHILLIPSON", "FALAPSAN"),
                // DropBy original: FFASTA[R]. See the KNUTH note for the rule 4j difference.
                encoding("PFEISTER", "FASTAR"),
                // DropBy original: SSANAF[T]. See the KNUTH note for the rule 4j difference.
                encoding("SCHOENHOEFT", "SANAFT"));
    }

    private void assertLastCharacterTranscodingExamples() {
        assertEncodings(
                encoding("MCKEE", "MCY"),
                encoding("MACKIE", "MCY"),
                encoding("HEITSCHMIDT", "HATSNAD"),
                encoding("BART", "BAD"),
                encoding("HURD", "HAD"),
                encoding("HUNT", "HAD"),
                encoding("WESTERLUND", "WASTARLAD"));
    }

    private void assertRemainingCharacterTranscodingExamples() {
        assertEncodings(
                encoding("CASSTEVENS", "CASTAFAN"),
                encoding("VASQUEZ", "VASG"),
                encoding("FRAZIER", "FRASAR"),
                encoding("BOWMAN", "BANAN"),
                encoding("MCKNIGHT", "MCNAGT"),
                encoding("RICKERT", "RACAD"),
                // DropBy original: DATS. Rule 5 removes the final S.
                encoding("DEUTSCH", "DAT"),
                encoding("WESTPHAL", "WASTFAL"),
                // DropBy original: SHRAVA[R]. Rule 4h transcodes H to S, matching the first key character.
                encoding("SHRIVER", "SRAVAR"),
                // DropBy original: C. This follows the same loss-of-intermediate-letter case as KOEHN.
                encoding("KUHL", "CAL"),
                encoding("RAWSON", "RASAN"));
    }

    private void assertTrailingCharacterCleanupExamples() {
        assertEncodings(
                encoding("JILES", "JAL"),
                // DropBy original: CARAY. Rule 6 replaces a trailing AY with Y.
                encoding("CARRAWAY", "CARY"),
                encoding("YAMADA", "YANAD"));
    }
}
