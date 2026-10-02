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

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Soundex}.
 *
 * <p>Keep this file in UTF-8 encoding for proper Javadoc processing.</p>
 */
class SoundexTest {

    private final Soundex stringEncoder = createStringEncoder();

    private Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testB650() throws EncoderException {
        checkEncodingVariations("B650",
            "BARHAM", "BARONE", "BARRON", "BERNA", "BIRNEY", "BIRNIE", "BOOROM", "BOREN", "BORN",
            "BOURN", "BOURNE", "BOWRON", "BRAIN", "BRAME", "BRANN", "BRAUN", "BREEN", "BRIEN",
            "BRIM", "BRIMM", "BRINN", "BRION", "BROOM", "BROOME", "BROWN", "BROWNE", "BRUEN",
            "BRUHN", "BRUIN", "BRUMM", "BRUN", "BRUNO", "BRYAN", "BURIAN", "BURN", "BURNEY",
            "BYRAM", "BYRNE", "BYRON", "BYRUM");
    }

    @Test
    void testBadCharacters() {
        assertEncodes("H452", "HOL>MES");
    }

    @Test
    void testDifference() throws EncoderException {
        assertDifference(0, null, null);
        assertDifference(0, "", "");
        assertDifference(0, " ", " ");

        assertDifference(4, "Smith", "Smythe");
        assertDifference(2, "Ann", "Andrew");
        assertDifference(1, "Margaret", "Andrew");
        assertDifference(0, "Janet", "Margaret");

        assertDifference(4, "Green", "Greene");
        assertDifference(0, "Blotchet-Halls", "Greene");

        assertDifference(4, "Smith", "Smythe");
        assertDifference(4, "Smithers", "Smythers");
        assertDifference(2, "Anothers", "Brothers");
    }

    @Test
    void testEncodeBasic() {
        assertEncodes("T235", "testing");
        assertEncodes("T000", "The");
        assertEncodes("Q200", "quick");
        assertEncodes("B650", "brown");
        assertEncodes("F200", "fox");
        assertEncodes("J513", "jumped");
        assertEncodes("O160", "over");
        assertEncodes("T000", "the");
        assertEncodes("L200", "lazy");
        assertEncodes("D200", "dogs");
    }

    /**
     * Examples from http://www.bradandkathy.com/genealogy/overviewofsoundex.html
     */
    @Test
    void testEncodeBatch2() {
        assertEncodes("A462", "Allricht");
        assertEncodes("E166", "Eberhard");
        assertEncodes("E521", "Engebrethson");
        assertEncodes("H512", "Heimbach");
        assertEncodes("H524", "Hanselmann");
        assertEncodes("H431", "Hildebrand");
        assertEncodes("K152", "Kavanagh");
        assertEncodes("L530", "Lind");
        assertEncodes("L222", "Lukaschowsky");
        assertEncodes("M235", "McDonnell");
        assertEncodes("M200", "McGee");
        assertEncodes("O155", "Opnian");
        assertEncodes("O155", "Oppenheimer");
        assertEncodes("R355", "Riedemanas");
        assertEncodes("Z300", "Zita");
        assertEncodes("Z325", "Zitzmeinn");
    }

    /**
     * Examples from http://www.archives.gov/research_room/genealogy/census/soundex.html
     */
    @Test
    void testEncodeBatch3() {
        assertEncodes("W252", "Washington");
        assertEncodes("L000", "Lee");
        assertEncodes("G362", "Gutierrez");
        assertEncodes("P236", "Pfister");
        assertEncodes("J250", "Jackson");
        assertEncodes("T522", "Tymczak");
        assertEncodes("V532", "VanDeusen");
    }

    /**
     * Examples from http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testEncodeBatch4() {
        assertEncodes("H452", "HOLMES");
        assertEncodes("A355", "ADOMOMI");
        assertEncodes("V536", "VONDERLEHR");
        assertEncodes("B400", "BALL");
        assertEncodes("S000", "SHAW");
        assertEncodes("J250", "JACKSON");
        assertEncodes("S545", "SCANLON");
        assertEncodes("S532", "SAINTJOHN");
    }

    @Test
    void testEncodeIgnoreApostrophes() throws EncoderException {
        checkEncodingVariations("O165",
            "OBrien", "'OBrien", "O'Brien", "OB'rien", "OBr'ien", "OBri'en", "OBrie'n", "OBrien'");
    }

    /**
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testEncodeIgnoreHyphens() throws EncoderException {
        checkEncodingVariations("K525",
            "KINGSMITH", "-KINGSMITH", "K-INGSMITH", "KI-NGSMITH", "KIN-GSMITH", "KING-SMITH",
            "KINGS-MITH", "KINGSM-ITH", "KINGSMI-TH", "KINGSMIT-H", "KINGSMITH-");
    }

    @Test
    void testEncodeIgnoreTrimmable() {
        assertEncodes("W252", " \t\n\r Washington \t\n\r ");
    }

    /**
     * Examples and algorithm rules from http://www.genealogy.com/articles/research/00000060.html.
     */
    @Test
    void testGenealogy() {
        final Soundex soundex = Soundex.US_ENGLISH_GENEALOGY;
        assertEncodes(soundex, "H251", "Heggenburger");
        assertEncodes(soundex, "B425", "Blackman");
        assertEncodes(soundex, "S530", "Schmidt");
        assertEncodes(soundex, "L150", "Lippmann");
        assertEncodes(soundex, "D200", "Dodds");
        assertEncodes(soundex, "D200", "Dhdds");
        assertEncodes(soundex, "D200", "Dwdds");
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     */
    @Test
    void testHWRuleEx1() {
        assertEncodes("A261", "Ashcraft");
        assertEncodes("A261", "Ashcroft");
        assertEncodes("Y330", "yehudit");
        assertEncodes("Y330", "yhwdyt");
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testHWRuleEx2() {
        assertEncodes("B312", "BOOTHDAVIS");
        assertEncodes("B312", "BOOTH-DAVIS");
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * @throws EncoderException for some failure scenarios
     */
    @Test
    void testHWRuleEx3() throws EncoderException {
        assertEncodes("S460", "Sgler");
        assertEncodes("S460", "Swhgler");
        checkEncodingVariations("S460",
            "SAILOR", "SALYER", "SAYLOR", "SCHALLER", "SCHELLER", "SCHILLER", "SCHOOLER", "SCHULER",
            "SCHUYLER", "SEILER", "SEYLER", "SHOLAR", "SHULER", "SILAR", "SILER", "SILLER");
    }

    /**
     * Examples for MS SQLServer from
     * https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
     */
    @Test
    void testMsSqlServer1() {
        assertEncodes("S530", "Smith");
        assertEncodes("S530", "Smythe");
    }

    /**
     * Examples for MS SQLServer from https://support.microsoft.com.
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
        assertEncodes("A500", "Ann");
        assertEncodes("A536", "Andrew");
        assertEncodes("J530", "Janet");
        assertEncodes("M626", "Margaret");
        assertEncodes("S315", "Steven");
        assertEncodes("M240", "Michael");
        assertEncodes("R163", "Robert");
        assertEncodes("L600", "Laura");
        assertEncodes("A500", "Anne");
    }

    /**
     * https://issues.apache.org/jira/browse/CODEC-54 https://issues.apache.org/jira/browse/CODEC-56
     */
    @Test
    void testNewInstance() {
        assertEquals("W452", new Soundex().soundex("Williams"));
    }

    @Test
    void testNewInstance2() {
        assertEquals("W452", new Soundex(Soundex.US_ENGLISH_MAPPING_STRING.toCharArray()).soundex("Williams"));
    }

    @Test
    void testNewInstance3() {
        assertEquals("W452", new Soundex(Soundex.US_ENGLISH_MAPPING_STRING).soundex("Williams"));
    }

    /**
     * Examples and algorithm rules from http://west-penwith.org.uk/misc/soundex.htm.
     */
    @Test
    void testSimplifiedSoundex() {
        final Soundex soundex = Soundex.US_ENGLISH_SIMPLIFIED;
        assertEncodes(soundex, "W452", "WILLIAMS");
        assertEncodes(soundex, "B625", "BARAGWANATH");
        assertEncodes(soundex, "D540", "DONNELL");
        assertEncodes(soundex, "L300", "LLOYD");
        assertEncodes(soundex, "W422", "WOOLCOCK");
        assertEncodes(soundex, "D320", "Dodds");
        assertEncodes(soundex, "D320", "Dwdds");
        assertEncodes(soundex, "D320", "Dhdds");
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
     * https://issues.apache.org/jira/browse/CODEC-54 https://issues.apache.org/jira/browse/CODEC-56
     */
    @Test
    void testUsEnglishStatic() {
        assertEquals("W452", Soundex.US_ENGLISH.soundex("Williams"));
    }

    /**
     * Fancy characters are not mapped by the default US mapping.
     *
     * https://issues.apache.org/jira/browse/CODEC-30
     */
    @Test
    void testUsMappingEWithAcute() {
        assertEncodes("E000", "e");
        if (Character.isLetter('\u00e9')) {
            assertThrows(IllegalArgumentException.class, () -> getStringEncoder().encode("\u00e9"));
        } else {
            assertEncodes("", "\u00e9");
        }
    }

    /**
     * Fancy characters are not mapped by the default US mapping.
     *
     * https://issues.apache.org/jira/browse/CODEC-30
     */
    @Test
    void testUsMappingOWithDiaeresis() {
        assertEncodes("O000", "o");
        if (Character.isLetter('\u00f6')) {
            assertThrows(IllegalArgumentException.class, () -> getStringEncoder().encode("\u00f6"));
        } else {
            assertEncodes("", "\u00f6");
        }
    }

    /**
     * Tests example from https://en.wikipedia.org/wiki/Soundex#American_Soundex as of 2015-03-22.
     */
    @Test
    void testWikipediaAmericanSoundex() {
        assertEncodes("R163", "Robert");
        assertEncodes("R163", "Rupert");
        assertEncodes("A261", "Ashcraft");
        assertEncodes("A261", "Ashcroft");
        assertEncodes("T522", "Tymczak");
        assertEncodes("P236", "Pfister");
    }

    private void assertDifference(final int expectedDifference, final String left, final String right) throws EncoderException {
        assertEquals(expectedDifference, getStringEncoder().difference(left, right));
    }

    private void assertEncodes(final String expectedSoundex, final String source) {
        assertEquals(expectedSoundex, getStringEncoder().encode(source));
    }

    private void assertEncodes(final Soundex soundex, final String expectedSoundex, final String source) {
        assertEquals(expectedSoundex, soundex.encode(source));
    }

    private void checkEncodingVariations(final String expectedSoundex, final String... sources) throws EncoderException {
        for (final String source : sources) {
            assertEquals(expectedSoundex, getStringEncoder().encode(source));
            assertEquals(expectedSoundex, getStringEncoder().encode((Object) source));
        }
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }
}
