/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Nysiis}.
 *
 * <p>Two encoder instances are used throughout these tests:
 * <ul>
 *   <li>{@code fullNysiis} — non-strict mode ({@code new Nysiis(false)}), produces
 *       NYSIIS keys of arbitrary length; used by {@link #assertEncodings}.</li>
 *   <li>{@link #getStringEncoder()} — strict mode ({@code new Nysiis()}), truncates
 *       encoded keys to a maximum of 6 characters; used by {@link #encodeAll}.</li>
 * </ul>
 */
class NysiisTest extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder that produces full-length (unbounded) NYSIIS keys. */
    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Asserts that each input/expected pair encodes correctly using the non-strict encoder.
     *
     * @param testValues varargs of two-element arrays; index 0 is the raw input name
     *                   and index 1 is the expected NYSIIS code.
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

    /**
     * Asserts that every name in {@code strings} encodes to {@code expectedEncoding}
     * using the strict encoder (6-character maximum).
     *
     * @param strings          the input names that should all share the same phonetic key.
     * @param expectedEncoding the expected NYSIIS code, at most 6 characters.
     */
    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    @Test
    void testBran() {
        encodeAll(new String[] { "Brian", "Brown", "Brun" }, "BRAN");
    }

    @Test
    void testCap() {
        encodeAll(new String[] { "Capp", "Cope", "Copp", "Kipp" }, "CAP");
    }

    @Test
    void testDad() {
        // Data Quality and Record Linkage Techniques P.121 claims this is DAN,
        // but it should be DAD, verified also with dropby.com
        encodeAll(new String[] { "Dent" }, "DAD");
    }

    @Test
    void testDan() {
        encodeAll(new String[] { "Dane", "Dean", "Dionne" }, "DAN");
    }

    /**
     * Tests data gathered from around the internet.
     *
     * <p>Where this implementation differs from the dropby.com reference, a comment
     * shows the reference output in brackets and explains the relevant rule that causes
     * the divergence. Rule numbers correspond to the algorithm steps in {@link Nysiis}.
     *
     * @see <a href="https://www.dropby.com/NYSIISTextStrings.html">http://www.dropby.com/NYSIISTextStrings.html</a>
     */
    @Test
    void testDropBy() {
        assertEncodings(
                // Rule 1: Transcode first characters of name
                new String[] { "MACINTOSH", "MCANT" },
                // Rule 4j: the doubled leading key character is collapsed (NN → N),
                // so the reference output "NNAT" becomes "NAT"
                new String[] { "KNUTH", "NAT" },           // Reference: NNAT
                // Rules 4a and 4h: O and E transcode to A; H transcodes to its previous char,
                // which causes the following N to be skipped; rule 7 then removes the trailing A
                new String[] { "KOEHN", "CAN" },           // Reference: C
                // Rule 4j causes leading doubled characters to collapse (FF → F)
                new String[] { "PHILLIPSON", "FALAPSAN" }, // Reference: FFALAP[SAN]
                new String[] { "PFEISTER", "FASTAR" },     // Reference: FFASTA[R]
                new String[] { "SCHOENHOEFT", "SANAFT" },  // Reference: SSANAF[T]

                // Rule 2: Transcode last characters of name
                new String[] { "MCKEE", "MCY" },
                new String[] { "MACKIE", "MCY" },
                new String[] { "HEITSCHMIDT", "HATSNAD" },
                new String[] { "BART", "BAD" },
                new String[] { "HURD", "HAD" },
                new String[] { "HUNT", "HAD" },
                new String[] { "WESTERLUND", "WASTARLAD" },

                // Rule 4: Transcode remaining characters
                new String[] { "CASSTEVENS", "CASTAFAN" },
                new String[] { "VASQUEZ", "VASG" },
                new String[] { "FRAZIER", "FRASAR" },
                new String[] { "BOWMAN", "BANAN" },
                new String[] { "MCKNIGHT", "MCNAGT" },
                new String[] { "RICKERT", "RACAD" },
                // Rule 5 variance: trailing S not removed (cf. DEUTS → DAT, same result)
                new String[] { "DEUTSCH", "DAT" },         // Reference: DATS
                new String[] { "WESTPHAL", "WASTFAL" },
                // Rule 4h: H transcodes to S, which is then collapsed as a duplicate first key char
                new String[] { "SHRIVER", "SRAVAR" },      // Reference: SHRAVA[R]
                // Same as KOEHN: L is lost via rule 4h lookahead behaviour
                new String[] { "KUHL", "CAL" },            // Reference: C
                new String[] { "RAWSON", "RASAN" },
                // Rule 5: trailing S removed
                new String[] { "JILES", "JAL" },
                // Rule 6: AY suffix replaced by Y
                new String[] { "CARRAWAY", "CARY" },       // Reference: CARAY
                new String[] { "YAMADA", "YANAD" });
    }

    @Test
    void testFal() {
        encodeAll(new String[] { "Phil" }, "FAL");
    }

    /**
     * Tests data gathered from around the internet.
     */
    @Test
    void testOthers() {
        assertEncodings(
                // Apostrophe-prefixed surnames collapse to the same phonetic key
                new String[] { "O'Daniel", "ODANAL" },
                new String[] { "O'Donnel", "ODANAL" },
                // Spelling variants that share the same phonetic key
                new String[] { "Cory", "CARY" },
                new String[] { "Corey", "CARY" },
                new String[] { "Kory", "CARY" },
                // Double-Z: Z → S (rule 4.2), then duplicate S collapsed (rule 8), trailing S removed (rule 5)
                new String[] { "FUZZY", "FASY" });
    }

    /**
     * Tests rule 1: Translate first characters of name: MAC → MCC, KN → N, K → C, PH/PF → FF, SCH → SSS.
     */
    @Test
    void testRule1() {
        assertEncodings(
                new String[] { "MACX", "MCX" },
                new String[] { "KNX", "NX" },
                new String[] { "KX", "CX" },
                new String[] { "PHX", "FX" },
                new String[] { "PFX", "FX" },
                new String[] { "SCHX", "SX" });
    }

    /**
     * Tests rule 2: Translate last characters of name: EE/IE → Y, DT/RT/RD/NT/ND → D.
     */
    @Test
    void testRule2() {
        assertEncodings(
                new String[] { "XEE", "XY" },
                new String[] { "XIE", "XY" },
                new String[] { "XDT", "XD" },
                new String[] { "XRT", "XD" },
                new String[] { "XRD", "XD" },
                new String[] { "XNT", "XD" },
                new String[] { "XND", "XD" });
    }

    /**
     * Tests rule 4.1: EV → AF, and all other vowels (A/E/I/O/U) → A.
     */
    @Test
    void testRule4Dot1() {
        assertEncodings(
                new String[] { "XEV", "XAF" },
                new String[] { "XAX", "XAX" },
                new String[] { "XEX", "XAX" },
                new String[] { "XIX", "XAX" },
                new String[] { "XOX", "XAX" },
                new String[] { "XUX", "XAX" });
    }

    /**
     * Tests rule 4.2: Q → G, Z → S, M → N.
     * Note: "XZ" encodes to "X" rather than "XS" because rule 5 subsequently removes
     * the trailing S produced by the Z → S transcoding.
     */
    @Test
    void testRule4Dot2() {
        assertEncodings(
                new String[] { "XQ", "XG" },
                new String[] { "XZ", "X" },   // Z → S (rule 4.2), trailing S removed (rule 5)
                new String[] { "XM", "XN" });
    }

    /**
     * Tests rule 5: If last character is S, remove it.
     */
    @Test
    void testRule5() {
        assertEncodings(
                new String[] { "XS", "X" },
                new String[] { "XSS", "X" });
    }

    /**
     * Tests rule 6: If last characters are AY, replace with Y.
     */
    @Test
    void testRule6() {
        assertEncodings(
                new String[] { "XAY", "XY" },
                new String[] { "XAYS", "XY" }); // Rules 5 then 6 applied in sequence
    }

    /**
     * Tests rule 7: If last character is A, remove it.
     */
    @Test
    void testRule7() {
        assertEncodings(
                new String[] { "XA", "X" },
                new String[] { "XAS", "X" }); // Rules 5 then 7 applied in sequence
    }

    @Test
    void testSnad() {
        // Data Quality and Record Linkage Techniques P.121 claims this is SNAT,
        // but it should be SNAD
        encodeAll(new String[] { "Schmidt" }, "SNAD");
    }

    @Test
    void testSnat() {
        encodeAll(new String[] { "Smith", "Schmit" }, "SNAT");
    }

    /**
     * Tests boundary conditions in the encoding loop: names that start with vowels,
     * very short names, and inputs that exercise less common transcoding branches.
     */
    @Test
    void testSpecialBranches() {
        encodeAll(new String[] { "Kobwick" }, "CABWAC");
        encodeAll(new String[] { "Kocher" }, "CACAR");
        encodeAll(new String[] { "Fesca" }, "FASC");
        encodeAll(new String[] { "Shom" }, "SAN");
        encodeAll(new String[] { "Ohlo" }, "OL");
        encodeAll(new String[] { "Uhu" }, "UH");
        encodeAll(new String[] { "Um" }, "UN");
    }

    @Test
    void testTranan() {
        encodeAll(new String[] { "Trueman", "Truman" }, "TRANAN");
    }

    /**
     * Tests strict mode: the encoded string is truncated to a maximum of 6 characters.
     * "WESTERLUND" produces "WASTARLAD" in non-strict mode and "WASTAR" in strict mode.
     */
    @Test
    void testTrueVariant() {
        final Nysiis encoder = new Nysiis(true);

        final String encoded = encoder.encode("WESTERLUND");
        assertTrue(encoded.length() <= 6);
        assertEquals("WASTAR", encoded);
    }

}
