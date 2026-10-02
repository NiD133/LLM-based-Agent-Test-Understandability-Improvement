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

    private static final double DEFAULT_DELTA = 0.00001d;
    private static final double EXACT_ZERO_DELTA = 0.00000000000000000001d;

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    private static void assertSimilarity(final double expected, final CharSequence left, final CharSequence right, final double delta) {
        assertEquals(expected, similarity.apply(left, right), delta);
    }

    private static void assertSimilarity(final double expected, final Class<?> inputClass, final String left, final String right, final double delta) {
        assertEquals(expected, similarity.apply(SimilarityInputTest.build(inputClass, left), SimilarityInputTest.build(inputClass, right)), delta);
    }

    private static void assertWrappedStringSimilarity(final double expected, final String left, final String right, final double delta) {
        assertSimilarity(expected, wrap(left), right, delta);
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
        assertSimilarity(1d, cls, "", "", DEFAULT_DELTA);
        assertSimilarity(1d, cls, "foo", "foo", DEFAULT_DELTA);
        assertSimilarity(0.94166d, cls, "foo", "foo ", DEFAULT_DELTA);
        assertSimilarity(0.90666d, cls, "foo", "foo  ", DEFAULT_DELTA);
        assertSimilarity(0.86666d, cls, "foo", " foo ", DEFAULT_DELTA);
        assertSimilarity(0.51111d, cls, "foo", "  foo", DEFAULT_DELTA);
        assertSimilarity(0.92499d, cls, "frog", "fog", DEFAULT_DELTA);
        assertSimilarity(0.0d, cls, "fly", "ant", EXACT_ZERO_DELTA);
        assertSimilarity(0.44166d, cls, "elephant", "hippo", DEFAULT_DELTA);
        assertSimilarity(0.90666d, cls, "ABC Corporation", "ABC Corp", DEFAULT_DELTA);
        assertSimilarity(0.95251d, cls, "D N H Enterprises Inc", "D & H Enterprises, Inc.", DEFAULT_DELTA);
        assertSimilarity(0.942d, cls, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness", DEFAULT_DELTA);
        assertSimilarity(0.898018d, cls, "PENNSYLVANIA", "PENNCISYLVNIA", DEFAULT_DELTA);
        assertSimilarity(0.971428d, cls, "/opt/software1", "/opt/software2", DEFAULT_DELTA);
        assertSimilarity(0.941666d, cls, "aaabcd", "aaacdb", DEFAULT_DELTA);
        assertSimilarity(0.911111d, cls, "John Horn", "John Hopkins", DEFAULT_DELTA);
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
        assertWrappedStringSimilarity(1d, "", "", DEFAULT_DELTA);
        assertWrappedStringSimilarity(1d, "foo", "foo", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.94166d, "foo", "foo ", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.90666d, "foo", "foo  ", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.86666d, "foo", " foo ", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.51111d, "foo", "  foo", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.92499d, "frog", "fog", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.0d, "fly", "ant", EXACT_ZERO_DELTA);
        assertWrappedStringSimilarity(0.44166d, "elephant", "hippo", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.90666d, "ABC Corporation", "ABC Corp", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.95251d, "D N H Enterprises Inc", "D & H Enterprises, Inc.", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.942d, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.898018d, "PENNSYLVANIA", "PENNCISYLVNIA", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.971428d, "/opt/software1", "/opt/software2", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.941666d, "aaabcd", "aaacdb", DEFAULT_DELTA);
        assertWrappedStringSimilarity(0.911111d, "John Horn", "John Hopkins", DEFAULT_DELTA);
    }
}
