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

// (FYI: Formatted and sorted with Eclipse)

package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Soundex}.
 *
 * <p>Keep this file in UTF-8 encoding for proper Javadoc processing.</p>
 */
class SoundexTest extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * All of these surnames are known to share the Soundex code "B650", so encoding any of them
     * must yield that single code.
     */
    @Test
    void testB650() throws EncoderException {
        // @formatter:off
        checkEncodingVariations("B650",
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
            "BYRUM");
        // @formatter:on
    }

    /**
     * Unmapped punctuation embedded in a word is simply skipped rather than rejected.
     */
    @Test
    void testBadCharacters() {
        final Soundex soundex = getStringEncoder();

        assertEquals("H452", soundex.encode("HOL>MES"));
    }

    /**
     * {@link Soundex#difference(String, String)} returns how many of the four encoded characters
     * match (0 = no similarity, 4 = identical encoding).
     */
    @Test
    void testDifference() throws EncoderException {
        final Soundex soundex = getStringEncoder();

        // Edge cases: null, empty and blank inputs all encode the same, so the difference is 0.
        assertEquals(0, soundex.difference(null, null));
        assertEquals(0, soundex.difference("", ""));
        assertEquals(0, soundex.difference(" ", " "));

        // Normal cases.
        assertEquals(4, soundex.difference("Smith", "Smythe"));
        assertEquals(2, soundex.difference("Ann", "Andrew"));
        assertEquals(1, soundex.difference("Margaret", "Andrew"));
        assertEquals(0, soundex.difference("Janet", "Margaret"));

        // Examples from https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_de-dz_8co5.asp
        assertEquals(4, soundex.difference("Green", "Greene"));
        assertEquals(0, soundex.difference("Blotchet-Halls", "Greene"));

        // Examples from https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
        assertEquals(4, soundex.difference("Smith", "Smythe"));
        assertEquals(4, soundex.difference("Smithers", "Smythers"));
        assertEquals(2, soundex.difference("Anothers", "Brothers"));
    }

    /**
     * Basic word-to-code encodings using the default US English mapping.
     */
    @Test
    void testEncodeBasic() {
        final Soundex soundex = getStringEncoder();

        assertEquals("T235", soundex.encode("testing"));
        assertEquals("T000", soundex.encode("The"));
        assertEquals("Q200", soundex.encode("quick"));
        assertEquals("B650", soundex.encode("brown"));
        assertEquals("F200", soundex.encode("fox"));
        assertEquals("J513", soundex.encode("jumped"));
        assertEquals("O160", soundex.encode("over"));
        assertEquals("T000", soundex.encode("the"));
        assertEquals("L200", soundex.encode("lazy"));
        assertEquals("D200", soundex.encode("dogs"));
    }

    /**
     * Examples from http://www.bradandkathy.com/genealogy/overviewofsoundex.html
     */
    @Test
    void testEncodeBatch2() {
        final Soundex soundex = getStringEncoder();

        assertEquals("A462", soundex.encode("Allricht"));
        assertEquals("E166", soundex.encode("Eberhard"));
        assertEquals("E521", soundex.encode("Engebrethson"));
        assertEquals("H512", soundex.encode("Heimbach"));
        assertEquals("H524", soundex.encode("Hanselmann"));
        assertEquals("H431", soundex.encode("Hildebrand"));
        assertEquals("K152", soundex.encode("Kavanagh"));
        assertEquals("L530", soundex.encode("Lind"));
        assertEquals("L222", soundex.encode("Lukaschowsky"));
        assertEquals("M235", soundex.encode("McDonnell"));
        assertEquals("M200", soundex.encode("McGee"));
        assertEquals("O155", soundex.encode("Opnian"));
        assertEquals("O155", soundex.encode("Oppenheimer"));
        assertEquals("R355", soundex.encode("Riedemanas"));
        assertEquals("Z300", soundex.encode("Zita"));
        assertEquals("Z325", soundex.encode("Zitzmeinn"));
    }

    /**
     * Examples from http://www.archives.gov/research_room/genealogy/census/soundex.html
     */
    @Test
    void testEncodeBatch3() {
        final Soundex soundex = getStringEncoder();

        assertEquals("W252", soundex.encode("Washington"));
        assertEquals("L000", soundex.encode("Lee"));
        assertEquals("G362", soundex.encode("Gutierrez"));
        assertEquals("P236", soundex.encode("Pfister"));
        assertEquals("J250", soundex.encode("Jackson"));
        assertEquals("T522", soundex.encode("Tymczak"));
        // For VanDeusen: D-250 (D, 2 for the S, 5 for the N, 0 added) is also
        // possible.
        assertEquals("V532", soundex.encode("VanDeusen"));
    }

    /**
     * Examples from: http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testEncodeBatch4() {
        final Soundex soundex = getStringEncoder();

        assertEquals("H452", soundex.encode("HOLMES"));
        assertEquals("A355", soundex.encode("ADOMOMI"));
        assertEquals("V536", soundex.encode("VONDERLEHR"));
        assertEquals("B400", soundex.encode("BALL"));
        assertEquals("S000", soundex.encode("SHAW"));
        assertEquals("J250", soundex.encode("JACKSON"));
        assertEquals("S545", soundex.encode("SCANLON"));
        assertEquals("S532", soundex.encode("SAINTJOHN"));
    }

    /**
     * Apostrophes are ignored, so the same name encodes to "O165" regardless of where an
     * apostrophe is inserted.
     */
    @Test
    void testEncodeIgnoreApostrophes() throws EncoderException {
        // @formatter:off
        checkEncodingVariations("O165",
            "OBrien",
            "'OBrien",
            "O'Brien",
            "OB'rien",
            "OBr'ien",
            "OBri'en",
            "OBrie'n",
            "OBrien'");
        // @formatter:on
    }

    /**
     * Hyphens are ignored, so the same name encodes to "K525" regardless of where a hyphen is
     * inserted.
     *
     * <p>Test data from http://www.myatt.demon.co.uk/sxalg.htm</p>
     *
     * @throws EncoderException for some failure scenarios     */
    @Test
    void testEncodeIgnoreHyphens() throws EncoderException {
        // @formatter:off
        checkEncodingVariations("K525",
            "KINGSMITH",
            "-KINGSMITH",
            "K-INGSMITH",
            "KI-NGSMITH",
            "KIN-GSMITH",
            "KING-SMITH",
            "KINGS-MITH",
            "KINGSM-ITH",
            "KINGSMI-TH",
            "KINGSMIT-H",
            "KINGSMITH-");
        // @formatter:on
    }

    /**
     * Surrounding whitespace (spaces, tabs, newlines, carriage returns) is trimmed before encoding.
     */
    @Test
    void testEncodeIgnoreTrimmable() {
        final Soundex soundex = getStringEncoder();

        assertEquals("W252", soundex.encode(" \t\n\r Washington \t\n\r "));
    }

    /**
     * Genealogy mapping treats vowels, H and W as silent letters.
     *
     * <p>Examples and algorithm rules from: http://www.genealogy.com/articles/research/00000060.html</p>
     */
    @Test
    void testGenealogy() {
        final Soundex soundex = Soundex.US_ENGLISH_GENEALOGY;

        assertEquals("H251", soundex.encode("Heggenburger"));
        assertEquals("B425", soundex.encode("Blackman"));
        assertEquals("S530", soundex.encode("Schmidt"));
        assertEquals("L150", soundex.encode("Lippmann"));
        // Additional local examples: a silent letter does not separate equal consonant codes.
        assertEquals("D200", soundex.encode("Dodds")); // 'o' is not a separator here - it is silent
        assertEquals("D200", soundex.encode("Dhdds")); // 'h' is silent
        assertEquals("D200", soundex.encode("Dwdds")); // 'w' is silent
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     */
    @Test
    void testHWRuleEx1() {
        final Soundex soundex = getStringEncoder();

        // From http://www.archives.gov/research_room/genealogy/census/soundex.html:
        // Ashcraft is coded A-261 (A, 2 for the S, C ignored, 6 for the R, 1
        // for the F). It is not coded A-226.
        assertEquals("A261", soundex.encode("Ashcraft"));
        assertEquals("A261", soundex.encode("Ashcroft"));
        assertEquals("Y330", soundex.encode("yehudit"));
        assertEquals("Y330", soundex.encode("yhwdyt"));
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * <p>Test data from http://www.myatt.demon.co.uk/sxalg.htm</p>
     */
    @Test
    void testHWRuleEx2() {
        final Soundex soundex = getStringEncoder();

        assertEquals("B312", soundex.encode("BOOTHDAVIS"));
        assertEquals("B312", soundex.encode("BOOTH-DAVIS"));
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * <p>Test data from http://www.myatt.demon.co.uk/sxalg.htm</p>
     *
     * @throws EncoderException for some failure scenarios     */
    @Test
    void testHWRuleEx3() throws EncoderException {
        final Soundex soundex = getStringEncoder();

        assertEquals("S460", soundex.encode("Sgler"));
        assertEquals("S460", soundex.encode("Swhgler"));
        // The following names all share the code "S460" as well.
        // @formatter:off
        checkEncodingVariations("S460",
            "SAILOR",
            "SALYER",
            "SAYLOR",
            "SCHALLER",
            "SCHELLER",
            "SCHILLER",
            "SCHOOLER",
            "SCHULER",
            "SCHUYLER",
            "SEILER",
            "SEYLER",
            "SHOLAR",
            "SHULER",
            "SILAR",
            "SILER",
            "SILLER");
        // @formatter:on
    }

    /**
     * Examples for MS SQLServer from
     * https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
     */
    @Test
    void testMsSqlServer1() {
        final Soundex soundex = getStringEncoder();

        assertEquals("S530", soundex.encode("Smith"));
        assertEquals("S530", soundex.encode("Smythe"));
    }

    /**
     * Examples for MS SQLServer from
     * https://support.microsoft.com/default.aspx?scid=https://support.microsoft.com:80/support
     * /kb/articles/Q100/3/65.asp&NoWebContent=1
     *
     * @throws EncoderException for some failure scenarios     */
    @Test
    void testMsSqlServer2() throws EncoderException {
        checkEncodingVariations("E625", "Erickson", "Erickson", "Erikson", "Ericson", "Ericksen", "Ericsen");
    }

    /**
     * Examples for MS SQLServer from https://databases.about.com/library/weekly/aa042901a.htm
     */
    @Test
    void testMsSqlServer3() {
        final Soundex soundex = getStringEncoder();

        assertEquals("A500", soundex.encode("Ann"));
        assertEquals("A536", soundex.encode("Andrew"));
        assertEquals("J530", soundex.encode("Janet"));
        assertEquals("M626", soundex.encode("Margaret"));
        assertEquals("S315", soundex.encode("Steven"));
        assertEquals("M240", soundex.encode("Michael"));
        assertEquals("R163", soundex.encode("Robert"));
        assertEquals("L600", soundex.encode("Laura"));
        assertEquals("A500", soundex.encode("Anne"));
    }

    /**
     * A freshly constructed default instance encodes the same way as the shared static instance.
     *
     * <p>https://issues.apache.org/jira/browse/CODEC-54 https://issues.apache.org/jira/browse/CODEC-56</p>
     */
    @Test
    void testNewInstance() {
        assertEquals("W452", new Soundex().soundex("Williams"));
    }

    /**
     * Constructing with the default mapping as a {@code char[]} behaves like the default instance.
     */
    @Test
    void testNewInstance2() {
        assertEquals("W452", new Soundex(Soundex.US_ENGLISH_MAPPING_STRING.toCharArray()).soundex("Williams"));
    }

    /**
     * Constructing with the default mapping as a {@code String} behaves like the default instance.
     */
    @Test
    void testNewInstance3() {
        assertEquals("W452", new Soundex(Soundex.US_ENGLISH_MAPPING_STRING).soundex("Williams"));
    }

    /**
     * Simplified mapping treats vowels, H and W as separators (rather than silent letters).
     *
     * <p>Examples and algorithm rules from: http://west-penwith.org.uk/misc/soundex.htm</p>
     */
    @Test
    void testSimplifiedSoundex() {
        final Soundex soundex = Soundex.US_ENGLISH_SIMPLIFIED;

        assertEquals("W452", soundex.encode("WILLIAMS"));
        assertEquals("B625", soundex.encode("BARAGWANATH"));
        assertEquals("D540", soundex.encode("DONNELL"));
        assertEquals("L300", soundex.encode("LLOYD"));
        assertEquals("W422", soundex.encode("WOOLCOCK"));
        // Additional local examples: a separator breaks up otherwise-equal consonant codes.
        assertEquals("D320", soundex.encode("Dodds"));
        assertEquals("D320", soundex.encode("Dwdds")); // w is a separator
        assertEquals("D320", soundex.encode("Dhdds")); // h is a separator
    }

    @Test
    void testSoundexUtilsConstructable() {
        new SoundexUtils();
    }

    @Test
    void testSoundexUtilsNullBehaviour() {
        assertNull(SoundexUtils.clean(null));
        assertEquals("", SoundexUtils.clean(""));
        assertEquals(0, SoundexUtils.differenceEncoded(null, ""));
        assertEquals(0, SoundexUtils.differenceEncoded("", null));
    }

    /**
     * The shared static US English instance encodes the same as a fresh default instance.
     *
     * <p>https://issues.apache.org/jira/browse/CODEC-54 https://issues.apache.org/jira/browse/CODEC-56</p>
     */
    @Test
    void testUsEnglishStatic() {
        assertEquals("W452", Soundex.US_ENGLISH.soundex("Williams"));
    }

    /**
     * Fancy characters are not mapped by the default US mapping.
     *
     * <p>https://issues.apache.org/jira/browse/CODEC-30</p>
     */
    @Test
    void testUsMappingEWithAcute() {
        final Soundex soundex = getStringEncoder();

        assertEquals("E000", soundex.encode("e"));
        if (Character.isLetter('é')) { // e-acute
            // An out-of-range letter is rejected because the default mapping only covers A-Z.
            assertThrows(IllegalArgumentException.class, () -> soundex.encode("é"));
        } else {
            // Where it is not a letter, it is cleaned away and the result is empty.
            assertEquals("", soundex.encode("é"));
        }
    }

    /**
     * Fancy characters are not mapped by the default US mapping.
     *
     * <p>https://issues.apache.org/jira/browse/CODEC-30</p>
     */
    @Test
    void testUsMappingOWithDiaeresis() {
        final Soundex soundex = getStringEncoder();

        assertEquals("O000", soundex.encode("o"));
        if (Character.isLetter('ö')) { // o-umlaut
            // An out-of-range letter is rejected because the default mapping only covers A-Z.
            assertThrows(IllegalArgumentException.class, () -> soundex.encode("ö"));
        } else {
            // Where it is not a letter, it is cleaned away and the result is empty.
            assertEquals("", soundex.encode("ö"));
        }
    }

    /**
     * Tests example from https://en.wikipedia.org/wiki/Soundex#American_Soundex as of 2015-03-22.
     */
    @Test
    void testWikipediaAmericanSoundex() {
        final Soundex soundex = getStringEncoder();

        assertEquals("R163", soundex.encode("Robert"));
        assertEquals("R163", soundex.encode("Rupert"));
        assertEquals("A261", soundex.encode("Ashcraft"));
        assertEquals("A261", soundex.encode("Ashcroft"));
        assertEquals("T522", soundex.encode("Tymczak"));
        assertEquals("P236", soundex.encode("Pfister"));
    }
}
