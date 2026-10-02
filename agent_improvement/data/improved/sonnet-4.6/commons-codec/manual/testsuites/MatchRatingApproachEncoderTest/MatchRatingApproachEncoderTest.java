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

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Match Rating Approach (MRA) phonetic algorithm.
 *
 * <p>The MRA algorithm encodes names phonetically to support fuzzy name matching.
 * The encoding pipeline is: clean → remove vowels (except word-initial) →
 * remove double consonants → take first 3 + last 3 letters.
 *
 * <p>Test method naming convention:
 * {@code test<FeatureArea>_<InputDescription>_<ExpectedOutcome>}
 *
 * <p>Corner-case inputs (null, empty, single-character, whitespace-only) are
 * labelled "CornerCase" in the method name.
 */
class MatchRatingApproachEncoderTest extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    // -------------------------------------------------------------------------
    // removeAccents
    // -------------------------------------------------------------------------

    @Test
    final void testAccentRemoval_NullInput_ReturnsNull() {
        assertNull(getStringEncoder().removeAccents(null));
    }

    @Test
    final void testAccentRemoval_EmptyString_ReturnsEmpty() {
        assertEquals("", getStringEncoder().removeAccents(""));
    }

    @Test
    final void testAccentRemoval_PlainAsciiString_Unchanged() {
        assertEquals("Colorless green ideas sleep furiously",
                getStringEncoder().removeAccents("Colorless green ideas sleep furiously"));
    }

    @Test
    final void testAccentRemoval_LowercaseVowelsWithAcutes_AccentsStripped() {
        assertEquals("aeiou", getStringEncoder().removeAccents("áéíóú"));
    }

    @Test
    final void testAccentRemoval_MixedCaseAccentedVowels_CasePreserved() {
        assertEquals("AeiOuu", getStringEncoder().removeAccents("ÁeíÓuu"));
    }

    @Test
    final void testAccentRemoval_StringWithSpaces_SpacesPreserved() {
        assertEquals("ae io  u", getStringEncoder().removeAccents("áé íó  ú"));
    }

    @Test
    final void testAccentRemoval_GermanSpanishFrenchMix_AccentsStripped() {
        assertEquals("aeoußAEOUnNa", getStringEncoder().removeAccents("äëöüßÄËÖÜñÑà"));
    }

    @Test
    final void testAccentRemoval_FrenchAccentedLettersWithPunctuation_PunctuationPreserved() {
        assertEquals("E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c",
                getStringEncoder().removeAccents("È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç"));
    }

    @Test
    final void testAccentRemoval_MixedAccentsAndNonAlphaChars_NonAlphaPreserved() {
        assertEquals("A-e'i.,o&u", getStringEncoder().removeAccents("Á-e'í.,ó&ú"));
    }

    // -------------------------------------------------------------------------
    // cleanName
    // -------------------------------------------------------------------------

    @Test
    final void testCleanName_NameWithPunctuationAccentsAndSpaces_ReturnsUppercaseLettersOnly() {
        assertEquals("THISISATEST", getStringEncoder().cleanName("This-ís   a t.,es &t"));
    }

    // -------------------------------------------------------------------------
    // isVowel
    // -------------------------------------------------------------------------

    @Test
    final void testIsVowel_UppercaseA_ReturnsTrue() {
        assertTrue(getStringEncoder().isVowel("A"));
    }

    @Test
    final void testIsVowel_UppercaseI_ReturnsTrue() {
        assertTrue(getStringEncoder().isVowel("I"));
    }

    @Test
    final void testIsVowel_LowercaseConsonant_ReturnsFalse() {
        assertFalse(getStringEncoder().isVowel("d"));
    }

    // -------------------------------------------------------------------------
    // removeVowels
    // -------------------------------------------------------------------------

    @Test
    final void testRemoveVowels_WordStartingWithVowel_InitialVowelRetained() {
        assertEquals("ADN", getStringEncoder().removeVowels("AIDAN"));
    }

    @Test
    final void testRemoveVowels_WordStartingWithConsonant_AllVowelsRemoved() {
        assertEquals("DCLN", getStringEncoder().removeVowels("DECLAN"));
    }

    @Test
    final void testRemoveVowels_LongerWordStartingWithVowel_InitialVowelRetained() {
        assertEquals("ALSSNDR", getStringEncoder().removeVowels("ALESSANDRA"));
    }

    // -------------------------------------------------------------------------
    // removeDoubleConsonants
    // -------------------------------------------------------------------------

    @Test
    final void testRemoveDoubleConsonants_MultipleDoubles_AllCollapsed() {
        assertEquals("MISISIPI", getStringEncoder().removeDoubleConsonants("MISSISSIPPI"));
    }

    @Test
    final void testRemoveDoubleConsonants_SingleDoubleConsonant_Collapsed() {
        assertEquals("BUBLE", getStringEncoder().removeDoubleConsonants("BUBBLE"));
    }

    @Test
    final void testRemoveDoubleConsonants_DoubleVowel_NotCollapsed() {
        assertEquals("BEETLE", getStringEncoder().removeDoubleConsonants("BEETLE"));
    }

    // -------------------------------------------------------------------------
    // getFirst3Last3
    // -------------------------------------------------------------------------

    @Test
    final void testGetFirst3Last3_WordLongerThan6Chars_ReturnsFirst3PlusLast3() {
        assertEquals("Aleder", getStringEncoder().getFirst3Last3("Alexzander"));
    }

    @Test
    final void testGetFirst3Last3_WordOf4Chars_ReturnsWordUnchanged() {
        assertEquals("PETE", getStringEncoder().getFirst3Last3("PETE"));
    }

    // -------------------------------------------------------------------------
    // getMinRating — minimum similarity threshold based on combined name length
    // -------------------------------------------------------------------------

    @Test
    final void testGetMinRating_SumLength1_Returns5() {
        assertEquals(5, getStringEncoder().getMinRating(1));
    }

    @Test
    final void testGetMinRating_SumLength2_Returns5() {
        assertEquals(5, getStringEncoder().getMinRating(2));
    }

    @Test
    final void testGetMinRating_SumLength5_Returns4() {
        assertEquals(4, getStringEncoder().getMinRating(5));
    }

    @Test
    final void testGetMinRating_SumLength5_Returns4_Duplicate() {
        assertEquals(4, getStringEncoder().getMinRating(5));
    }

    @Test
    final void testGetMinRating_SumLength6_Returns4() {
        assertEquals(4, getStringEncoder().getMinRating(6));
    }

    @Test
    final void testGetMinRating_SumLength7_Returns4() {
        assertEquals(4, getStringEncoder().getMinRating(7));
    }

    @Test
    final void testGetMinRating_SumLength7_Returns4_Duplicate() {
        assertEquals(4, getStringEncoder().getMinRating(7));
    }

    @Test
    final void testGetMinRating_SumLength8_Returns3() {
        assertEquals(3, getStringEncoder().getMinRating(8));
    }

    @Test
    final void testGetMinRating_SumLength10_Returns3() {
        assertEquals(3, getStringEncoder().getMinRating(10));
    }

    @Test
    final void testGetMinRating_SumLength11_Returns3() {
        assertEquals(3, getStringEncoder().getMinRating(11));
    }

    @Test
    final void testGetMinRating_SumLength13_Returns1() {
        assertEquals(1, getStringEncoder().getMinRating(13));
    }

    // -------------------------------------------------------------------------
    // leftToRightThenRightToLeftProcessing
    // -------------------------------------------------------------------------

    @Test
    final void testLeftToRightThenRightToLeft_SimilarNames_Returns4() {
        assertEquals(4, getStringEncoder().leftToRightThenRightToLeftProcessing("ALEXANDER", "ALEXANDRA"));
    }

    @Test
    final void testLeftToRightThenRightToLeft_DissimilarNames_Returns0() {
        assertEquals(0, getStringEncoder().leftToRightThenRightToLeftProcessing("EINSTEIN", "MICHAELA"));
    }

    // -------------------------------------------------------------------------
    // encode (MRA code generation)
    // -------------------------------------------------------------------------

    @Test
    final void testGetEncoding_NullInput_ReturnsEmpty() {
        assertEquals("", getStringEncoder().encode(null));
    }

    @Test
    final void testGetEncoding_EmptyString_ReturnsEmpty() {
        assertEquals("", getStringEncoder().encode(""));
    }

    @Test
    final void testGetEncoding_WhitespaceOnly_ReturnsEmpty() {
        assertEquals("", getStringEncoder().encode(" "));
    }

    @Test
    final void testGetEncoding_SingleLetter_ReturnsEmpty() {
        assertEquals("", getStringEncoder().encode("E"));
    }

    @Test
    final void testGetEncoding_PunctuationOnly_ReturnsEmpty() {
        assertEquals("", getStringEncoder().encode(".,-"));
    }

    @Test
    final void testGetEncoding_AllVowelsAndPunctuation_ReturnsInitialVowelOnly() {
        assertEquals("U", getStringEncoder().encode("uoiea.,-AEIOU"));
    }

    @Test
    final void testGetEncoding_AllVowels_ReturnsFirstVowelOnly() {
        assertEquals("A", getStringEncoder().encode("aeiouAEIOU"));
    }

    @Test
    final void testGetEncoding_Smith_ReturnsSMTH() {
        assertEquals("SMTH", getStringEncoder().encode("Smith"));
    }

    @Test
    final void testGetEncoding_Smyth_ReturnsSMYTH() {
        assertEquals("SMYTH", getStringEncoder().encode("Smyth"));
    }

    @Test
    final void testGetEncoding_Harper_ReturnsHRPR() {
        assertEquals("HRPR", getStringEncoder().encode("HARPER"));
    }

    // -------------------------------------------------------------------------
    // isEncodeEquals — corner cases (null, empty, single-char, whitespace)
    // -------------------------------------------------------------------------

    @Test
    final void testIsEncodeEquals_BothNulls_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals(null, null));
    }

    @Test
    final void testIsEncodeEquals_FirstNameNull_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals(null, "test"));
    }

    @Test
    final void testIsEncodeEquals_SecondNameNull_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("test", null));
    }

    @Test
    final void testIsEncodeEquals_FirstNameEmpty_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("", "test"));
    }

    @Test
    final void testIsEncodeEquals_SecondNameEmpty_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("test", ""));
    }

    @Test
    final void testIsEncodeEquals_FirstNameWhitespaceOnly_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals(" ", "test"));
    }

    @Test
    final void testIsEncodeEquals_SecondNameWhitespaceOnly_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("test", " "));
    }

    @Test
    final void testIsEncodeEquals_FirstNameSingleLetter_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("t", "test"));
    }

    @Test
    final void testIsEncodeEquals_SecondNameSingleLetter_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("test", "t"));
    }

    @Test
    final void testIsEncodeEquals_FirstNameNullSecondNameSpace_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals(null, " "));
    }

    @Test
    final void testIsEncodeEquals_FirstNameMurphySecondNameEmpty_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("Murphy", ""));
    }

    @Test
    final void testIsEncodeEquals_FirstNameMurphySecondNameSpace_ReturnsFalse() {
        assertFalse(getStringEncoder().isEncodeEquals("Murphy", " "));
    }

    @Test
    final void testIsEncodeEquals_ShortNameAl_EdDoNotMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Al", "Ed"));
    }

    @Test
    final void testIsEncodeEquals_KarlVsSingleLetterC_DoesNotMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Karl", "C"));
    }

    // -------------------------------------------------------------------------
    // isEncodeEquals — name comparisons (matching pairs)
    // -------------------------------------------------------------------------

    @Test
    final void testIsEncodeEquals_SameNameBothJohn_ReturnsTrue() {
        assertTrue(getStringEncoder().isEncodeEquals("John", "John"));
    }

    @Test
    final void testIsEncodeEquals_BrianBryan_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Brian", "Bryan"));
    }

    @Test
    final void testIsEncodeEquals_SmithSmyth_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("smith", "smyth"));
    }

    @Test
    final void testIsEncodeEquals_SophiaSofia_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Sophie", "Sofia"));
    }

    @Test
    final void testIsEncodeEquals_SamSamuel_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Sam", "Samuel"));
    }

    @Test
    final void testIsEncodeEquals_SeanJohn_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Sean", "John"));
    }

    @Test
    final void testIsEncodeEquals_MickyMichael_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Micky", "Michael"));
    }

    @Test
    final void testIsEncodeEquals_TomaszTom_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Tomasz", "tom"));
    }

    @Test
    final void testIsEncodeEquals_ZachZacharia_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Zach", "Zacharia"));
    }

    @Test
    final void testIsEncodeEquals_StephenSteven_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Stephen", "Steven"));
    }

    @Test
    final void testIsEncodeEquals_StephenStefan_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Stephen", "Stefan"));
    }

    @Test
    final void testIsEncodeEquals_StevenStefan_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Steven", "Stefan"));
    }

    @Test
    final void testIsEncodeEquals_BurnsBourne_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Burns", "Bourne"));
    }

    @Test
    final void testIsEncodeEquals_CatherineKathryn_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Catherine", "Kathryn"));
    }

    @Test
    final void testIsEncodeEquals_FranciszekFrances_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Franciszek", "Frances"));
    }

    @Test
    final void testIsEncodeEquals_PetersonPeters_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Peterson", "Peters"));
    }

    @Test
    final void testIsEncodeEquals_OonaOonagh_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Oona", "Oonagh"));
    }

    @Test
    final void testIsEncodeEquals_SeanShaun_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Séan", "Shaun"));
    }

    // Kl is short but the algorithm still matches it to the longer Karl
    @Test
    final void testIsEncodeEquals_ShortInputKlKarl_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Kl", "Karl"));
    }

    @Test
    final void testIsEncodeEquals_McGowan_McGeoghegan_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("McGowan", "Mc Geoghegan"));
    }

    // -------------------------------------------------------------------------
    // isEncodeEquals — surname comparisons (matching pairs)
    // -------------------------------------------------------------------------

    @Test
    final void testIsEncodeEquals_AuerbachUhrbach_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Auerbach", "Uhrbach"));
    }

    @Test
    final void testIsEncodeEquals_HaileyHalley_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Hailey", "Halley"));
    }

    @Test
    final void testIsEncodeEquals_LewinskyLevinski_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("LEWINSKY", "LEVINSKI"));
    }

    @Test
    final void testIsEncodeEquals_LipshitzLippszyc_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("LIPSHITZ", "LIPPSZYC"));
    }

    @Test
    final void testIsEncodeEquals_MoskowitzMoskovitz_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Moskowitz", "Moskovitz"));
    }

    @Test
    final void testIsEncodeEquals_SzlamawiczShlamovitz_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("SZLAMAWICZ", "SHLAMOVITZ"));
    }

    @Test
    final void testIsEncodeEquals_CooperFlynnSuperLyn_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Cooper-Flynn", "Super-Lyn"));
    }

    // Both names contain accents, symbols and spaces that are cleaned before comparison
    @Test
    final void testIsEncodeEquals_ColmColinWithAccentsAndSymbols_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("Cólm.   ", "C-olín"));
    }

    @Test
    final void testIsEncodeEquals_OSullivanOSuilleabhain_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("O'Sullivan", "Ó ' Súilleabháin"));
    }

    // Long surname pair where one romanisation uses inserted spaces
    @Test
    final void testIsEncodeEquals_OMuireadhaighOMuircheartaigh_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("o'muireadhaigh", "Ó 'Muircheartaigh "));
    }

    // Names with spaces between every character — both cleaned to the same consonant skeleton
    @Test
    final void testIsEncodeEquals_PrzemyslPshemeshil_Match() {
        assertTrue(getStringEncoder().isEncodeEquals(" P rz e m y s l", " P sh e m e sh i l"));
    }

    @Test
    final void testIsEncodeEquals_RosochowaciecRosokhovatsets_Match() {
        assertTrue(getStringEncoder().isEncodeEquals("R o s o ch o w a c ie c", " R o s o k ho v a ts e ts"));
    }

    // -------------------------------------------------------------------------
    // isEncodeEquals — non-matching pairs
    // -------------------------------------------------------------------------

    @Test
    final void testIsEncodeEquals_SeanPete_NoMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Sean", "Pete"));
    }

    @Test
    final void testIsEncodeEquals_MurphyLynch_NoMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Murphy", "Lynch"));
    }

    @Test
    final void testIsEncodeEquals_KarlAlessandro_NoMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Karl", "Alessandro"));
    }

    @Test
    final void testIsEncodeEquals_MoriartyOMuircheartaigh_NoMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Moriarty", "OMuircheartaigh"));
    }

    // The algorithm cannot match these phonetically similar names
    @Test
    final void testIsEncodeEquals_UnaOonagh_DoesNotMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Úna", "Oonagh")); // Disappointing
    }

    // -------------------------------------------------------------------------
    // isEncodeEquals — whitespace tolerance
    // -------------------------------------------------------------------------

    @Test
    final void testIsEncodeEquals_LeadingAndTrailingWhitespace_TreatedAsMatch() {
        // Verify that whitespace around names is ignored during comparison
        assertTrue(getStringEncoder().isEncodeEquals("Brian", "Bryan"));
        assertTrue(getStringEncoder().isEncodeEquals(" Brian", "Bryan"));
        assertTrue(getStringEncoder().isEncodeEquals("Brian ", "Bryan"));
        assertTrue(getStringEncoder().isEncodeEquals(" Brian ", "Bryan"));
        assertTrue(getStringEncoder().isEncodeEquals("Brian", " Bryan"));
        assertTrue(getStringEncoder().isEncodeEquals("Brian", "Bryan "));
        assertTrue(getStringEncoder().isEncodeEquals("Brian", " Bryan "));
    }
}
