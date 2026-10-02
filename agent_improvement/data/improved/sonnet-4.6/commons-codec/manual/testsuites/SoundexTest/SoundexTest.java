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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Soundex}.
 *
 * <p>Tests are grouped thematically:</p>
 * <ol>
 *   <li>Encoding correctness — common words and reference examples</li>
 *   <li>H and W special-case rules</li>
 *   <li>Whitespace, punctuation, and non-ASCII character handling</li>
 *   <li>Variant mappings: Simplified and Genealogy</li>
 *   <li>Phonetic difference score</li>
 *   <li>Constructor variants and static instances</li>
 *   <li>SoundexUtils helper class</li>
 * </ol>
 *
 * <p>Keep this file in UTF-8 encoding for proper Javadoc processing.</p>
 */
class SoundexTest extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    // -----------------------------------------------------------------------
    // Encoding correctness — common words and reference examples
    // -----------------------------------------------------------------------

    /**
     * Verifies basic encoding of common English words against known expected codes.
     * <p>
     * The inputs are the words of the well-known "quick brown fox" phrase, providing
     * a quick sanity check of the most common phoneme patterns.
     * </p>
     */
    @Test
    void testEncodeBasic() {
        assertEquals("T235", getStringEncoder().encode("testing"));
        assertEquals("T000", getStringEncoder().encode("The"));
        assertEquals("Q200", getStringEncoder().encode("quick"));
        assertEquals("B650", getStringEncoder().encode("brown"));
        assertEquals("F200", getStringEncoder().encode("fox"));
        assertEquals("J513", getStringEncoder().encode("jumped"));
        assertEquals("O160", getStringEncoder().encode("over"));
        assertEquals("T000", getStringEncoder().encode("the"));
        assertEquals("L200", getStringEncoder().encode("lazy"));
        assertEquals("D200", getStringEncoder().encode("dogs"));
    }

    /**
     * Verifies that 40 genealogically related surname variants all collapse to the
     * same Soundex code B650, demonstrating Soundex's core purpose of grouping
     * phonetically similar names together regardless of spelling.
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
     * Examples from http://www.bradandkathy.com/genealogy/overviewofsoundex.html
     */
    @Test
    void testEncodeBatch2() {
        assertEquals("A462", getStringEncoder().encode("Allricht"));
        assertEquals("E166", getStringEncoder().encode("Eberhard"));
        assertEquals("E521", getStringEncoder().encode("Engebrethson"));
        assertEquals("H512", getStringEncoder().encode("Heimbach"));
        assertEquals("H524", getStringEncoder().encode("Hanselmann"));
        assertEquals("H431", getStringEncoder().encode("Hildebrand"));
        assertEquals("K152", getStringEncoder().encode("Kavanagh"));
        assertEquals("L530", getStringEncoder().encode("Lind"));
        assertEquals("L222", getStringEncoder().encode("Lukaschowsky"));
        assertEquals("M235", getStringEncoder().encode("McDonnell"));
        assertEquals("M200", getStringEncoder().encode("McGee"));
        assertEquals("O155", getStringEncoder().encode("Opnian"));
        assertEquals("O155", getStringEncoder().encode("Oppenheimer"));
        assertEquals("R355", getStringEncoder().encode("Riedemanas"));
        assertEquals("Z300", getStringEncoder().encode("Zita"));
        assertEquals("Z325", getStringEncoder().encode("Zitzmeinn"));
    }

    /**
     * Examples from http://www.archives.gov/research_room/genealogy/census/soundex.html
     */
    @Test
    void testEncodeBatch3() {
        assertEquals("W252", getStringEncoder().encode("Washington"));
        assertEquals("L000", getStringEncoder().encode("Lee"));
        assertEquals("G362", getStringEncoder().encode("Gutierrez"));
        assertEquals("P236", getStringEncoder().encode("Pfister"));
        assertEquals("J250", getStringEncoder().encode("Jackson"));
        assertEquals("T522", getStringEncoder().encode("Tymczak"));
        // VanDeusen can also be coded D-250 (D, 2 for the S, 5 for the N, 0 added).
        assertEquals("V532", getStringEncoder().encode("VanDeusen"));
    }

    /**
     * Examples from: http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testEncodeBatch4() {
        assertEquals("H452", getStringEncoder().encode("HOLMES"));
        assertEquals("A355", getStringEncoder().encode("ADOMOMI"));
        assertEquals("V536", getStringEncoder().encode("VONDERLEHR"));
        assertEquals("B400", getStringEncoder().encode("BALL"));
        assertEquals("S000", getStringEncoder().encode("SHAW"));
        assertEquals("J250", getStringEncoder().encode("JACKSON"));
        assertEquals("S545", getStringEncoder().encode("SCANLON"));
        assertEquals("S532", getStringEncoder().encode("SAINTJOHN"));
    }

    /**
     * Examples for MS SQLServer from
     * https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
     * <p>
     * "Smith" and "Smythe" share the same code because the vowels 'i'/'y' between
     * the consonants are not encoded.
     * </p>
     */
    @Test
    void testMsSqlServer1() {
        assertEquals("S530", getStringEncoder().encode("Smith"));
        assertEquals("S530", getStringEncoder().encode("Smythe"));
    }

    /**
     * Examples for MS SQLServer from
     * https://support.microsoft.com/default.aspx?scid=https://support.microsoft.com:80/support
     * /kb/articles/Q100/3/65.asp&NoWebContent=1
     * <p>
     * All Erickson/Erikson/Ericson/Ericksen/Ericsen spelling variants should
     * encode to the same code E625.
     * </p>
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testMsSqlServer2() throws EncoderException {
        checkEncodingVariations("E625", "Erickson", "Erickson", "Erikson", "Ericson", "Ericksen", "Ericsen");
    }

    /**
     * Examples for MS SQLServer from https://databases.about.com/library/weekly/aa042901a.htm
     */
    @Test
    void testMsSqlServer3() {
        assertEquals("A500", getStringEncoder().encode("Ann"));
        assertEquals("A536", getStringEncoder().encode("Andrew"));
        assertEquals("J530", getStringEncoder().encode("Janet"));
        assertEquals("M626", getStringEncoder().encode("Margaret"));
        assertEquals("S315", getStringEncoder().encode("Steven"));
        assertEquals("M240", getStringEncoder().encode("Michael"));
        assertEquals("R163", getStringEncoder().encode("Robert"));
        assertEquals("L600", getStringEncoder().encode("Laura"));
        assertEquals("A500", getStringEncoder().encode("Anne"));
    }

    /**
     * Tests example from https://en.wikipedia.org/wiki/Soundex#American_Soundex as of 2015-03-22.
     */
    @Test
    void testWikipediaAmericanSoundex() {
        assertEquals("R163", getStringEncoder().encode("Robert"));
        assertEquals("R163", getStringEncoder().encode("Rupert"));
        assertEquals("A261", getStringEncoder().encode("Ashcraft"));
        assertEquals("A261", getStringEncoder().encode("Ashcroft"));
        assertEquals("T522", getStringEncoder().encode("Tymczak"));
        assertEquals("P236", getStringEncoder().encode("Pfister"));
    }

    // -----------------------------------------------------------------------
    // H and W special-case rules
    // -----------------------------------------------------------------------

    /**
     * Consonants from the same code group separated by H or W are treated as one.
     * <p>
     * From http://www.archives.gov/research_room/genealogy/census/soundex.html:<br>
     * "Ashcraft" is coded A-261 (A, 2 for the S, C ignored, 6 for the R, 1 for
     * the F). The 'h' between 'S' and 'C' causes them to share a single slot,
     * so the code is NOT A-226.
     * </p>
     */
    @Test
    void testHWRuleEx1() {
        assertEquals("A261", getStringEncoder().encode("Ashcraft"));
        assertEquals("A261", getStringEncoder().encode("Ashcroft"));
        assertEquals("Y330", getStringEncoder().encode("yehudit"));
        assertEquals("Y330", getStringEncoder().encode("yhwdyt"));
    }

    /**
     * Consonants from the same code group separated by H or W are treated as one.
     * <p>
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm:<br>
     * "BOOTHDAVIS" and its hyphenated form "BOOTH-DAVIS" encode identically
     * because the intervening H is silent between same-group consonants.
     * </p>
     */
    @Test
    void testHWRuleEx2() {
        assertEquals("B312", getStringEncoder().encode("BOOTHDAVIS"));
        assertEquals("B312", getStringEncoder().encode("BOOTH-DAVIS"));
    }

    /**
     * Consonants from the same code group separated by H or W are treated as one.
     * <p>
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm:<br>
     * All 16 names in the SAILOR/SCHILLER family encode to S460 because the
     * intervening vowels, H, and W between 'S' and the 'L'-group consonant
     * do not introduce a different phoneme code.
     * </p>
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testHWRuleEx3() throws EncoderException {
        assertEquals("S460", getStringEncoder().encode("Sgler"));
        assertEquals("S460", getStringEncoder().encode("Swhgler"));
        // Also S460:
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

    // -----------------------------------------------------------------------
    // Whitespace, punctuation, and non-ASCII character handling
    // -----------------------------------------------------------------------

    /**
     * Verifies that non-alphabetic characters (e.g. {@code '>'}) embedded in an
     * input string are silently skipped and do not corrupt the encoded result.
     */
    @Test
    void testBadCharacters() {
        assertEquals("H452", getStringEncoder().encode("HOL>MES"));
    }

    /**
     * Verifies that apostrophes embedded anywhere in a name are ignored, so all
     * spelling variants of "O'Brien" collapse to the same Soundex code.
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
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     * <p>
     * Verifies that hyphens embedded anywhere in a name are ignored, so all
     * hyphenated variants of "KINGSMITH" collapse to the same Soundex code.
     * </p>
     *
     * @throws EncoderException for some failure scenarios
     */
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
     * Verifies that surrounding whitespace (spaces, tabs, newlines, carriage
     * returns) is stripped before encoding, so "Washington" is recognized
     * regardless of surrounding whitespace.
     */
    @Test
    void testEncodeIgnoreTrimmable() {
        assertEquals("W252", getStringEncoder().encode(" \t\n\r Washington \t\n\r "));
    }

    /**
     * Fancy characters (e.g. é, U+00E9) outside the basic A–Z range are not
     * present in the default US mapping.
     * <ul>
     *   <li>If the JVM classifies é as a letter, encoding it must throw
     *       {@link IllegalArgumentException} because no mapping entry exists.</li>
     *   <li>Otherwise (é is not a letter) the encoder returns an empty string.</li>
     * </ul>
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-30">CODEC-30</a>
     */
    @Test
    void testUsMappingEWithAcute() {
        assertEquals("E000", getStringEncoder().encode("e"));
        if (Character.isLetter('é')) { // e-acute (é)
            //         uppercase E-acute
            assertThrows(IllegalArgumentException.class, () -> getStringEncoder().encode("é"));
        } else {
            assertEquals("", getStringEncoder().encode("é"));
        }
    }

    /**
     * Fancy characters (e.g. ö, U+00F6) outside the basic A–Z range are not
     * present in the default US mapping.
     * <ul>
     *   <li>If the JVM classifies ö as a letter, encoding it must throw
     *       {@link IllegalArgumentException} because no mapping entry exists.</li>
     *   <li>Otherwise (ö is not a letter) the encoder returns an empty string.</li>
     * </ul>
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-30">CODEC-30</a>
     */
    @Test
    void testUsMappingOWithDiaeresis() {
        assertEquals("O000", getStringEncoder().encode("o"));
        if (Character.isLetter('ö')) { // o-umlaut (ö)
            //         uppercase O-umlaut
            assertThrows(IllegalArgumentException.class, () -> getStringEncoder().encode("ö"));
        } else {
            assertEquals("", getStringEncoder().encode("ö"));
        }
    }

    // -----------------------------------------------------------------------
    // Variant Soundex mappings: Simplified and Genealogy
    // -----------------------------------------------------------------------

    /**
     * Tests the Simplified Soundex variant ({@link Soundex#US_ENGLISH_SIMPLIFIED}).
     * In this mode H and W are treated the same as vowels: they are not encoded
     * but they <em>do</em> act as separators between consecutive consonants that
     * share the same code group.
     * <p>
     * Algorithm rules from: http://west-penwith.org.uk/misc/soundex.htm
     * </p>
     */
    @Test
    void testSimplifiedSoundex() { // treat vowels and HW as separators
        final Soundex s = Soundex.US_ENGLISH_SIMPLIFIED;
        assertEquals("W452", s.encode("WILLIAMS"));
        assertEquals("B625", s.encode("BARAGWANATH"));
        assertEquals("D540", s.encode("DONNELL"));
        assertEquals("L300", s.encode("LLOYD"));
        assertEquals("W422", s.encode("WOOLCOCK"));
        // In Simplified mode 'o', 'w', and 'h' all act as separators, so the
        // duplicate D's in "Dodds" are split and each contributes its own code.
        assertEquals("D320", s.encode("Dodds"));
        assertEquals("D320", s.encode("Dwdds")); // w is a separator
        assertEquals("D320", s.encode("Dhdds")); // h is a separator
    }

    /**
     * Tests the Genealogy Soundex variant ({@link Soundex#US_ENGLISH_GENEALOGY}).
     * In this mode vowels (A, E, I, O, U, Y), H, and W are all completely silent:
     * they are ignored after the first character and do <em>not</em> act as
     * separators between duplicate codes.
     * <p>
     * Algorithm rules from: http://www.genealogy.com/articles/research/00000060.html
     * </p>
     */
    @Test
    void testGenealogy() { // treat vowels and HW as silent
        final Soundex s = Soundex.US_ENGLISH_GENEALOGY;
        assertEquals("H251", s.encode("Heggenburger"));
        assertEquals("B425", s.encode("Blackman"));
        assertEquals("S530", s.encode("Schmidt"));
        assertEquals("L150", s.encode("Lippmann"));
        // In Genealogy mode 'o', 'h', and 'w' are all silent (not separators),
        // so each pair of D's collapses to a single code-3 slot.
        assertEquals("D200", s.encode("Dodds")); // 'o' is not a separator here - it is silent
        assertEquals("D200", s.encode("Dhdds")); // 'h' is silent
        assertEquals("D200", s.encode("Dwdds")); // 'w' is silent
    }

    // -----------------------------------------------------------------------
    // Phonetic difference score
    // -----------------------------------------------------------------------

    /**
     * Tests {@link Soundex#difference(String, String)}, which counts how many
     * character positions match between two Soundex codes and returns a score
     * from 0 (no phonetic similarity) to 4 (identical phonetics).
     */
    @Test
    void testDifference() throws EncoderException {
        // Edge cases: null and blank inputs yield no similarity
        assertEquals(0, getStringEncoder().difference(null, null));
        assertEquals(0, getStringEncoder().difference("", ""));
        assertEquals(0, getStringEncoder().difference(" ", " "));

        // Normal cases
        assertEquals(4, getStringEncoder().difference("Smith", "Smythe"));
        assertEquals(2, getStringEncoder().difference("Ann", "Andrew"));
        assertEquals(1, getStringEncoder().difference("Margaret", "Andrew"));
        assertEquals(0, getStringEncoder().difference("Janet", "Margaret"));

        // Examples from https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_de-dz_8co5.asp
        assertEquals(4, getStringEncoder().difference("Green", "Greene"));
        assertEquals(0, getStringEncoder().difference("Blotchet-Halls", "Greene"));

        // Examples from https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
        assertEquals(4, getStringEncoder().difference("Smith", "Smythe"));
        assertEquals(4, getStringEncoder().difference("Smithers", "Smythers"));
        assertEquals(2, getStringEncoder().difference("Anothers", "Brothers"));
    }

    // -----------------------------------------------------------------------
    // Constructor variants and static instances
    // -----------------------------------------------------------------------

    /**
     * Verifies that the default no-arg constructor produces the expected encoding.
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-54">CODEC-54</a>
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-56">CODEC-56</a>
     */
    @Test
    void testNewInstance() {
        assertEquals("W452", new Soundex().soundex("Williams"));
    }

    /**
     * Verifies that constructing {@link Soundex} from a {@code char[]} mapping
     * derived from {@link Soundex#US_ENGLISH_MAPPING_STRING} produces the same
     * encoding as the default constructor.
     */
    @Test
    void testNewInstance2() {
        assertEquals("W452", new Soundex(Soundex.US_ENGLISH_MAPPING_STRING.toCharArray()).soundex("Williams"));
    }

    /**
     * Verifies that constructing {@link Soundex} directly from the
     * {@link Soundex#US_ENGLISH_MAPPING_STRING} String produces the same encoding
     * as the default constructor.
     */
    @Test
    void testNewInstance3() {
        assertEquals("W452", new Soundex(Soundex.US_ENGLISH_MAPPING_STRING).soundex("Williams"));
    }

    /**
     * Verifies that the shared static {@link Soundex#US_ENGLISH} instance is
     * correctly initialized and produces the expected encoding.
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-54">CODEC-54</a>
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-56">CODEC-56</a>
     */
    @Test
    void testUsEnglishStatic() {
        assertEquals("W452", Soundex.US_ENGLISH.soundex("Williams"));
    }

    // -----------------------------------------------------------------------
    // SoundexUtils helper class
    // -----------------------------------------------------------------------

    /**
     * Verifies that {@link SoundexUtils} can be instantiated directly (it does
     * not enforce a private constructor in this version).
     */
    @Test
    void testSoundexUtilsConstructable() {
        new SoundexUtils();
    }

    /**
     * Verifies null and empty input handling in the {@link SoundexUtils} helpers:
     * <ul>
     *   <li>{@link SoundexUtils#clean(String)} returns {@code null} for {@code null}
     *       and {@code ""} for an empty string.</li>
     *   <li>{@link SoundexUtils#differenceEncoded(String, String)} returns 0 when
     *       either argument is {@code null}.</li>
     * </ul>
     */
    @Test
    void testSoundexUtilsNullBehaviour() {
        assertNull(SoundexUtils.clean(null));
        assertEquals("", SoundexUtils.clean(""));
        assertEquals(0, SoundexUtils.differenceEncoded(null, ""));
        assertEquals(0, SoundexUtils.differenceEncoded("", null));
    }
}
