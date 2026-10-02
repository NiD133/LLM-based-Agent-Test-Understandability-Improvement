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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link LevenshteinDistance}.
 *
 * <p>Tests are organised into three groups:
 * <ol>
 *   <li>Constructor and accessor validation</li>
 *   <li>Null-argument guards</li>
 *   <li>Distance computation — unlimited mode and threshold-limited mode</li>
 * </ol>
 */
@DisplayName("LevenshteinDistance")
class LevenshteinDistanceTest {

    /** Shared unlimited-distance instance (no threshold). */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    // -----------------------------------------------------------------------
    // Constructor and accessor
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Constructor rejects a negative threshold")
    void testConstructorWithNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(-1));
    }

    @Test
    @DisplayName("getDefaultInstance() exposes a null threshold (unlimited mode)")
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(LevenshteinDistance.getDefaultInstance().getThreshold());
    }

    // -----------------------------------------------------------------------
    // Null-argument guards
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("apply(SimilarityInput) throws IllegalArgumentException for null inputs")
    void testApplyThrowsIllegalArgumentExceptionSimilarityInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply(new SimilarityCharacterInput("asdf"),
                        (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply((SimilarityCharacterInput) null,
                        new SimilarityCharacterInput("asdf")));
    }

    @Test
    @DisplayName("apply(String) throws IllegalArgumentException when both arguments are null")
    void testApplyThrowsIllegalArgumentExceptionString() {
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply((String) null, (String) null));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply throws when the right (second) input is null")
    void testApplyThrowsOnNullRightInput(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "a"),
                        SimilarityInputTest.build(cls, null)));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply throws when the left (first) input is null")
    void testApplyThrowsOnNullLeftInput(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, null),
                        SimilarityInputTest.build(cls, "a")));
    }

    // -----------------------------------------------------------------------
    // Distance computation — unlimited mode (no threshold)
    // -----------------------------------------------------------------------

    /**
     * Verifies that the unlimited-distance instance produces correct edit distances
     * for a representative set of string pairs.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply computes correct Levenshtein distances in unlimited mode")
    void testUnlimitedDistanceComputesCorrectValues(final Class<?> cls) {
        assertEquals(0, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, "")));
        assertEquals(1, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, "a")));
        assertEquals(7, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "aaapppp"),
                SimilarityInputTest.build(cls, "")));
        assertEquals(1, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "frog"),
                SimilarityInputTest.build(cls, "fog")));
        assertEquals(3, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "fly"),
                SimilarityInputTest.build(cls, "ant")));
        assertEquals(7, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "elephant"),
                SimilarityInputTest.build(cls, "hippo")));
        assertEquals(7, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"),
                SimilarityInputTest.build(cls, "elephant")));
        assertEquals(8, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"),
                SimilarityInputTest.build(cls, "zzzzzzzz")));
        assertEquals(8, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "zzzzzzzz"),
                SimilarityInputTest.build(cls, "hippo")));
        assertEquals(1, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hello"),
                SimilarityInputTest.build(cls, "hallo")));
    }

    // -----------------------------------------------------------------------
    // Distance computation — threshold-limited mode
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("apply returns -1 when distance exceeds a zero threshold (empty vs non-empty)")
    void testGetLevenshteinDistance_EmptyStringString() {
        assertEquals(-1, new LevenshteinDistance(0).apply(
                new SimilarityCharacterInput(""),
                new SimilarityCharacterInput("asdf")));
    }

    /**
     * Exhaustively verifies threshold-limited behaviour across all supported
     * {@link SimilarityInput} implementations.
     *
     * <p>Cases are grouped by the relationship between the actual edit distance and
     * the configured threshold:
     * <ul>
     *   <li>Empty strings</li>
     *   <li>Unequal strings with a zero threshold</li>
     *   <li>Equal strings</li>
     *   <li>Same-length strings with different characters</li>
     *   <li>Distance less than / equal to / greater than the threshold</li>
     *   <li>Stripe overflow (threshold so small the algorithm exits early)</li>
     *   <li>Threshold set to {@link Integer#MAX_VALUE} (effectively unlimited)</li>
     * </ul>
     * A return value of {@code -1} signals that the distance exceeds the threshold.
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    @DisplayName("apply respects the threshold and returns -1 when distance exceeds it")
    void testGetLevenshteinDistance_WithThreshold(final Class<?> cls) {
        // empty strings: distance equals string length
        assertEquals(0,  new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, ""),        SimilarityInputTest.build(cls, "")));
        assertEquals(7,  new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));
        assertEquals(7,  new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")));

        // unequal strings with a zero threshold: any change pushes distance beyond threshold
        assertEquals(-1, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, "b"), SimilarityInputTest.build(cls, "a")));
        assertEquals(-1, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, "a"), SimilarityInputTest.build(cls, "b")));

        // equal strings: distance is always 0, even with a restrictive threshold
        assertEquals(0, new LevenshteinDistance(0).apply(SimilarityInputTest.build(cls, "aa"), SimilarityInputTest.build(cls, "aa")));
        assertEquals(0, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "aa"), SimilarityInputTest.build(cls, "aa")));

        // same length, fully different characters
        assertEquals(-1, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "aaa"), SimilarityInputTest.build(cls, "bbb")));
        assertEquals(3,  new LevenshteinDistance(3).apply(SimilarityInputTest.build(cls, "aaa"), SimilarityInputTest.build(cls, "bbb")));

        // threshold much larger than the actual distance (big stripe)
        assertEquals(6, new LevenshteinDistance(10).apply(SimilarityInputTest.build(cls, "aaaaaa"), SimilarityInputTest.build(cls, "b")));

        // distance less than threshold: exact distance is returned
        assertEquals(7, new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "b")));
        assertEquals(3, new LevenshteinDistance(4).apply(SimilarityInputTest.build(cls, "a"),       SimilarityInputTest.build(cls, "bbb")));

        // distance equal to threshold: boundary case — exact distance is returned
        assertEquals(7, new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "b")));
        assertEquals(3, new LevenshteinDistance(3).apply(SimilarityInputTest.build(cls, "a"),       SimilarityInputTest.build(cls, "bbb")));

        // distance greater than threshold: returns -1
        assertEquals(-1, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "a"),       SimilarityInputTest.build(cls, "bbb")));
        assertEquals(-1, new LevenshteinDistance(2).apply(SimilarityInputTest.build(cls, "bbb"),     SimilarityInputTest.build(cls, "a")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "b")));

        // stripe runs off the DP array — strings are dissimilar, threshold too tight
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "a"),   SimilarityInputTest.build(cls, "bbb")));
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "bbb"), SimilarityInputTest.build(cls, "a")));

        // stripe runs off the DP array — strings share a prefix but length difference exceeds threshold
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "12345"),   SimilarityInputTest.build(cls, "1234567")));
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "1234567"), SimilarityInputTest.build(cls, "12345")));

        // classic word-pair distances verified at their exact threshold
        assertEquals(1,  new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "frog"),     SimilarityInputTest.build(cls, "fog")));
        assertEquals(3,  new LevenshteinDistance(3).apply(SimilarityInputTest.build(cls, "fly"),      SimilarityInputTest.build(cls, "ant")));
        assertEquals(7,  new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(7,  new LevenshteinDistance(7).apply(SimilarityInputTest.build(cls, "hippo"),    SimilarityInputTest.build(cls, "elephant")));
        assertEquals(-1, new LevenshteinDistance(6).apply(SimilarityInputTest.build(cls, "hippo"),    SimilarityInputTest.build(cls, "elephant")));
        assertEquals(8,  new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "hippo"),    SimilarityInputTest.build(cls, "zzzzzzzz")));
        assertEquals(8,  new LevenshteinDistance(8).apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(1,  new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "hello"),    SimilarityInputTest.build(cls, "hallo")));

        // Integer.MAX_VALUE threshold acts as effectively unlimited — distances must match unlimited mode
        assertEquals(1, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "frog"),     SimilarityInputTest.build(cls, "fog")));
        assertEquals(3, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "fly"),      SimilarityInputTest.build(cls, "ant")));
        assertEquals(7, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(7, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "hippo"),    SimilarityInputTest.build(cls, "elephant")));
        assertEquals(8, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "hippo"),    SimilarityInputTest.build(cls, "zzzzzzzz")));
        assertEquals(8, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        assertEquals(1, new LevenshteinDistance(Integer.MAX_VALUE).apply(SimilarityInputTest.build(cls, "hello"),    SimilarityInputTest.build(cls, "hallo")));

        // transposition ("abc" → "acb") costs 2 edits — exceeds a threshold of 1
        assertEquals(-1, new LevenshteinDistance(1).apply(SimilarityInputTest.build(cls, "abc"), SimilarityInputTest.build(cls, "acb")));
    }
}
