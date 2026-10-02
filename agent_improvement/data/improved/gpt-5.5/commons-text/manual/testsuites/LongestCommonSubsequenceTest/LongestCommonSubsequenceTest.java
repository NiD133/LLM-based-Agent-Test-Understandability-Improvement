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
import org.junit.jupiter.api.Test;

/**
 * Tests {@link LongestCommonSubsequence}.
 */
class LongestCommonSubsequenceTest {

    private static final SubsequenceCase[] SUBSEQUENCE_CASES = {
        subsequenceCase("", "", ""),
        subsequenceCase("", "left", ""),
        subsequenceCase("", "", "right"),
        subsequenceCase("", "l", "a"),
        subsequenceCase("", "left", "a"),
        subsequenceCase("fog", "frog", "fog"),
        subsequenceCase("", "fly", "ant"),
        subsequenceCase("h", "elephant", "hippo"),
        subsequenceCase("ABC Corp", "ABC Corporation", "ABC Corp"),
        subsequenceCase("D  H Enterprises Inc", "D N H Enterprises Inc", "D & H Enterprises, Inc."),
        subsequenceCase("My Gym Childrens Fitness", "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"),
        subsequenceCase("PENNSYLVNIA", "PENNSYLVANIA", "PENNCISYLVNIA"),
        subsequenceCase("t", "left", "right"),
        subsequenceCase("tttt", "leettteft", "ritttght"),
        subsequenceCase("the same string", "the same string", "the same string")
    };

    private static final LengthCase[] LENGTH_CASES = {
        lengthCase(0, "", ""),
        lengthCase(0, "left", ""),
        lengthCase(0, "", "right"),
        lengthCase(3, "frog", "fog"),
        lengthCase(0, "fly", "ant"),
        lengthCase(1, "elephant", "hippo"),
        lengthCase(8, "ABC Corporation", "ABC Corp"),
        lengthCase(20, "D N H Enterprises Inc", "D & H Enterprises, Inc."),
        lengthCase(24, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"),
        lengthCase(11, "PENNSYLVANIA", "PENNCISYLVNIA"),
        lengthCase(1, "left", "right"),
        lengthCase(4, "leettteft", "ritttght"),
        lengthCase(15, "the same string", "the same string")
    };

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    @SuppressWarnings("deprecation")
    void testGettingLogestCommonSubsequenceNullNull() {
        assertInvalidInput(() -> subject.logestCommonSubsequence(null, null));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testGettingLogestCommonSubsequenceNullString() {
        assertInvalidInput(() -> subject.logestCommonSubsequence(null, "right"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testGettingLogestCommonSubsequenceStringNull() {
        assertInvalidInput(() -> subject.logestCommonSubsequence(" ", null));
    }

    @Test
    void testGettingLongestCommonSubsequenceApplyNullNull() {
        assertInvalidInput(() -> subject.apply(null, null));
    }

    @Test
    void testGettingLongestCommonSubsequenceApplyNullString() {
        assertInvalidInput(() -> subject.apply(null, "right"));
    }

    @Test
    void testGettingLongestCommonSubsequenceApplyStringNull() {
        assertInvalidInput(() -> subject.apply(" ", null));
    }

    @Test
    void testGettingLongestCommonSubsequenceNullNull() {
        assertInvalidInput(() -> subject.longestCommonSubsequence(null, null));
    }

    @Test
    void testGettingLongestCommonSubsequenceNullString() {
        assertInvalidInput(() -> subject.longestCommonSubsequence(null, "right"));
    }

    @Test
    void testGettingLongestCommonSubsequenceStringNull() {
        assertInvalidInput(() -> subject.longestCommonSubsequence(" ", null));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testLogestCommonSubsequence() {
        for (final SubsequenceCase testCase : SUBSEQUENCE_CASES) {
            assertEquals(testCase.expected, subject.logestCommonSubsequence(testCase.left, testCase.right));
        }
    }

    @Test
    void testLongestCommonSubsequence() {
        for (final SubsequenceCase testCase : SUBSEQUENCE_CASES) {
            assertEquals(testCase.expected, subject.longestCommonSubsequence(testCase.left, testCase.right));
        }
    }

    @Test
    void testLongestCommonSubsequenceApply() {
        for (final LengthCase testCase : LENGTH_CASES) {
            assertEquals(testCase.expected, subject.apply(testCase.left, testCase.right));
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    void testLongestCommonSubstringLengthArray() {
        assertArrayEquals(new int[][] { {0, 0, 0, 0}, {0, 1, 1, 1}, {0, 1, 2, 2} },
            subject.longestCommonSubstringLengthArray("ab", "abc"));
    }

    private static void assertInvalidInput(final Executable executable) {
        assertThrows(IllegalArgumentException.class, executable);
    }

    private static SubsequenceCase subsequenceCase(final String expected, final String left, final String right) {
        return new SubsequenceCase(expected, left, right);
    }

    private static LengthCase lengthCase(final int expected, final String left, final String right) {
        return new LengthCase(expected, left, right);
    }

    private interface Executable extends org.junit.jupiter.api.function.Executable {
        // Gives null-input assertions a domain-specific name without changing JUnit behavior.
    }

    private static final class SubsequenceCase {
        private final String expected;
        private final String left;
        private final String right;

        private SubsequenceCase(final String expected, final String left, final String right) {
            this.expected = expected;
            this.left = left;
            this.right = right;
        }
    }

    private static final class LengthCase {
        private final int expected;
        private final String left;
        private final String right;

        private LengthCase(final int expected, final String left, final String right) {
            this.expected = expected;
            this.left = left;
            this.right = right;
        }
    }
}
