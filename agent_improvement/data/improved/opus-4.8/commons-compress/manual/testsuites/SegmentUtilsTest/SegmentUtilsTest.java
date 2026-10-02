/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link SegmentUtils}, which counts method arguments from JVM method
 * descriptors and counts how many flag values satisfy a given {@link IMatcher}.
 */
class SegmentUtilsTest {

    /**
     * An {@link IMatcher} that matches every value that is an exact multiple of
     * {@code divisor} (i.e. divisible with no remainder).
     */
    private static final class MultipleMatches implements IMatcher {

        private final int divisor;

        MultipleMatches(final int divisor) {
            this.divisor = divisor;
        }

        @Override
        public boolean matches(final long value) {
            return value % divisor == 0;
        }

    }

    /** Matches even numbers (multiples of 2). */
    private static final IMatcher matchesMultiplesOfTwo = new MultipleMatches(2);

    /** Matches multiples of 5. */
    private static final IMatcher matchesMultiplesOfFive = new MultipleMatches(5);

    /**
     * Method descriptors paired with the number of arguments they declare when
     * each long/double counts as 1. Used by {@link #testCountArgs}.
     */
    static Stream<Arguments> countArgs() {
        return Stream.of(
                Arguments.of("()V", 0),                    // no arguments
                Arguments.of("(D)V", 1),                   // single double
                Arguments.of("([D)V", 1),                  // single double array
                Arguments.of("([[D)V", 1),                 // single two-dimensional double array
                Arguments.of("(DD)V", 2),                  // two doubles
                Arguments.of("(DDD)V", 3),                 // three doubles
                Arguments.of("(Lblah/blah;D)V", 2),        // object reference + double
                Arguments.of("(Lblah/blah;DLbLah;)V", 3)); // object + double + object
    }

    /**
     * Method descriptors paired with the number of arguments they declare for an
     * invokeinterface call, where each long/double counts as 2. Used by
     * {@link #testCountInvokeInterfaceArgs}.
     */
    static Stream<Arguments> countInvokeInterfaceArgs() {
        return Stream.of(
                Arguments.of("(Z)V", 1),                    // single boolean
                Arguments.of("(D)V", 2),                    // single double counts as 2
                Arguments.of("(J)V", 2),                    // single long counts as 2
                Arguments.of("([D)V", 1),                   // double array counts as 1
                Arguments.of("([[D)V", 1),                  // two-dimensional double array counts as 1
                Arguments.of("(DD)V", 4),                   // two doubles count as 2 each
                Arguments.of("(Lblah/blah;D)V", 3),         // object (1) + double (2)
                Arguments.of("(Lblah/blah;DLbLah;)V", 4),   // object + double (2) + object
                Arguments.of("([Lblah/blah;DLbLah;)V", 4)); // object array + double (2) + object
    }

    @ParameterizedTest(name = "countArgs(\"{0}\") == {1}")
    @MethodSource("countArgs")
    void testCountArgs(final String descriptor, final int expectedArgsCount) {
        assertEquals(expectedArgsCount, SegmentUtils.countArgs(descriptor));
    }

    @ParameterizedTest(name = "countInvokeInterfaceArgs(\"{0}\") == {1}")
    @MethodSource("countInvokeInterfaceArgs")
    void testCountInvokeInterfaceArgs(final String descriptor, final int expectedArgsCount) {
        assertEquals(expectedArgsCount, SegmentUtils.countInvokeInterfaceArgs(descriptor));
    }

    @Test
    void testCountMatches() {
        final long[] oneToTen = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        final long[] fiveSixSeven = { 5, 6, 7 };

        // Even values in oneToTen: 2,4,6,8,10 (5) plus 6 from fiveSixSeven (1) = 6.
        assertEquals(6, SegmentUtils.countMatches(new long[][] { oneToTen, fiveSixSeven }, matchesMultiplesOfTwo));
        // Even values in oneToTen only: 2,4,6,8,10 = 5.
        assertEquals(5, SegmentUtils.countMatches(new long[][] { oneToTen }, matchesMultiplesOfTwo));
        // Same five even values, passed as a single (non-nested) array.
        assertEquals(5, SegmentUtils.countMatches(oneToTen, matchesMultiplesOfTwo));

        // Multiples of 5 in oneToTen: 5,10 (2) plus 5 from fiveSixSeven (1) = 3.
        assertEquals(3, SegmentUtils.countMatches(new long[][] { oneToTen, fiveSixSeven }, matchesMultiplesOfFive));
        // Multiples of 5 in oneToTen only: 5,10 = 2.
        assertEquals(2, SegmentUtils.countMatches(new long[][] { oneToTen }, matchesMultiplesOfFive));
        // Same two multiples of 5, passed as a single (non-nested) array.
        assertEquals(2, SegmentUtils.countMatches(oneToTen, matchesMultiplesOfFive));
    }
}
