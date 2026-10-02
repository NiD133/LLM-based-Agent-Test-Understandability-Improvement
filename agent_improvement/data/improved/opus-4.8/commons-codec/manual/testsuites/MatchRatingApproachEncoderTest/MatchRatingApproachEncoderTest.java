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
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for the Match Rating Approach algorithm.
 *
 * <p>
 * The tests are grouped, one {@link Nested} class per package-protected helper of
 * {@link MatchRatingApproachEncoder}, so that related cases live together. Each group uses a
 * {@link ParameterizedTest} fed by a {@link MethodSource} table of {@code input -> expected}
 * cases; the table is the single place to read or extend the examples.
 * </p>
 *
 * <p>
 * The nested classes use {@link TestInstance.Lifecycle#PER_CLASS} so their data-provider methods
 * can be instance methods (static members are not allowed in inner classes when targeting Java 8).
 * </p>
 */
class MatchRatingApproachEncoderTest extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /** Tests for {@link MatchRatingApproachEncoder#removeAccents(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RemoveAccents {

        Stream<Arguments> accentedToPlain() {
            return Stream.of(
                    arguments("áéíóú", "aeiou"),
                    arguments("È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç",
                            "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c"),
                    arguments("äëöüßÄËÖÜñÑà", "aeoußAEOUnNa"),
                    // Punctuation and symbols are left untouched.
                    arguments("Á-e'í.,ó&ú", "A-e'i.,o&u"),
                    // Empty input is returned unchanged.
                    arguments("", ""),
                    // Case is preserved while accents are stripped.
                    arguments("ÁeíÓuu", "AeiOuu"),
                    // Spaces (including runs of spaces) are preserved.
                    arguments("áé íó  ú", "ae io  u"),
                    // A string with no accents is returned unchanged.
                    arguments("Colorless green ideas sleep furiously", "Colorless green ideas sleep furiously"));
        }

        @ParameterizedTest(name = "removeAccents(\"{0}\") = \"{1}\"")
        @MethodSource("accentedToPlain")
        void stripsAccentsAndKeepsEverythingElse(final String accented, final String expected) {
            assertEquals(expected, getStringEncoder().removeAccents(accented));
        }

        @Test
        void returnsNullForNullInput() {
            assertNull(getStringEncoder().removeAccents(null));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#cleanName(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class CleanName {

        @Test
        void upperCasesAndStripsPunctuationAccentsAndSpaces() {
            assertEquals("THISISATEST", getStringEncoder().cleanName("This-ís   a t.,es &t"));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#encode(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Encode {

        Stream<Arguments> nameToCode() {
            return Stream.of(
                    arguments("HARPER", "HRPR"),
                    arguments("Smith", "SMTH"),
                    arguments("Smyth", "SMYTH"),
                    // Trivial input (null, empty, single space, single letter) encodes to nothing.
                    arguments(null, ""),
                    arguments("", ""),
                    arguments(" ", ""),
                    arguments("E", ""),
                    // Pure punctuation cleans away to nothing.
                    arguments(".,-", ""),
                    // Only a leading vowel survives.
                    arguments("uoiea.,-AEIOU", "U"),
                    arguments("aeiouAEIOU", "A"));
        }

        @ParameterizedTest(name = "encode(\"{0}\") = \"{1}\"")
        @MethodSource("nameToCode")
        void producesExpectedMraCode(final String name, final String expectedCode) {
            assertEquals(expectedCode, getStringEncoder().encode(name));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#getFirst3Last3(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class GetFirst3Last3 {

        Stream<Arguments> nameToFirst3Last3() {
            return Stream.of(
                    // Longer than 6 characters: first three + last three.
                    arguments("Alexzander", "Aleder"),
                    // Six or fewer characters: returned unchanged.
                    arguments("PETE", "PETE"));
        }

        @ParameterizedTest(name = "getFirst3Last3(\"{0}\") = \"{1}\"")
        @MethodSource("nameToFirst3Last3")
        void keepsAtMostFirstAndLastThreeLetters(final String name, final String expected) {
            assertEquals(expected, getStringEncoder().getFirst3Last3(name));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#getMinRating(int)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class GetMinRating {

        Stream<Arguments> sumLengthToMinRating() {
            return Stream.of(
                    // sum <= 4 -> 5
                    arguments(1, 5),
                    arguments(2, 5),
                    // 5 <= sum <= 7 -> 4
                    arguments(5, 4),
                    arguments(6, 4),
                    arguments(7, 4),
                    // 8 <= sum <= 11 -> 3
                    arguments(8, 3),
                    arguments(10, 3),
                    arguments(11, 3),
                    // sum >= 13 -> 1
                    arguments(13, 1));
        }

        @ParameterizedTest(name = "getMinRating({0}) = {1}")
        @MethodSource("sumLengthToMinRating")
        void mapsCombinedLengthToMinimumRating(final int sumLength, final int expectedRating) {
            assertEquals(expectedRating, getStringEncoder().getMinRating(sumLength));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#isVowel(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class IsVowel {

        Stream<Arguments> letterToIsVowel() {
            return Stream.of(
                    arguments("A", true),
                    arguments("I", true),
                    arguments("d", false));
        }

        @ParameterizedTest(name = "isVowel(\"{0}\") = {1}")
        @MethodSource("letterToIsVowel")
        void recognisesVowels(final String letter, final boolean expected) {
            assertEquals(expected, getStringEncoder().isVowel(letter));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#leftToRightThenRightToLeftProcessing(String, String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class LeftToRightThenRightToLeftProcessing {

        Stream<Arguments> namePairToSimilarity() {
            return Stream.of(
                    arguments("ALEXANDER", "ALEXANDRA", 4),
                    arguments("EINSTEIN", "MICHAELA", 0));
        }

        @ParameterizedTest(name = "process(\"{0}\", \"{1}\") = {2}")
        @MethodSource("namePairToSimilarity")
        void returnsSimilarityCount(final String name1, final String name2, final int expected) {
            assertEquals(expected, getStringEncoder().leftToRightThenRightToLeftProcessing(name1, name2));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#removeDoubleConsonants(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RemoveDoubleConsonants {

        Stream<Arguments> nameToSingleConsonants() {
            return Stream.of(
                    // Double consonants collapse to a single one.
                    arguments("MISSISSIPPI", "MISISIPI"),
                    arguments("BUBBLE", "BUBLE"),
                    // Double vowels are not affected.
                    arguments("BEETLE", "BEETLE"));
        }

        @ParameterizedTest(name = "removeDoubleConsonants(\"{0}\") = \"{1}\"")
        @MethodSource("nameToSingleConsonants")
        void collapsesDoubledConsonantsOnly(final String name, final String expected) {
            assertEquals(expected, getStringEncoder().removeDoubleConsonants(name));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#removeVowels(String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RemoveVowels {

        Stream<Arguments> nameToDevoweled() {
            return Stream.of(
                    arguments("AIDAN", "ADN"),
                    arguments("DECLAN", "DCLN"),
                    arguments("ALESSANDRA", "ALSSNDR"));
        }

        @ParameterizedTest(name = "removeVowels(\"{0}\") = \"{1}\"")
        @MethodSource("nameToDevoweled")
        void dropsVowelsExceptALeadingOne(final String name, final String expected) {
            assertEquals(expected, getStringEncoder().removeVowels(name));
        }
    }

    /** Tests for {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}. */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class IsEncodeEquals {

        /** Pairs of names that the algorithm considers homophonous. */
        Stream<Arguments> matchingNames() {
            return Stream.of(
                    arguments("Brian", "Bryan"),
                    arguments("Burns", "Bourne"),
                    arguments("Catherine", "Kathryn"),
                    // Accents, symbols and spaces are cleaned before comparing.
                    arguments("Cólm.   ", "C-olín"),
                    arguments("Sean", "John"),
                    arguments("Franciszek", "Frances"),
                    arguments("o'muireadhaigh", "Ó 'Muircheartaigh "),
                    arguments("McGowan", "Mc Geoghegan"),
                    arguments("Micky", "Michael"),
                    arguments("Oona", "Oonagh"),
                    arguments("Peterson", "Peters"),
                    arguments("Sam", "Samuel"),
                    arguments("Séan", "Shaun"),
                    arguments("Kl", "Karl"),
                    arguments("smith", "smyth"),
                    arguments("Sophie", "Sofia"),
                    arguments("Stephen", "Stefan"),
                    arguments("Stephen", "Steven"),
                    arguments("Steven", "Stefan"),
                    arguments("Auerbach", "Uhrbach"),
                    arguments("Cooper-Flynn", "Super-Lyn"),
                    arguments("Hailey", "Halley"),
                    arguments("LEWINSKY", "LEVINSKI"),
                    arguments("LIPSHITZ", "LIPPSZYC"),
                    arguments("Moskowitz", "Moskovitz"),
                    arguments("O'Sullivan", "Ó ' Súilleabháin"),
                    arguments(" P rz e m y s l", " P sh e m e sh i l"),
                    arguments("R o s o ch o w a c ie c", " R o s o k ho v a ts e ts"),
                    arguments("SZLAMAWICZ", "SHLAMOVITZ"),
                    arguments("Tomasz", "tom"),
                    arguments("Zach", "Zacharia"),
                    // Identical names short-circuit to a match.
                    arguments("John", "John"));
        }

        /** Pairs of names that the algorithm does not consider homophonous. */
        Stream<Arguments> nonMatchingNames() {
            return Stream.of(
                    arguments("Sean", "Pete"),
                    // Disappointing: these ought to match phonetically, but the algorithm says no.
                    arguments("Úna", "Oonagh"),
                    arguments("Karl", "Alessandro"),
                    arguments("Moriarty", "OMuircheartaigh"),
                    arguments("Murphy", "Lynch"),
                    // Names too short or whose codes differ by 3+ in length never match.
                    arguments("Al", "Ed"),
                    arguments("Karl", "C"),
                    // Trivial / empty / null inputs are rejected before any comparison.
                    arguments(null, null),
                    arguments("Murphy", ""),
                    arguments("Murphy", " "),
                    arguments(null, " "),
                    arguments("t", "test"),
                    arguments(" ", "test"),
                    arguments("", "test"),
                    arguments(null, "test"),
                    arguments("test", " "),
                    arguments("test", ""),
                    arguments("test", null),
                    arguments("test", "t"));
        }

        @ParameterizedTest(name = "isEncodeEquals(\"{0}\", \"{1}\") is true")
        @MethodSource("matchingNames")
        void treatsHomophonousNamesAsEqual(final String name1, final String name2) {
            assertTrue(getStringEncoder().isEncodeEquals(name1, name2));
        }

        @ParameterizedTest(name = "isEncodeEquals(\"{0}\", \"{1}\") is false")
        @MethodSource("nonMatchingNames")
        void treatsDifferentNamesAsUnequal(final String name1, final String name2) {
            assertFalse(getStringEncoder().isEncodeEquals(name1, name2));
        }

        @Test
        void ignoresLeadingAndTrailingWhitespaceAroundNames() {
            // Sanity check without whitespace.
            assertTrue(getStringEncoder().isEncodeEquals("Brian", "Bryan"));
            // The same pair still matches with whitespace added to either side of either name.
            assertTrue(getStringEncoder().isEncodeEquals(" Brian", "Bryan"));
            assertTrue(getStringEncoder().isEncodeEquals("Brian ", "Bryan"));
            assertTrue(getStringEncoder().isEncodeEquals(" Brian ", "Bryan"));
            assertTrue(getStringEncoder().isEncodeEquals("Brian", " Bryan"));
            assertTrue(getStringEncoder().isEncodeEquals("Brian", "Bryan "));
            assertTrue(getStringEncoder().isEncodeEquals("Brian", " Bryan "));
        }
    }
}
