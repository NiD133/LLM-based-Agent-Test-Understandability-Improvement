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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JaroWinklerSimilarity}.
 */
class JaroWinklerSimilarityTest {

    /**
     * A single Jaro-Winkler example: the score expected for comparing {@link #left} with {@link #right}, together with
     * the floating-point tolerance to allow when checking it.
     */
    private static final class SimilarityCase {

        final String left;
        final String right;
        final double expectedScore;
        final double tolerance;

        SimilarityCase(final String left, final String right, final double expectedScore, final double tolerance) {
            this.left = left;
            this.right = right;
            this.expectedScore = expectedScore;
            this.tolerance = tolerance;
        }

        @Override
        public String toString() {
            return "apply(\"" + left + "\", \"" + right + "\") ~ " + expectedScore;
        }
    }

    /** Default tolerance for the (rounded) expected scores below. */
    private static final double DELTA = 0.00001d;

    /**
     * Tolerance for the {@code "fly"} vs. {@code "ant"} case: these share no characters, so the algorithm returns an
     * exact {@code 0.0} and we assert it with essentially no slack.
     */
    private static final double EXACT_ZERO_DELTA = 0.00000000000000000001d;

    /**
     * The expected Jaro-Winkler scores. Both {@link #testGetJaroWinklerSimilarity(Class)} and
     * {@link #testGetJaroWinklerSimilarity_StringString()} run these same cases; they differ only in how the inputs are
     * wrapped before being passed to {@link JaroWinklerSimilarity#apply}.
     */
    private static final SimilarityCase[] SIMILARITY_CASES = {
        new SimilarityCase("", "", 1d, DELTA),
        new SimilarityCase("foo", "foo", 1d, DELTA),
        new SimilarityCase("foo", "foo ", 0.94166d, DELTA),
        new SimilarityCase("foo", "foo  ", 0.90666d, DELTA),
        new SimilarityCase("foo", " foo ", 0.86666d, DELTA),
        new SimilarityCase("foo", "  foo", 0.51111d, DELTA),
        new SimilarityCase("frog", "fog", 0.92499d, DELTA),
        new SimilarityCase("fly", "ant", 0.0d, EXACT_ZERO_DELTA),
        new SimilarityCase("elephant", "hippo", 0.44166d, DELTA),
        new SimilarityCase("ABC Corporation", "ABC Corp", 0.90666d, DELTA),
        new SimilarityCase("D N H Enterprises Inc", "D & H Enterprises, Inc.", 0.95251d, DELTA),
        new SimilarityCase("My Gym Children's Fitness Center", "My Gym. Childrens Fitness", 0.942d, DELTA),
        new SimilarityCase("PENNSYLVANIA", "PENNCISYLVNIA", 0.898018d, DELTA),
        new SimilarityCase("/opt/software1", "/opt/software2", 0.971428d, DELTA),
        new SimilarityCase("aaabcd", "aaacdb", 0.941666d, DELTA),
        new SimilarityCase("John Horn", "John Hopkins", 0.911111d, DELTA),
    };

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    /**
     * Wraps the string in a custom {@link CharSequence}. This ensures that using the {@link Object#equals(Object)} method on the input CharSequence to test for
     * equality will fail.
     *
     * @param string the string
     * @return the char sequence
     */
    private static CharSequence wrap(final String string) {
        return new CharSequence() {

            @Override
            public char charAt(final int index) {
                return string.charAt(index);
            }

            @Override
            public boolean equals(final Object obj) {
                return string.equals(obj);
            }

            @Override
            public int hashCode() {
                return string.hashCode();
            }

            @Override
            public int length() {
                return string.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return string.subSequence(start, end);
            }

            @Override
            public String toString() {
                return string;
            }
        };
    }

    @Test
    void testApply_NullSimilarityInput() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, new SimilarityCharacterInput("a")));
    }

    @Test
    void testApply_SimilarityInputNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(new SimilarityCharacterInput("a"), null));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputsEquals()")
    void testGetJaroWinklerSimilarity(final Class<?> cls) {
        // Build each side of every case as the SimilarityInput implementation under test.
        for (final SimilarityCase testCase : SIMILARITY_CASES) {
            assertEquals(testCase.expectedScore,
                    similarity.apply(SimilarityInputTest.build(cls, testCase.left), SimilarityInputTest.build(cls, testCase.right)),
                    testCase.tolerance, testCase::toString);
        }
    }

    @Test
    void testGetJaroWinklerSimilarity_NullNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply((String) null, null));
    }

    @Test
    void testGetJaroWinklerSimilarity_NullString() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, "clear"));
    }

    @Test
    void testGetJaroWinklerSimilarity_StringNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(" ", null));
    }

    @Test
    void testGetJaroWinklerSimilarity_StringString() {
        // Compare a custom CharSequence (whose equals() differs from String's) against a plain String.
        for (final SimilarityCase testCase : SIMILARITY_CASES) {
            assertEquals(testCase.expectedScore,
                    similarity.apply(wrap(testCase.left), testCase.right),
                    testCase.tolerance, testCase::toString);
        }
    }
}
