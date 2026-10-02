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

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link LongestCommonSubsequence}.
 */
class LongestCommonSubsequenceTest {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    /**
     * Pairs of input strings together with the longest common subsequence the algorithm should
     * return for them. This data set is shared by the tests that exercise the subsequence-returning
     * methods ({@link LongestCommonSubsequence#longestCommonSubsequence} and its deprecated
     * {@code logestCommonSubsequence} alias).
     *
     * @return arguments of {@code (left, right, expectedSubsequence)}.
     */
    static Stream<Arguments> subsequenceCases() {
        return Stream.of(
            Arguments.of("", "", ""),
            Arguments.of("left", "", ""),
            Arguments.of("", "right", ""),
            Arguments.of("l", "a", ""),
            Arguments.of("left", "a", ""),
            Arguments.of("frog", "fog", "fog"),
            Arguments.of("fly", "ant", ""),
            Arguments.of("elephant", "hippo", "h"),
            Arguments.of("ABC Corporation", "ABC Corp", "ABC Corp"),
            Arguments.of("D N H Enterprises Inc", "D & H Enterprises, Inc.", "D  H Enterprises Inc"),
            Arguments.of("My Gym Children's Fitness Center", "My Gym. Childrens Fitness", "My Gym Childrens Fitness"),
            Arguments.of("PENNSYLVANIA", "PENNCISYLVNIA", "PENNSYLVNIA"),
            Arguments.of("left", "right", "t"),
            Arguments.of("leettteft", "ritttght", "tttt"),
            Arguments.of("the same string", "the same string", "the same string"));
    }

    /**
     * Pairs of input strings together with the length of their longest common subsequence, as
     * returned by {@link LongestCommonSubsequence#apply}.
     *
     * @return arguments of {@code (left, right, expectedLength)}.
     */
    static Stream<Arguments> subsequenceLengthCases() {
        return Stream.of(
            Arguments.of("", "", 0),
            Arguments.of("left", "", 0),
            Arguments.of("", "right", 0),
            Arguments.of("frog", "fog", 3),
            Arguments.of("fly", "ant", 0),
            Arguments.of("elephant", "hippo", 1),
            Arguments.of("ABC Corporation", "ABC Corp", 8),
            Arguments.of("D N H Enterprises Inc", "D & H Enterprises, Inc.", 20),
            Arguments.of("My Gym Children's Fitness Center", "My Gym. Childrens Fitness", 24),
            Arguments.of("PENNSYLVANIA", "PENNCISYLVNIA", 11),
            Arguments.of("left", "right", 1),
            Arguments.of("leettteft", "ritttght", 4),
            Arguments.of("the same string", "the same string", 15));
    }

    // ------------------------------------------------------------------
    // Null inputs must be rejected by every public entry point.
    // ------------------------------------------------------------------

    @Test
    @SuppressWarnings("deprecation")
    void logestCommonSubsequenceRejectsNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(null, null));
        assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(null, "right"));
        assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(" ", null));
    }

    @Test
    void longestCommonSubsequenceRejectsNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(null, null));
        assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(null, "right"));
        assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(" ", null));
    }

    @Test
    void applyRejectsNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> subject.apply(null, null));
        assertThrows(IllegalArgumentException.class, () -> subject.apply(null, "right"));
        assertThrows(IllegalArgumentException.class, () -> subject.apply(" ", null));
    }

    // ------------------------------------------------------------------
    // Computing the actual longest common subsequence.
    // ------------------------------------------------------------------

    @ParameterizedTest(name = "logestCommonSubsequence(\"{0}\", \"{1}\") = \"{2}\"")
    @MethodSource("subsequenceCases")
    @SuppressWarnings("deprecation")
    void logestCommonSubsequenceReturnsExpectedSubsequence(final String left, final String right, final String expected) {
        assertEquals(expected, subject.logestCommonSubsequence(left, right));
    }

    @ParameterizedTest(name = "longestCommonSubsequence(\"{0}\", \"{1}\") = \"{2}\"")
    @MethodSource("subsequenceCases")
    void longestCommonSubsequenceReturnsExpectedSubsequence(final String left, final String right, final String expected) {
        assertEquals(expected, subject.longestCommonSubsequence(left, right));
    }

    // ------------------------------------------------------------------
    // Computing the length of the longest common subsequence.
    // ------------------------------------------------------------------

    @ParameterizedTest(name = "apply(\"{0}\", \"{1}\") = {2}")
    @MethodSource("subsequenceLengthCases")
    void applyReturnsExpectedSubsequenceLength(final String left, final String right, final int expected) {
        assertEquals(expected, subject.apply(left, right));
    }

    // ------------------------------------------------------------------
    // Deprecated dynamic-programming length table.
    // ------------------------------------------------------------------

    @Test
    @SuppressWarnings("deprecation")
    void longestCommonSubstringLengthArrayBuildsDpTable() {
        final int[][] expectedDpTable = {
            {0, 0, 0, 0},
            {0, 1, 1, 1},
            {0, 1, 2, 2}};
        assertArrayEquals(expectedDpTable, subject.longestCommonSubstringLengthArray("ab", "abc"));
    }

}
