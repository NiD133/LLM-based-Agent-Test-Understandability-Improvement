package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testDropBy extends AbstractStringEncoderTest<Nysiis> {

    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Takes an array of String pairs where each pair's first element is the input and the second element the expected
     * encoding.
     *
     * @param testValues
     *            an array of String pairs where each pair's first element is the input and the second element the
     *            expected encoding.
     */
    private void assertEncodings(final String[]... testValues) {
        for (final String[] arr : testValues) {
            assertEquals(arr[1], this.fullNysiis.encode(arr[0]), "Problem with " + arr[0]);
        }
    }

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Tests data gathered from around the internet.
     *
     * @see <a href="https://www.dropby.com/NYSIISTextStrings.html">http://www.dropby.com/NYSIISTextStrings.html</a>
     */
    @Test
    void testDropBy() {
        // Explanation of differences between this implementation and the one at dropby.com is
        // prepended to the test string. The referenced rules refer to the outlined steps the
        // class description for Nysiis.
        assertEncodings(// 1. Transcode first characters of name
        new String[] { "MACINTOSH", "MCANT" }, // violates 4j: the second N should not be added, as the first
        //              key char is already a N
        // Original: NNAT; modified: NATH
        new String[] { "KNUTH", "NAT" }, // O and E are transcoded to A because of rule 4a
        // H also to A because of rule 4h
        // the N gets mysteriously lost, maybe because of a wrongly implemented rule 4h
        // that skips the next char in such a case?
        // the remaining A is removed because of rule 7
        // Original: C
        new String[] { "KOEHN", "CAN" }, // violates 4j: see also KNUTH
        // Original: FFALAP[SAN]
        new String[] { "PHILLIPSON", "FALAPSAN" }, // violates 4j: see also KNUTH
        // Original: FFASTA[R]
        new String[] { "PFEISTER", "FASTAR" }, // violates 4j: see also KNUTH
        // Original: SSANAF[T]
        new String[] { "SCHOENHOEFT", "SANAFT" }, // 2. Transcode last characters of name:
        new String[] { "MCKEE", "MCY" }, new String[] { "MACKIE", "MCY" }, new String[] { "HEITSCHMIDT", "HATSNAD" }, new String[] { "BART", "BAD" }, new String[] { "HURD", "HAD" }, new String[] { "HUNT", "HAD" }, new String[] { "WESTERLUND", "WASTARLAD" }, // 4. Transcode remaining characters by following these rules,
        //    incrementing by one character each time:
        new String[] { "CASSTEVENS", "CASTAFAN" }, new String[] { "VASQUEZ", "VASG" }, new String[] { "FRAZIER", "FRASAR" }, new String[] { "BOWMAN", "BANAN" }, new String[] { "MCKNIGHT", "MCNAGT" }, new String[] { "RICKERT", "RACAD" }, // violates 5: the last S is not removed
        // when comparing to DEUTS, which is phonetically similar
        // the result it also DAT, which is correct for DEUTSCH too imo
        // Original: DATS
        new String[] { "DEUTSCH", "DAT" }, new String[] { "WESTPHAL", "WASTFAL" }, // violates 4h: the H should be transcoded to S and thus ignored as
        // the first key character is also S
        // Original: SHRAVA[R]
        new String[] { "SHRIVER", "SRAVAR" }, // same as KOEHN, the L gets mysteriously lost
        // Original: C
        new String[] { "KUHL", "CAL" }, new String[] { "RAWSON", "RASAN" }, // If last character is S, remove it
        new String[] { "JILES", "JAL" }, // violates 6: if the last two characters are AY, remove A
        // Original: CARAY
        new String[] { "CARRAWAY", "CARY" }, new String[] { "YAMADA", "YANAD" });
    }
}
