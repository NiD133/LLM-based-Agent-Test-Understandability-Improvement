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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JaroWinklerSimilarity}.
 */
class JaroWinklerSimilarityTest {

    /** Acceptable floating-point delta for similarity score comparisons. */
    private static final double TOLERANCE = 0.00001d;

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    /**
     * Wraps the string in a custom {@link CharSequence} whose {@link Object#equals(Object)}
     * deliberately does NOT match a plain {@link String}, ensuring the implementation handles
     * arbitrary {@link CharSequence} types rather than relying on {@code String.equals}.
     *
     * @param string the string to wrap
     * @return a {@link CharSequence} backed by {@code string}
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

    // -----------------------------------------------------------------------
    // Null-argument rejection tests
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("apply(null, SimilarityInput) throws IllegalArgumentException")
    void testApply_NullSimilarityInput() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, new SimilarityCharacterInput("a")));
    }

    @Test
    @DisplayName("apply(SimilarityInput, null) throws IllegalArgumentException")
    void testApply_SimilarityInputNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(new SimilarityCharacterInput("a"), null));
    }

    @Test
    @DisplayName("apply((String) null, null) throws IllegalArgumentException")
    void testGetJaroWinklerSimilarity_NullNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply((String) null, null));
    }

    @Test
    @DisplayName("apply(null, String) throws IllegalArgumentException")
    void testGetJaroWinklerSimilarity_NullString() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, "clear"));
    }

    @Test
    @DisplayName("apply(String, null) throws IllegalArgumentException")
    void testGetJaroWinklerSimilarity_StringNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(" ", null));
    }

    // -----------------------------------------------------------------------
    // Parameterized test covering multiple SimilarityInput implementations
    // -----------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputsEquals()")
    @DisplayName("Jaro-Winkler similarity scores are consistent across SimilarityInput implementations")
    void testGetJaroWinklerSimilarity(final Class<?> cls) {
        assertEquals(1d,       similarity.apply(SimilarityInputTest.build(cls, ""),    SimilarityInputTest.build(cls, "")),       TOLERANCE);
        assertEquals(1d,       similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo")),    TOLERANCE);
        assertEquals(0.94166d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo ")),   TOLERANCE);
        assertEquals(0.90666d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "foo  ")),  TOLERANCE);
        assertEquals(0.86666d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, " foo ")),  TOLERANCE);
        assertEquals(0.51111d, similarity.apply(SimilarityInputTest.build(cls, "foo"), SimilarityInputTest.build(cls, "  foo")),  TOLERANCE);
        assertEquals(0.92499d, similarity.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")),   TOLERANCE);
        assertEquals(0.0d,     similarity.apply(SimilarityInputTest.build(cls, "fly"),  SimilarityInputTest.build(cls, "ant")),   0.00000000000000000001d);
        assertEquals(0.44166d, similarity.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")), TOLERANCE);
        assertEquals(0.90666d, similarity.apply(SimilarityInputTest.build(cls, "ABC Corporation"),             SimilarityInputTest.build(cls, "ABC Corp")),            TOLERANCE);
        assertEquals(0.95251d, similarity.apply(SimilarityInputTest.build(cls, "D N H Enterprises Inc"),       SimilarityInputTest.build(cls, "D & H Enterprises, Inc.")), TOLERANCE);
        assertEquals(0.942d,   similarity.apply(SimilarityInputTest.build(cls, "My Gym Children's Fitness Center"), SimilarityInputTest.build(cls, "My Gym. Childrens Fitness")), TOLERANCE);
        assertEquals(0.898018d, similarity.apply(SimilarityInputTest.build(cls, "PENNSYLVANIA"),  SimilarityInputTest.build(cls, "PENNCISYLVNIA")),  TOLERANCE);
        assertEquals(0.971428d, similarity.apply(SimilarityInputTest.build(cls, "/opt/software1"), SimilarityInputTest.build(cls, "/opt/software2")), TOLERANCE);
        assertEquals(0.941666d, similarity.apply(SimilarityInputTest.build(cls, "aaabcd"),         SimilarityInputTest.build(cls, "aaacdb")),         TOLERANCE);
        assertEquals(0.911111d, similarity.apply(SimilarityInputTest.build(cls, "John Horn"),      SimilarityInputTest.build(cls, "John Hopkins")),   TOLERANCE);
    }

    // -----------------------------------------------------------------------
    // CharSequence (wrapped) vs String tests — split by scenario
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Identical strings (empty and non-empty) yield similarity 1.0")
    void testGetJaroWinklerSimilarity_IdenticalStrings() {
        assertEquals(1d, similarity.apply(wrap(""),    ""),    TOLERANCE);
        assertEquals(1d, similarity.apply(wrap("foo"), "foo"), TOLERANCE);
    }

    @Test
    @DisplayName("Strings that differ only by trailing spaces have similarity close to 1.0")
    void testGetJaroWinklerSimilarity_TrailingSpaces() {
        assertEquals(0.94166d, similarity.apply(wrap("foo"), "foo "),  TOLERANCE);
        assertEquals(0.90666d, similarity.apply(wrap("foo"), "foo  "), TOLERANCE);
    }

    @Test
    @DisplayName("Strings with leading or surrounding spaces have noticeably lower similarity")
    void testGetJaroWinklerSimilarity_LeadingOrSurroundingSpaces() {
        assertEquals(0.86666d, similarity.apply(wrap("foo"), " foo "), TOLERANCE);
        assertEquals(0.51111d, similarity.apply(wrap("foo"), "  foo"), TOLERANCE);
    }

    @Test
    @DisplayName("Strings sharing most characters with one deletion yield high similarity")
    void testGetJaroWinklerSimilarity_OneCharacterDeleted() {
        assertEquals(0.92499d, similarity.apply(wrap("frog"), "fog"), TOLERANCE);
    }

    @Test
    @DisplayName("Completely unrelated strings yield similarity 0.0")
    void testGetJaroWinklerSimilarity_CompletelyDifferentStrings() {
        assertEquals(0.0d, similarity.apply(wrap("fly"), "ant"), 0.00000000000000000001d);
    }

    @Test
    @DisplayName("Strings with few shared characters yield low but non-zero similarity")
    void testGetJaroWinklerSimilarity_LowSimilarity() {
        assertEquals(0.44166d, similarity.apply(wrap("elephant"), "hippo"), TOLERANCE);
    }

    @Test
    @DisplayName("Company name abbreviations and punctuation variants yield high similarity")
    void testGetJaroWinklerSimilarity_CompanyNames() {
        assertEquals(0.90666d, similarity.apply(wrap("ABC Corporation"),             "ABC Corp"),                      TOLERANCE);
        assertEquals(0.95251d, similarity.apply(wrap("D N H Enterprises Inc"),       "D & H Enterprises, Inc."),       TOLERANCE);
        assertEquals(0.942d,   similarity.apply(wrap("My Gym Children's Fitness Center"), "My Gym. Childrens Fitness"), TOLERANCE);
    }

    @Test
    @DisplayName("Real-world strings with minor typos or digit differences yield high similarity")
    void testGetJaroWinklerSimilarity_RealWorldStrings() {
        assertEquals(0.898018d, similarity.apply(wrap("PENNSYLVANIA"),   "PENNCISYLVNIA"),  TOLERANCE);
        assertEquals(0.971428d, similarity.apply(wrap("/opt/software1"), "/opt/software2"), TOLERANCE);
        assertEquals(0.941666d, similarity.apply(wrap("aaabcd"),         "aaacdb"),         TOLERANCE);
        assertEquals(0.911111d, similarity.apply(wrap("John Horn"),      "John Hopkins"),   TOLERANCE);
    }
}
