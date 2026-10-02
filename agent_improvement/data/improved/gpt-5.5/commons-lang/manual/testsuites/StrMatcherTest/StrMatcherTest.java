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

package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link org.apache.commons.lang3.text.StrMatcher}.
 */
@Deprecated
class StrMatcherTest extends AbstractLangTest {

    private static final char[] MIXED_SEPARATOR_BUFFER = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] ALPHABET_BUFFER = "abcdef".toCharArray();

    @Test
    void testCharMatcher_char() {
        final StrMatcher matcher = StrMatcher.charMatcher('c');

        assertMatches(matcher, ALPHABET_BUFFER, 0, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 1, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 2, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 3, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 4, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 5, 0);
    }

    @Test
    void testCharSetMatcher_charArray() {
        final StrMatcher matcher = StrMatcher.charSetMatcher("ace".toCharArray());

        assertMatches(matcher, ALPHABET_BUFFER, 0, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 1, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 2, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 3, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 4, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 5, 0);
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher());
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher((char[]) null));
        assertInstanceOf(StrMatcher.CharMatcher.class, StrMatcher.charSetMatcher("a".toCharArray()));
    }

    @Test
    void testCharSetMatcher_String() {
        final StrMatcher matcher = StrMatcher.charSetMatcher("ace");

        assertMatches(matcher, ALPHABET_BUFFER, 0, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 1, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 2, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 3, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 4, 1);
        assertMatches(matcher, ALPHABET_BUFFER, 5, 0);
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher(""));
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher((String) null));
        assertInstanceOf(StrMatcher.CharMatcher.class, StrMatcher.charSetMatcher("a"));
    }

    @Test
    void testCommaMatcher() {
        final StrMatcher matcher = StrMatcher.commaMatcher();

        assertSame(matcher, StrMatcher.commaMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 0, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 1, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 2, 0);
    }

    @Test
    void testDoubleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.doubleQuoteMatcher();

        assertSame(matcher, StrMatcher.doubleQuoteMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 11, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 12, 1);
    }

    @Test
    void testMatcherIndices() {
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");

        assertBoundedMatch(matcher, ALPHABET_BUFFER, 1, 1, ALPHABET_BUFFER.length, 2);
        assertBoundedMatch(matcher, ALPHABET_BUFFER, 1, 0, 3, 2);
        assertBoundedMatch(matcher, ALPHABET_BUFFER, 1, 0, 2, 0);
    }

    @Test
    void testNoneMatcher() {
        final StrMatcher matcher = StrMatcher.noneMatcher();

        assertSame(matcher, StrMatcher.noneMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 0, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 1, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 2, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 3, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 4, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 5, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 6, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 7, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 8, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 9, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 10, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 11, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 12, 0);
    }

    @Test
    void testQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.quoteMatcher();

        assertSame(matcher, StrMatcher.quoteMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 10, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 11, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 12, 1);
    }

    @Test
    void testSingleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.singleQuoteMatcher();

        assertSame(matcher, StrMatcher.singleQuoteMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 10, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 11, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 12, 0);
    }

    @Test
    void testSpaceMatcher() {
        final StrMatcher matcher = StrMatcher.spaceMatcher();

        assertSame(matcher, StrMatcher.spaceMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 4, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 5, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 6, 0);
    }

    @Test
    void testSplitMatcher() {
        final StrMatcher matcher = StrMatcher.splitMatcher();

        assertSame(matcher, StrMatcher.splitMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 2, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 3, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 4, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 5, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 6, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 7, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 8, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 9, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 10, 0);
    }

    @Test
    void testStringMatcher_String() {
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");

        assertMatches(matcher, ALPHABET_BUFFER, 0, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 1, 2);
        assertMatches(matcher, ALPHABET_BUFFER, 2, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 3, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 4, 0);
        assertMatches(matcher, ALPHABET_BUFFER, 5, 0);
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(""));
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(null));
    }

    @Test
    void testTabMatcher() {
        final StrMatcher matcher = StrMatcher.tabMatcher();

        assertSame(matcher, StrMatcher.tabMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 2, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 3, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 4, 0);
    }

    @Test
    void testTrimMatcher() {
        final StrMatcher matcher = StrMatcher.trimMatcher();

        assertSame(matcher, StrMatcher.trimMatcher());
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 2, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 3, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 4, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 5, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 6, 0);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 7, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 8, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 9, 1);
        assertMatches(matcher, MIXED_SEPARATOR_BUFFER, 10, 1);
    }

    private static void assertBoundedMatch(final StrMatcher matcher, final char[] buffer, final int position,
            final int bufferStart, final int bufferEnd, final int expectedMatchLength) {
        assertEquals(expectedMatchLength, matcher.isMatch(buffer, position, bufferStart, bufferEnd));
    }

    private static void assertMatches(final StrMatcher matcher, final char[] buffer, final int position,
            final int expectedMatchLength) {
        assertEquals(expectedMatchLength, matcher.isMatch(buffer, position));
    }

}
