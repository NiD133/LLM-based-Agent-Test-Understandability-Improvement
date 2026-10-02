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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Match Rating Approach phonetic algorithm.
 */
class MatchRatingApproachEncoderTest {

    private final MatchRatingApproachEncoder stringEncoder = new MatchRatingApproachEncoder();

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testRemoveAccentsPreservesCaseAndNonAccentCharacters() {
        assertAccentRemoval("áéíóú", "aeiou");
        assertAccentRemoval("È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç",
                "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c");
        assertAccentRemoval("äëöüßÄËÖÜñÑà", "aeoußAEOUnNa");
        assertAccentRemoval("Á-e'í.,ó&ú", "A-e'i.,o&u");
        assertAccentRemoval("ÁeíÓuu", "AeiOuu");
        assertAccentRemoval("áé íó  ú", "ae io  u");
        assertAccentRemoval("Colorless green ideas sleep furiously", "Colorless green ideas sleep furiously");
        assertAccentRemoval("", "");
        assertNull(getStringEncoder().removeAccents(null));
    }

    @Test
    final void testCleanNameRemovesPunctuationWhitespaceAndAccents() {
        assertEquals("THISISATEST", getStringEncoder().cleanName("This-ís   a t.,es &t"));
    }

    @Test
    final void testKnownMatchingNamePairs() {
        assertNamesMatch("Brian", "Bryan");
        assertNamesMatch("Burns", "Bourne");
        assertNamesMatch("Catherine", "Kathryn");
        assertNamesMatch("Cólm.   ", "C-olín");
        assertNamesMatch("Sean", "John");
        assertNamesMatch("Franciszek", "Frances");
        assertNamesMatch("o'muireadhaigh", "Ó 'Muircheartaigh ");
        assertNamesMatch("McGowan", "Mc Geoghegan");
        assertNamesMatch("Micky", "Michael");
        assertNamesMatch("Oona", "Oonagh");
        assertNamesMatch("Peterson", "Peters");
        assertNamesMatch("Sam", "Samuel");
        assertNamesMatch("Séan", "Shaun");
        assertNamesMatch("smith", "smyth");
        assertNamesMatch("Sophie", "Sofia");
        assertNamesMatch("Stephen", "Stefan");
        assertNamesMatch("Stephen", "Steven");
        assertNamesMatch("Steven", "Stefan");
        assertNamesMatch("Auerbach", "Uhrbach");
        assertNamesMatch("Cooper-Flynn", "Super-Lyn");
        assertNamesMatch("Hailey", "Halley");
        assertNamesMatch("LEWINSKY", "LEVINSKI");
        assertNamesMatch("LIPSHITZ", "LIPPSZYC");
        assertNamesMatch("Moskowitz", "Moskovitz");
        assertNamesMatch("O'Sullivan", "Ó ' Súilleabháin");
        assertNamesMatch(" P rz e m y s l", " P sh e m e sh i l");
        assertNamesMatch("R o s o ch o w a c ie c", " R o s o k ho v a ts e ts");
        assertNamesMatch("SZLAMAWICZ", "SHLAMOVITZ");
        assertNamesMatch("Tomasz", "tom");
        assertNamesMatch("Zach", "Zacharia");
        assertNamesMatch("John", "John");
        assertNamesMatch("Kl", "Karl");
    }

    @Test
    final void testKnownNonMatchingNamePairs() {
        assertNamesDoNotMatch("Sean", "Pete");
        assertNamesDoNotMatch("Úna", "Oonagh");
        assertNamesDoNotMatch("Karl", "Alessandro");
        assertNamesDoNotMatch("Moriarty", "OMuircheartaigh");
        assertNamesDoNotMatch("Al", "Ed");
        assertNamesDoNotMatch(null, null);
        assertNamesDoNotMatch("Murphy", "Lynch");
        assertNamesDoNotMatch("Murphy", "");
        assertNamesDoNotMatch("Murphy", " ");
        assertNamesDoNotMatch(null, " ");
        assertNamesDoNotMatch("Karl", "C");
    }

    @Test
    final void testWhitespaceAroundMatchingNamesIsIgnored() {
        assertNamesMatch("Brian", "Bryan");
        assertNamesMatch(" Brian", "Bryan");
        assertNamesMatch("Brian ", "Bryan");
        assertNamesMatch(" Brian ", "Bryan");
        assertNamesMatch("Brian", " Bryan");
        assertNamesMatch("Brian", "Bryan ");
        assertNamesMatch("Brian", " Bryan ");
    }

    @Test
    final void testInvalidInputsNeverMatch() {
        assertNamesDoNotMatch("t", "test");
        assertNamesDoNotMatch(" ", "test");
        assertNamesDoNotMatch("", "test");
        assertNamesDoNotMatch(null, "test");
        assertNamesDoNotMatch("test", " ");
        assertNamesDoNotMatch("test", "");
        assertNamesDoNotMatch("test", null);
        assertNamesDoNotMatch("test", "t");
    }

    @Test
    final void testEncodeReturnsExpectedMatchRatingCodes() {
        assertEncoding("HARPER", "HRPR");
        assertEncoding("", "");
        assertEncoding(null, "");
        assertEncoding("E", "");
        assertEncoding("Smith", "SMTH");
        assertEncoding("Smyth", "SMYTH");
        assertEncoding(" ", "");
        assertEncoding(".,-", "");
        assertEncoding("uoiea.,-AEIOU", "U");
        assertEncoding("aeiouAEIOU", "A");
    }

    @Test
    final void testGetFirst3Last3ShortensOnlyLongNames() {
        assertEquals("Aleder", getStringEncoder().getFirst3Last3("Alexzander"));
        assertEquals("PETE", getStringEncoder().getFirst3Last3("PETE"));
    }

    @Test
    final void testGetMinRatingUsesDocumentedLengthThresholds() {
        assertMinRating(1, 5);
        assertMinRating(2, 5);
        assertMinRating(5, 4);
        assertMinRating(5, 4);
        assertMinRating(6, 4);
        assertMinRating(7, 4);
        assertMinRating(7, 4);
        assertMinRating(8, 3);
        assertMinRating(10, 3);
        assertMinRating(11, 3);
        assertMinRating(13, 1);
    }

    @Test
    final void testIsVowelRecognizesVowelsCaseInsensitively() {
        assertTrue(getStringEncoder().isVowel("A"));
        assertTrue(getStringEncoder().isVowel("I"));
        assertFalse(getStringEncoder().isVowel("d"));
    }

    @Test
    final void testLeftToRightThenRightToLeftProcessingReturnsSimilarityRating() {
        assertEquals(4, getStringEncoder().leftToRightThenRightToLeftProcessing("ALEXANDER", "ALEXANDRA"));
        assertEquals(0, getStringEncoder().leftToRightThenRightToLeftProcessing("EINSTEIN", "MICHAELA"));
    }

    @Test
    final void testRemoveDoubleConsonantsCollapsesOnlyRepeatedConsonants() {
        assertEquals("MISISIPI", getStringEncoder().removeDoubleConsonants("MISSISSIPPI"));
        assertEquals("BEETLE", getStringEncoder().removeDoubleConsonants("BEETLE"));
        assertEquals("BUBLE", getStringEncoder().removeDoubleConsonants("BUBBLE"));
    }

    @Test
    final void testRemoveVowelsKeepsLeadingVowelOnly() {
        assertEquals("ADN", getStringEncoder().removeVowels("AIDAN"));
        assertEquals("DCLN", getStringEncoder().removeVowels("DECLAN"));
        assertEquals("ALSSNDR", getStringEncoder().removeVowels("ALESSANDRA"));
    }

    private void assertAccentRemoval(final String input, final String expected) {
        assertEquals(expected, getStringEncoder().removeAccents(input));
    }

    private void assertEncoding(final String input, final String expected) {
        assertEquals(expected, getStringEncoder().encode(input));
    }

    private void assertMinRating(final int sumLength, final int expected) {
        assertEquals(expected, getStringEncoder().getMinRating(sumLength));
    }

    private void assertNamesDoNotMatch(final String left, final String right) {
        assertFalse(getStringEncoder().isEncodeEquals(left, right));
    }

    private void assertNamesMatch(final String left, final String right) {
        assertTrue(getStringEncoder().isEncodeEquals(left, right));
    }
}
