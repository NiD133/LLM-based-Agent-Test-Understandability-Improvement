package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testDropBy extends AbstractStringEncoderTest<Nysiis> {

    /**
     * NYSIIS encoder in non-strict mode, so that the full (unbounded length)
     * encoding is produced rather than being truncated to 6 characters.
     */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that encoding {@code name} with the full (non-strict) NYSIIS
     * encoder yields {@code expectedEncoding}.
     *
     * @param name             the input name to encode.
     * @param expectedEncoding the expected NYSIIS encoding.
     */
    private void assertEncoding(final String name, final String expectedEncoding) {
        assertEquals(expectedEncoding, this.fullNysiis.encode(name), "Problem with " + name);
    }

    /**
     * Tests data gathered from around the internet.
     *
     * <p>Each case below documents how this implementation differs from the reference
     * implementation at dropby.com. Rule numbers refer to the algorithm steps outlined
     * in the {@link Nysiis} class description.</p>
     *
     * @see <a href="https://www.dropby.com/NYSIISTextStrings.html">http://www.dropby.com/NYSIISTextStrings.html</a>
     */
    @Test
    void testDropBy() {
        // 1. Transcode first characters of name.
        assertEncoding("MACINTOSH", "MCANT");

        // Violates rule 4j: the second N should not be added, as the first key char is
        // already an N. Original: NNAT; modified: NATH.
        assertEncoding("KNUTH", "NAT");

        // O and E are transcoded to A because of rule 4a; H also to A because of rule 4h.
        // The N gets lost, maybe because of a wrongly implemented rule 4h that skips the
        // next char in such a case. The remaining A is removed because of rule 7.
        // Original: C.
        assertEncoding("KOEHN", "CAN");

        // Violates rule 4j (see also KNUTH). Original: FFALAP[SAN].
        assertEncoding("PHILLIPSON", "FALAPSAN");

        // Violates rule 4j (see also KNUTH). Original: FFASTA[R].
        assertEncoding("PFEISTER", "FASTAR");

        // Violates rule 4j (see also KNUTH). Original: SSANAF[T].
        assertEncoding("SCHOENHOEFT", "SANAFT");

        // 2. Transcode last characters of name.
        assertEncoding("MCKEE", "MCY");
        assertEncoding("MACKIE", "MCY");
        assertEncoding("HEITSCHMIDT", "HATSNAD");
        assertEncoding("BART", "BAD");
        assertEncoding("HURD", "HAD");
        assertEncoding("HUNT", "HAD");
        assertEncoding("WESTERLUND", "WASTARLAD");

        // 4. Transcode remaining characters, incrementing by one character each time.
        assertEncoding("CASSTEVENS", "CASTAFAN");
        assertEncoding("VASQUEZ", "VASG");
        assertEncoding("FRAZIER", "FRASAR");
        assertEncoding("BOWMAN", "BANAN");
        assertEncoding("MCKNIGHT", "MCNAGT");
        assertEncoding("RICKERT", "RACAD");

        // Violates rule 5: the last S is not removed when comparing to DEUTS, which is
        // phonetically similar. The result DAT is correct for DEUTSCH too. Original: DATS.
        assertEncoding("DEUTSCH", "DAT");

        assertEncoding("WESTPHAL", "WASTFAL");

        // Violates rule 4h: the H should be transcoded to S and thus ignored as the first
        // key character is also S. Original: SHRAVA[R].
        assertEncoding("SHRIVER", "SRAVAR");

        // Same as KOEHN, the L gets lost. Original: C.
        assertEncoding("KUHL", "CAL");

        assertEncoding("RAWSON", "RASAN");

        // If last character is S, remove it.
        assertEncoding("JILES", "JAL");

        // Violates rule 6: if the last two characters are AY, remove A. Original: CARAY.
        assertEncoding("CARRAWAY", "CARY");

        assertEncoding("YAMADA", "YANAD");
    }
}
