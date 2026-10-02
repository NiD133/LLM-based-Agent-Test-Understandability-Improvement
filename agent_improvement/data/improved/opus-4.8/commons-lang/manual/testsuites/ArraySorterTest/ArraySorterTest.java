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

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter}.
 *
 * <p>Each test follows the same shape: take an unsorted array, build the
 * expected result by sorting an independent copy with {@link Arrays}, then
 * assert that {@link ArraySorter#sort} produces that same ordering. A second
 * assertion confirms that passing {@code null} returns {@code null}.</p>
 */
class ArraySorterTest extends AbstractLangTest {

    @Test
    void testSortByteArray() {
        final byte[] unsorted = {2, 1};
        final byte[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((byte[]) null));
    }

    @Test
    void testSortCharArray() {
        final char[] unsorted = {2, 1};
        final char[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((char[]) null));
    }

    @Test
    void testSortComparable() {
        final String[] unsorted = ArrayUtils.toArray("foo", "bar");
        final String[] expected = unsorted.clone();
        Arrays.sort(expected);

        // Sort using an explicit comparator (the natural String ordering).
        assertArrayEquals(expected, ArraySorter.sort(unsorted, String::compareTo));
        assertNull(ArraySorter.sort((String[]) null));
    }

    @Test
    void testSortDoubleArray() {
        final double[] unsorted = {2, 1};
        final double[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((double[]) null));
    }

    @Test
    void testSortFloatArray() {
        final float[] unsorted = {2, 1};
        final float[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((float[]) null));
    }

    @Test
    void testSortIntArray() {
        final int[] unsorted = {2, 1};
        final int[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((int[]) null));
    }

    @Test
    void testSortLongArray() {
        final long[] unsorted = {2, 1};
        final long[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((long[]) null));
    }

    @Test
    void testSortObjects() {
        final String[] unsorted = ArrayUtils.toArray("foo", "bar");
        final String[] expected = unsorted.clone();
        Arrays.sort(expected);

        // Sort using the elements' natural ordering (no comparator).
        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((String[]) null));
    }

    @Test
    void testSortShortArray() {
        final short[] unsorted = {2, 1};
        final short[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));
        assertNull(ArraySorter.sort((short[]) null));
    }

}
