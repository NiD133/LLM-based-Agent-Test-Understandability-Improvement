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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill}.
 *
 * <p>Every {@code ArrayFill} method follows the same fluent contract: it mutates the array in place
 * and returns the very same array instance so calls can be chained. These tests therefore verify two
 * things for each overload:</p>
 * <ol>
 *   <li>the returned reference is the same object as the input (checked with {@link assertSame}), and</li>
 *   <li>every targeted element holds the expected value.</li>
 * </ol>
 *
 * <p>A {@code null} array is always passed straight through unchanged, which the {@code *Null} tests confirm.</p>
 */
class ArrayFillTest extends AbstractLangTest {

    // -----------------------------------------------------------------------
    // clear(...) — fills the array with its "zero" value (0 / '\0').
    // -----------------------------------------------------------------------

    @Test
    void testClearByteArray() {
        final byte[] array = new byte[3];
        final byte[] result = ArrayFill.clear(array);
        // clear() must return the same instance and zero every element.
        assertSame(array, result);
        for (final byte element : result) {
            assertEquals((byte) 0, element);
        }
    }

    @Test
    void testClearByteArrayNull() {
        final byte[] array = null;
        // A null input is returned unchanged.
        assertSame(array, ArrayFill.clear(array));
    }

    @Test
    void testClearCharArray() {
        final char[] array = new char[3];
        final char[] result = ArrayFill.clear(array);
        // clear() fills with the NUL character '\0', not the digit '0'.
        assertSame(array, result);
        for (final char element : result) {
            assertEquals('\0', element);
        }
    }

    @Test
    void testClearCharArrayNull() {
        final char[] array = null;
        // A null input is returned unchanged.
        assertSame(array, ArrayFill.clear(array));
    }

    @Test
    void testClearCharArrayRange() {
        final char[] array = {'A', 'B', 'C', 'D', 'E'};
        // Clear indices 1 (inclusive) through 4 (exclusive); endpoints 'A' and 'E' stay put.
        final char[] result = ArrayFill.clear(array, 1, 4);
        assertSame(array, result);
        assertArrayEquals(new char[] {'A', '\0', '\0', '\0', 'E'}, result);
    }

    @Test
    void testClearCharArrayRangeNull() {
        // A null input yields null regardless of the index range.
        assertNull(ArrayFill.clear(null, 0, 0));
    }

    // -----------------------------------------------------------------------
    // fill(array, value) — fills the whole array with a single value.
    // -----------------------------------------------------------------------

    @Test
    void testFillBooleanArray() {
        final boolean[] array = new boolean[3];
        final boolean fillValue = true;
        final boolean[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final boolean element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillBooleanArrayNull() {
        final boolean[] array = null;
        assertSame(array, ArrayFill.fill(array, true));
    }

    @Test
    void testFillByteArray() {
        final byte[] array = new byte[3];
        final byte fillValue = 1;
        final byte[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final byte element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillByteArrayNull() {
        final byte[] array = null;
        assertSame(array, ArrayFill.fill(array, (byte) 1));
    }

    @Test
    void testFillCharArray() {
        final char[] array = new char[3];
        final char fillValue = 1;
        final char[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final char element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillCharArrayNull() {
        final char[] array = null;
        assertSame(array, ArrayFill.fill(array, (char) 1));
    }

    @Test
    void testFillCharArrayRange() {
        final char[] array = {'A', 'B', 'C', 'D', 'E'};
        // Fill indices 1 (inclusive) through 4 (exclusive) with 'Z'; endpoints stay put.
        final char[] result = ArrayFill.fill(array, 1, 4, 'Z');
        assertSame(array, result);
        assertArrayEquals(new char[] {'A', 'Z', 'Z', 'Z', 'E'}, result);
    }

    @Test
    void testFillCharArrayRangeEmpty() {
        final char[] array = {'A', 'B', 'C'};
        // An empty range (fromIndex == toIndex) leaves the array untouched.
        final char[] result = ArrayFill.fill(array, 1, 1, 'Z');
        assertSame(array, result);
        assertArrayEquals(new char[] {'A', 'B', 'C'}, result);
    }

    @Test
    void testFillCharArrayRangeNull() {
        // A null input yields null regardless of the index range.
        assertNull(ArrayFill.fill(null, 0, 0, 'Z'));
    }

    @Test
    void testFillDoubleArray() {
        final double[] array = new double[3];
        final double fillValue = 1;
        final double[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final double element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillDoubleArrayNull() {
        final double[] array = null;
        assertSame(array, ArrayFill.fill(array, 1d));
    }

    @Test
    void testFillFloatArray() {
        final float[] array = new float[3];
        final float fillValue = 1;
        final float[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final float element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillFloatArrayNull() {
        final float[] array = null;
        assertSame(array, ArrayFill.fill(array, 1f));
    }

    @Test
    void testFillIntArray() {
        final int[] array = new int[3];
        final int fillValue = 1;
        final int[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final int element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillIntArrayNull() {
        final int[] array = null;
        assertSame(array, ArrayFill.fill(array, 1));
    }

    @Test
    void testFillLongArray() {
        final long[] array = new long[3];
        final long fillValue = 1;
        final long[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final long element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillLongArrayNull() {
        final long[] array = null;
        assertSame(array, ArrayFill.fill(array, 1L));
    }

    @Test
    void testFillObjectArray() {
        final String[] array = new String[3];
        final String fillValue = "A";
        final String[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final String element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillObjectArrayNull() {
        final Object[] array = null;
        assertSame(array, ArrayFill.fill(array, (Object) 1));
    }

    @Test
    void testFillShortArray() {
        final short[] array = new short[3];
        final short fillValue = 1;
        final short[] result = ArrayFill.fill(array, fillValue);
        assertSame(array, result);
        for (final short element : result) {
            assertEquals(fillValue, element);
        }
    }

    @Test
    void testFillShortArrayNull() {
        final short[] array = null;
        assertSame(array, ArrayFill.fill(array, (short) 1));
    }

    // -----------------------------------------------------------------------
    // fill(array, generator) — computes each element from its index.
    // -----------------------------------------------------------------------

    @Test
    void testFillFunction() throws Exception {
        final FailableIntFunction<?, Exception> nullIntFunction = null;
        // A null array (with a null generator) is passed straight through as null.
        assertNull(ArrayFill.fill(null, nullIntFunction));
        assertArrayEquals(null, ArrayFill.fill(null, nullIntFunction));
        // A null generator leaves a non-null (here empty) array unchanged.
        assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, ArrayFill.fill(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, nullIntFunction));
        assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, ArrayFill.fill(ArrayUtils.EMPTY_OBJECT_ARRAY, nullIntFunction));
        // With a real generator, each element becomes its own index: array[i] == i.
        final Integer[] array = new Integer[10];
        final Integer[] result = ArrayFill.fill(array, Integer::valueOf);
        assertSame(array, result);
        for (int i = 0; i < array.length; i++) {
            assertEquals(i, array[i].intValue());
        }
    }
}
