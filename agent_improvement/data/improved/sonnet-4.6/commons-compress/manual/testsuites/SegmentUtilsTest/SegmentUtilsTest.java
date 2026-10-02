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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SegmentUtilsTest {

    /** IMatcher implementation that matches values evenly divisible by a given divisor. */
    private static final class MultipleOfMatcher implements IMatcher {

        private final int divisor;

        MultipleOfMatcher(final int divisor) {
            this.divisor = divisor;
        }

        @Override
        public boolean matches(final long value) {
            return value % divisor == 0;
        }
    }

    // Reusable matchers for countMatches tests
    public static final IMatcher even = new MultipleOfMatcher(2);
    public static final IMatcher five = new MultipleOfMatcher(5);

    // Reusable array used across countMatches tests
    private static final long[] ONE_TO_TEN = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    // -----------------------------------------------------------------------
    // countArgs — each argument counts as 1, regardless of type width
    // -----------------------------------------------------------------------

    /**
     * Provides (descriptor, expectedArgCount) pairs for countArgs tests.
     * Descriptors follow JVM method-descriptor notation; only the parameter
     * section (between '(' and ')') is relevant.
     */
    static Stream<Arguments> countArgs() {
        return Stream.of(
            Arguments.of("()V",                    0), // no parameters
            Arguments.of("(D)V",                   1), // one double
            Arguments.of("([D)V",                  1), // one array-of-double (counts as 1)
            Arguments.of("([[D)V",                 1), // one 2-d array-of-double (counts as 1)
            Arguments.of("(DD)V",                  2), // two doubles
            Arguments.of("(DDD)V",                 3), // three doubles
            Arguments.of("(Lblah/blah;D)V",        2), // one object ref + one double
            Arguments.of("(Lblah/blah;DLbLah;)V",  3)  // two object refs + one double
        );
    }

    /**
     * Provides (descriptor, expectedArgCount) pairs for countInvokeInterfaceArgs tests.
     * For invokeinterface, longs and doubles each count as 2 instead of 1.
     */
    static Stream<Arguments> countInvokeInterfaceArgs() {
        return Stream.of(
            Arguments.of("(Z)V",                   1), // boolean counts as 1
            Arguments.of("(D)V",                   2), // double counts as 2
            Arguments.of("(J)V",                   2), // long counts as 2
            Arguments.of("([D)V",                  1), // array-of-double counts as 1
            Arguments.of("([[D)V",                 1), // 2-d array-of-double counts as 1
            Arguments.of("(DD)V",                  4), // two doubles = 2 + 2
            Arguments.of("(Lblah/blah;D)V",        3), // object ref (1) + double (2)
            Arguments.of("(Lblah/blah;DLbLah;)V",  4), // object ref (1) + double (2) + object ref (1)
            Arguments.of("([Lblah/blah;DLbLah;)V", 4)  // array-of-object-ref (1) + double (2) + object ref (1)
        );
    }

    @ParameterizedTest
    @MethodSource("countArgs")
    @DisplayName("countArgs counts each parameter slot as 1")
    void testCountArgs(final String descriptor, final int expectedArgCount) {
        assertEquals(expectedArgCount, SegmentUtils.countArgs(descriptor));
    }

    @ParameterizedTest
    @MethodSource("countInvokeInterfaceArgs")
    @DisplayName("countInvokeInterfaceArgs counts longs/doubles as 2")
    void testCountInvokeInterfaceArgs(final String descriptor, final int expectedArgCount) {
        assertEquals(expectedArgCount, SegmentUtils.countInvokeInterfaceArgs(descriptor));
    }

    // -----------------------------------------------------------------------
    // countMatches — 1-D overload
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("countMatches(long[], even) returns count of even numbers in a flat array")
    void testCountMatchesOnFlatArrayWithEvenMatcher() {
        assertEquals(5, SegmentUtils.countMatches(ONE_TO_TEN, even));
    }

    @Test
    @DisplayName("countMatches(long[], multipleOfFive) returns count of multiples of 5 in a flat array")
    void testCountMatchesOnFlatArrayWithMultipleOfFiveMatcher() {
        assertEquals(2, SegmentUtils.countMatches(ONE_TO_TEN, five));
    }

    // -----------------------------------------------------------------------
    // countMatches — 2-D overload (single inner array)
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("countMatches(long[][], even) with one inner array equals flat-array result")
    void testCountMatchesOnSingleInnerArrayWithEvenMatcher() {
        assertEquals(5, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN }, even));
    }

    @Test
    @DisplayName("countMatches(long[][], multipleOfFive) with one inner array equals flat-array result")
    void testCountMatchesOnSingleInnerArrayWithMultipleOfFiveMatcher() {
        assertEquals(2, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN }, five));
    }

    // -----------------------------------------------------------------------
    // countMatches — 2-D overload (multiple inner arrays, matches may span arrays)
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("countMatches(long[][], even) sums even numbers across all inner arrays")
    void testCountMatchesAcrossMultipleInnerArraysWithEvenMatcher() {
        // ONE_TO_TEN has 5 even numbers; {5,6,7} adds one more even number (6)
        final long[] extra = { 5, 6, 7 };
        assertEquals(6, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN, extra }, even));
    }

    @Test
    @DisplayName("countMatches(long[][], multipleOfFive) sums multiples of 5 across all inner arrays")
    void testCountMatchesAcrossMultipleInnerArraysWithMultipleOfFiveMatcher() {
        // ONE_TO_TEN has 2 multiples of 5 (5,10); {5,6,7} adds one more (5)
        final long[] extra = { 5, 6, 7 };
        assertEquals(3, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN, extra }, five));
    }
}
