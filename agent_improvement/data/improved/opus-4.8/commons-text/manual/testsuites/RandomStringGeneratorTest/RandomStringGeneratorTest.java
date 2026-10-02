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

package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests for {@link RandomStringGenerator}.
 */
class RandomStringGeneratorTest {

    /** A predicate that accepts only the character {@code 'a'}. */
    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    /** A predicate that accepts only the character {@code 'b'}. */
    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    /** Sample length used by tests that exercise statistical coverage of the allowed characters. */
    private static final int LARGE_SAMPLE_LENGTH = 5000;

    /**
     * Returns the number of Unicode code points in the given string (which may differ from
     * {@link String#length()} when the string contains supplementary characters).
     */
    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    /**
     * Asserts that every {@code char} in {@code actual} equals {@code expected}.
     */
    private static void assertAllCharsEqual(final char expected, final String actual) {
        for (final char c : actual.toCharArray()) {
            assertEquals(expected, c);
        }
    }

    /**
     * Asserts that every {@code char} in {@code actual} appears in {@code allowedChars}.
     */
    private static void assertAllCharsIn(final String allowedChars, final String actual) {
        for (final char c : actual.toCharArray()) {
            assertTrue(allowedChars.indexOf(c) != -1, () -> "Unexpected character: " + c);
        }
    }

    /**
     * Asserts that every code point in {@code actual} lies within the inclusive range
     * {@code [minimumCodePoint, maximumCodePoint]}.
     */
    private static void assertAllCodePointsWithinRange(final String actual, final int minimumCodePoint, final int maximumCodePoint) {
        int i = 0;
        do {
            final int codePoint = actual.codePointAt(i);
            assertTrue(codePoint >= minimumCodePoint && codePoint <= maximumCodePoint);
            i += Character.charCount(codePoint);
        } while (i < actual.length());
    }

    @Test
    void testBadMaximumCodePoint() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(0, Character.MAX_CODE_POINT + 1));
    }

    @Test
    void testBadMinAndMax() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(2, 1));
    }

    @Test
    void testBadMinimumCodePoint() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(-1, 1));
    }

    @Test
    void testBuildDeprecated() {
        // The second filteredBy call replaces the first, so only 'b' should remain.
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder().withinRange('a', 'z').filteredBy(A_FILTER);
        final String str = builder.filteredBy(B_FILTER).build().generate(100);
        assertAllCharsEqual('b', str);
    }

    @Test
    void testChangeOfFilter() {
        // The second filteredBy call replaces the first, so only 'b' should remain.
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder().withinRange('a', 'z').filteredBy(A_FILTER);
        final String str = builder.filteredBy(B_FILTER).get().generate(100);
        assertAllCharsEqual('b', str);
    }

    @Test
    void testGenerateMinMaxLength() {
        final int minLength = 0;
        final int maxLength = 3;
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        final String str = generator.generate(minLength, maxLength);
        final int codePointLength = codePointLength(str);
        assertTrue(codePointLength >= minLength && codePointLength <= maxLength);
    }

    @Test
    void testGenerateMinMaxLengthInvalidLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        // A negative minimum length is rejected.
        assertThrowsExactly(IllegalArgumentException.class, () -> generator.generate(-1, 0));
    }

    @Test
    void testGenerateMinMaxLengthMinGreaterThanMax() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        // A minimum length greater than the maximum length is rejected.
        assertThrowsExactly(IllegalArgumentException.class, () -> generator.generate(1, 0));
    }

    @Test
    void testGenerateTakingIntThrowsNullPointerException() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
        // An array of two null predicates causes a NullPointerException during generation.
        final CharacterPredicate[] nullPredicates = new CharacterPredicate[2];
        builder.filteredBy(nullPredicates);
        final RandomStringGenerator generator = builder.get();
        assertThrowsExactly(NullPointerException.class, () -> generator.generate(18));
    }

    @Test
    void testInvalidLength() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().get().generate(-1));
    }

    @Test
    void testMultipleFilters() {
        // Both filters are active, so only 'a' and 'b' may appear and both must occur in a large sample.
        final String str = RandomStringGenerator.builder().withinRange('a', 'd').filteredBy(A_FILTER, B_FILTER).get().generate(LARGE_SAMPLE_LENGTH);
        boolean aFound = false;
        boolean bFound = false;
        for (final char c : str.toCharArray()) {
            if (c == 'a') {
                aFound = true;
            } else if (c == 'b') {
                bFound = true;
            } else {
                fail("Invalid character");
            }
        }
        assertTrue(aFound && bFound);
    }

    @Test
    void testNoLoneSurrogates() {
        final String str = RandomStringGenerator.builder().get().generate(LARGE_SAMPLE_LENGTH);
        char lastChar = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (Character.isLowSurrogate(c)) {
                // A low surrogate must be preceded by a high surrogate.
                assertTrue(Character.isHighSurrogate(lastChar));
            }
            if (Character.isHighSurrogate(lastChar)) {
                // A high surrogate must be followed by a low surrogate.
                assertTrue(Character.isLowSurrogate(c));
            }
            if (Character.isHighSurrogate(c)) {
                // A high surrogate must not be the last character in the string.
                assertTrue(i + 1 < str.length());
            }
            lastChar = c;
        }
    }

    @Test
    void testNoPrivateCharacters() {
        final int startOfPrivateBmpChars = 0xE000;
        // Request a string in an area of the Basic Multilingual Plane that is
        // largely occupied by private characters.
        final String str = RandomStringGenerator.builder()
                .withinRange(startOfPrivateBmpChars, Character.MIN_SUPPLEMENTARY_CODE_POINT - 1)
                .get()
                .generate(LARGE_SAMPLE_LENGTH);
        int i = 0;
        do {
            final int codePoint = str.codePointAt(i);
            assertFalse(Character.getType(codePoint) == Character.PRIVATE_USE);
            i += Character.charCount(codePoint);
        } while (i < str.length());
    }

    @Test
    void testPasswordExample() {
        final char[] punctuation = ArraySorter.sort(new char[] {
                '!', '"', '#', '$', '&', '\'', '(', ')', ',', '.', ':', ';', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~' });
        // @formatter:off
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(true)
                .withinRange('a', 'z')
                .withinRange('A', 'Z')
                .withinRange('0', '9')
                .selectFrom(punctuation)
                .get();
        // @formatter:on
        final String randomText = generator.generate(10);
        for (final char c : randomText.toCharArray()) {
            assertTrue(Character.isLetter(c) || Character.isDigit(c) || Arrays.binarySearch(punctuation, c) >= 0);
        }
    }

    @Test
    void testRemoveFilters() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder().withinRange('a', 'z').filteredBy(A_FILTER);
        // Calling filteredBy() with no arguments removes the previously set filter.
        builder.filteredBy();
        final String str = builder.get().generate(100);
        for (final char c : str.toCharArray()) {
            if (c != 'a') {
                // Encountering any non-'a' character proves the filter was successfully removed.
                return;
            }
        }
        fail("Filter appears to have remained in place");
    }

    @Test
    void testSelectFromCharArray() {
        final String allowedChars = "abc";
        final char[] charArray = allowedChars.toCharArray();
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(charArray).get();
        final String randomText = generator.generate(5);
        assertAllCharsIn(allowedChars, randomText);
    }

    @Test
    void testSelectFromCharVarargs() {
        final String allowedChars = "abc";
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom('a', 'b', 'c').get();
        final String randomText = generator.generate(5);
        assertAllCharsIn(allowedChars, randomText);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testSelectFromCharVarargs2(final boolean accumulate) {
        // Whether or not characters accumulate, the union of all selectFrom calls is "abcde".
        final String allowedChars = "abcde";
        // @formatter:off
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(accumulate)
                .selectFrom()
                .selectFrom(null)
                .selectFrom('a', 'b')
                .selectFrom('a', 'b', 'c')
                .selectFrom('a', 'b', 'c', 'd')
                .selectFrom('a', 'b', 'c', 'd', 'e') // only this last call matters when accumulate is false
                .get();
        // @formatter:on
        final String randomText = generator.generate(10);
        assertAllCharsIn(allowedChars, randomText);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testSelectFromCharVarargs3(final boolean accumulate) {
        final String allowedChars = "abcde";
        // @formatter:off
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(accumulate)
                .selectFrom('a', 'b', 'c', 'd', 'e')
                .selectFrom('a', 'b', 'c', 'd')
                .selectFrom('a', 'b', 'c')
                .selectFrom('a', 'b')
                .selectFrom(null)
                .selectFrom()
                .get();
        // @formatter:on
        final String randomText = generator.generate(10);
        // When accumulating, every char comes from "abcde"; otherwise the final empty
        // selectFrom wins and the generator falls back to the full code point range.
        for (final char c : randomText.toCharArray()) {
            assertEquals(accumulate, allowedChars.indexOf(c) != -1);
        }
    }

    @Test
    void testSelectFromCharVarargSize1() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom('a').get();
        final String randomText = generator.generate(5);
        assertAllCharsEqual('a', randomText);
    }

    @Test
    void testSelectFromEmptyCharVarargs() {
        // An empty selectFrom reverts to the default full code point range.
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom().get();
        final String randomText = generator.generate(5);
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }

    @Test
    void testSelectFromNullCharVarargs() {
        final int length = 5;
        // A null selectFrom reverts to the default full code point range.
        RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(null).get();
        String randomText = generator.generate(length);
        assertEquals(length, codePointLength(randomText));
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
        // With a single allowed character, every char is that character.
        final Builder builder = RandomStringGenerator.builder().selectFrom('a');
        generator = builder.get();
        randomText = generator.generate(length);
        assertAllCharsEqual('a', randomText);
        // Passing null resets the previously selected characters.
        generator = builder.selectFrom(null).get();
        randomText = generator.generate(length);
        assertEquals(length, codePointLength(randomText));
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }

    @Test
    void testSetLength() {
        final int length = 99;
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        final String str = generator.generate(length);
        assertEquals(length, codePointLength(str));
    }

    @Test
    void testUsingRandomIntUnaryOperator() {
        final char testChar = 'a';
        // A random source that always returns 'a' produces a string of only 'a'.
        final IntUnaryOperator testRandom = n -> testChar;
        final String str = RandomStringGenerator.builder().usingRandom(testRandom).get().generate(10);
        assertAllCharsEqual(testChar, str);
    }

    @Test
    void testUsingRandomTextRandomProvider() {
        final char testChar = 'a';
        // A random source that always returns 'a' produces a string of only 'a'.
        final TextRandomProvider testRandom = n -> testChar;
        final String str = RandomStringGenerator.builder().usingRandom(testRandom).get().generate(10);
        assertAllCharsEqual(testChar, str);
    }

    @Test
    void testWithinMultipleRanges() {
        final char[][] pairs = { { 'a', 'z' }, { '0', '9' } };
        // The empty and null withinRange calls are no-ops; only the pairs range applies.
        // @formatter:off
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange()
                .withinRange((char[][]) null)
                .withinRange(pairs)
                .get();
        // @formatter:on
        final String str = generator.generate(LARGE_SAMPLE_LENGTH);
        int minimumCodePoint = 0;
        int maximumCodePoint = 0;
        for (final char[] pair : pairs) {
            minimumCodePoint = Math.min(minimumCodePoint, pair[0]);
            maximumCodePoint = Math.max(maximumCodePoint, pair[1]);
        }
        assertAllCodePointsWithinRange(str, minimumCodePoint, maximumCodePoint);
    }

    @Test
    void testWithinRange() {
        final int minimumCodePoint = 'a';
        final int maximumCodePoint = 'z';
        final RandomStringGenerator generator = RandomStringGenerator.builder().withinRange(minimumCodePoint, maximumCodePoint).get();
        final String str = generator.generate(LARGE_SAMPLE_LENGTH);
        assertAllCodePointsWithinRange(str, minimumCodePoint, maximumCodePoint);
    }

    @Test
    void testZeroLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        assertEquals("", generator.generate(0));
    }
}
