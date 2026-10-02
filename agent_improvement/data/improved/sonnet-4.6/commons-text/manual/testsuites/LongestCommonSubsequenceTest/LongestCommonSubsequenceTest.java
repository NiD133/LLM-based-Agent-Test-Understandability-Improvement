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
package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests {@link LongestCommonSubsequence}.
 *
 * <p>Tests are grouped by the method under test:</p>
 * <ul>
 *   <li>{@link ApplyTests} — {@code apply()}, which returns the LCS length as an integer.</li>
 *   <li>{@link LongestCommonSubsequenceStringTests} — {@code longestCommonSubsequence()}, which returns the LCS string.</li>
 *   <li>{@link LogestCommonSubsequenceDeprecatedTests} — {@code logestCommonSubsequence()}, the deprecated alias (typo) for the above.</li>
 *   <li>{@link LongestCommonSubstringLengthArrayTests} — {@code longestCommonSubstringLengthArray()}, the deprecated DP-table builder.</li>
 * </ul>
 */
@DisplayName("LongestCommonSubsequence")
class LongestCommonSubsequenceTest {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    // -------------------------------------------------------------------------
    // apply() — returns LCS length as an integer
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("apply() — returns LCS length as an integer")
    class ApplyTests {

        @Test
        @DisplayName("throws IllegalArgumentException when both inputs are null")
        void testGettingLongestCommonSubsequenceApplyNullNull() {
            assertThrows(IllegalArgumentException.class, () -> subject.apply(null, null));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when left input is null")
        void testGettingLongestCommonSubsequenceApplyNullString() {
            assertThrows(IllegalArgumentException.class, () -> subject.apply(null, "right"));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when right input is null")
        void testGettingLongestCommonSubsequenceApplyStringNull() {
            assertThrows(IllegalArgumentException.class, () -> subject.apply(" ", null));
        }

        /**
         * Verifies that {@code apply(left, right)} returns the expected LCS length.
         *
         * <p>Test cases cover: empty strings, completely different strings, partial overlaps,
         * subsequences with gaps, and identical strings.</p>
         */
        @ParameterizedTest(name = "[{index}] apply(\"{0}\", \"{1}\") = {2}")
        @CsvSource({
            // empty inputs always yield length 0
            "'',         '',                              0",
            "left,       '',                              0",
            "'',         right,                           0",
            // no characters in common
            "fly,        ant,                             0",
            // single shared character
            "elephant,   hippo,                           1",
            "left,       right,                           1",
            // subsequence with gaps
            "frog,       fog,                             3",
            "leettteft,  ritttght,                        4",
            "PENNSYLVANIA, PENNCISYLVNIA,                 11",
            // longer real-world strings
            "'ABC Corporation',               'ABC Corp',                          8",
            "'D N H Enterprises Inc',         'D & H Enterprises, Inc.',           20",
            "'My Gym Children''s Fitness Center', 'My Gym. Childrens Fitness',     24",
            // identical strings: LCS length equals string length
            "'the same string', 'the same string', 15"
        })
        void testLongestCommonSubsequenceApply(final String left, final String right, final int expectedLength) {
            assertEquals(expectedLength, subject.apply(left, right));
        }
    }

    // -------------------------------------------------------------------------
    // longestCommonSubsequence() — returns the LCS as a CharSequence
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("longestCommonSubsequence() — returns the LCS as a CharSequence")
    class LongestCommonSubsequenceStringTests {

        @Test
        @DisplayName("throws IllegalArgumentException when both inputs are null")
        void testGettingLongestCommonSubsequenceNullNull() {
            assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(null, null));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when left input is null")
        void testGettingLongestCommonSubsequenceNullString() {
            assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(null, "right"));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when right input is null")
        void testGettingLongestCommonSubsequenceStringNull() {
            assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(" ", null));
        }

        /**
         * Verifies that {@code longestCommonSubsequence(left, right)} returns the expected LCS string.
         *
         * <p>Test cases cover: empty strings, single characters, partial overlaps,
         * subsequences spanning gaps, strings with punctuation, and identical strings.</p>
         */
        @ParameterizedTest(name = "[{index}] longestCommonSubsequence(\"{0}\", \"{1}\") = \"{2}\"")
        @CsvSource({
            // empty inputs yield an empty LCS
            "'',    '',     ''",
            "left,  '',     ''",
            "'',    right,  ''",
            // single characters that do not match
            "l,     a,      ''",
            "left,  a,      ''",
            // no characters in common at all
            "fly,   ant,    ''",
            // single shared character
            "elephant, hippo, h",
            "left,  right,  t",
            // subsequence with gaps
            "frog,  fog,    fog",
            "leettteft, ritttght, tttt",
            "PENNSYLVANIA, PENNCISYLVNIA, PENNSYLVNIA",
            // longer real-world company name strings
            "'ABC Corporation',               'ABC Corp',                         'ABC Corp'",
            "'D N H Enterprises Inc',         'D & H Enterprises, Inc.',          'D  H Enterprises Inc'",
            "'My Gym Children''s Fitness Center', 'My Gym. Childrens Fitness',    'My Gym Childrens Fitness'",
            // identical strings: LCS equals the string itself
            "'the same string', 'the same string', 'the same string'"
        })
        void testLongestCommonSubsequence(final String left, final String right, final String expectedLcs) {
            assertEquals(expectedLcs, subject.longestCommonSubsequence(left, right));
        }
    }

    // -------------------------------------------------------------------------
    // logestCommonSubsequence() — deprecated alias (contains a typo in its name)
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("logestCommonSubsequence() — deprecated alias for longestCommonSubsequence()")
    @SuppressWarnings("deprecation")
    class LogestCommonSubsequenceDeprecatedTests {

        @Test
        @DisplayName("throws IllegalArgumentException when both inputs are null")
        void testGettingLogestCommonSubsequenceNullNull() {
            assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(null, null));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when left input is null")
        void testGettingLogestCommonSubsequenceNullString() {
            assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(null, "right"));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when right input is null")
        void testGettingLogestCommonSubsequenceStringNull() {
            assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(" ", null));
        }

        /**
         * Verifies that the deprecated {@code logestCommonSubsequence()} delegates correctly
         * to {@code longestCommonSubsequence()} and returns the same results.
         */
        @ParameterizedTest(name = "[{index}] logestCommonSubsequence(\"{0}\", \"{1}\") = \"{2}\"")
        @CsvSource({
            "'',    '',     ''",
            "left,  '',     ''",
            "'',    right,  ''",
            "l,     a,      ''",
            "left,  a,      ''",
            "fly,   ant,    ''",
            "elephant, hippo, h",
            "left,  right,  t",
            "frog,  fog,    fog",
            "leettteft, ritttght, tttt",
            "PENNSYLVANIA, PENNCISYLVNIA, PENNSYLVNIA",
            "'ABC Corporation',               'ABC Corp',                         'ABC Corp'",
            "'D N H Enterprises Inc',         'D & H Enterprises, Inc.',          'D  H Enterprises Inc'",
            "'My Gym Children''s Fitness Center', 'My Gym. Childrens Fitness',    'My Gym Childrens Fitness'",
            "'the same string', 'the same string', 'the same string'"
        })
        void testLogestCommonSubsequence(final String left, final String right, final String expectedLcs) {
            assertEquals(expectedLcs, subject.logestCommonSubsequence(left, right));
        }
    }

    // -------------------------------------------------------------------------
    // longestCommonSubstringLengthArray() — deprecated DP-table builder
    // -------------------------------------------------------------------------

    @Nested
    @DisplayName("longestCommonSubstringLengthArray() — deprecated DP-table builder")
    class LongestCommonSubstringLengthArrayTests {

        /**
         * For inputs "ab" (length 2) and "abc" (length 3) the expected (3 x 4) DP table is:
         * <pre>
         *       ""  a   b   c
         *   ""  [0, 0,  0,  0]
         *   a   [0, 1,  1,  1]
         *   b   [0, 1,  2,  2]
         * </pre>
         */
        @Test
        @SuppressWarnings("deprecation")
        @DisplayName("returns the correct DP table for inputs \"ab\" and \"abc\"")
        void testLongestCommonSubstringLengthArray() {
            assertArrayEquals(
                new int[][]{ {0, 0, 0, 0}, {0, 1, 1, 1}, {0, 1, 2, 2} },
                subject.longestCommonSubstringLengthArray("ab", "abc")
            );
        }
    }
}
