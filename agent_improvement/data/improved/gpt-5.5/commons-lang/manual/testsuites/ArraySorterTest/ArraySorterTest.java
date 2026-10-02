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
 */
class ArraySorterTest extends AbstractLangTest {

    @Test
    void testSortByteArray() {
        final byte[] expectedSortedArray = {2, 1};
        final byte[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((byte[]) null));
    }

    @Test
    void testSortCharArray() {
        final char[] expectedSortedArray = {2, 1};
        final char[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((char[]) null));
    }

    @Test
    void testSortComparable() {
        final String[] expectedSortedArray = ArrayUtils.toArray("foo", "bar");
        final String[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort, String::compareTo));
        assertNull(ArraySorter.sort((String[]) null));
    }

    @Test
    void testSortDoubleArray() {
        final double[] expectedSortedArray = {2, 1};
        final double[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((double[]) null));
    }

    @Test
    void testSortFloatArray() {
        final float[] expectedSortedArray = {2, 1};
        final float[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((float[]) null));
    }

    @Test
    void testSortIntArray() {
        final int[] expectedSortedArray = {2, 1};
        final int[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((int[]) null));
    }

    @Test
    void testSortLongArray() {
        final long[] expectedSortedArray = {2, 1};
        final long[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((long[]) null));
    }

    @Test
    void testSortObjects() {
        final String[] expectedSortedArray = ArrayUtils.toArray("foo", "bar");
        final String[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((String[]) null));
    }

    @Test
    void testSortShortArray() {
        final short[] expectedSortedArray = {2, 1};
        final short[] arrayToSort = expectedSortedArray.clone();
        Arrays.sort(expectedSortedArray);
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((short[]) null));
    }

}
