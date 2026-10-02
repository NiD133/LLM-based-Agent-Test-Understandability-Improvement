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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils}.
 */
class WordUtilsTest {

    /** A string containing every Unicode whitespace code point, used to verify whitespace-only strings are preserved. */
    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    // -----------------------------------------------------------------------
    // abbreviate
    // -----------------------------------------------------------------------

    /**
     * Verifies that an upper limit below -1 (the "no limit" sentinel) is rejected.
     * Values of -2 or lower are not valid upper bounds.
     */
    @Test
    void testAbbreviateForLowerThanMinusOneValues() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("01 23 45 67 89", 9, -10, null));
    }

    /**
     * Verifies abbreviation behaviour when the abbreviation point is determined by
     * the first space found at or after the lower limit.
     */
    @Test
    void testAbbreviateForLowerValue() {
        // lower=0: first word "012" is kept (space at index 3, within upper=5)
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        // lower=5: first space is at index 5, so everything up to index 5 is kept
        assertEquals("01234", WordUtils.abbreviate("01234 56789", 5, 10, null));
        // upper=-1 means no upper limit; cut at the last space before or at lower=9
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 9, -1, null));
        // upper=10 caps the result at 10 characters
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, null));
        // lower exceeds string length: entire string is returned unchanged
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 15, 20, null));
    }

    /**
     * Verifies that the appendToEnd string is appended when abbreviation actually occurs,
     * but omitted when the full string fits within the limits.
     */
    @Test
    void testAbbreviateForLowerValueAndAppendedString() {
        // No abbreviation needed (null append): first word returned as-is
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        // Abbreviation occurred, "-" is appended
        assertEquals("01234-", WordUtils.abbreviate("01234 56789", 5, 10, "-"));
        // upper=-1: cut at space after lower, "abc" appended
        assertEquals("01 23 45 67abc", WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc"));
        // Empty append string: no visible suffix even though abbreviation occurred
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, ""));
    }

    /**
     * Verifies that null input returns null and empty input returns empty,
     * and that edge cases around zero-length results are handled.
     */
    @Test
    void testAbbreviateForNullAndEmptyString() {
        assertNull(WordUtils.abbreviate(null, 1, -1, ""));
        assertEquals(StringUtils.EMPTY, WordUtils.abbreviate("", 1, -1, ""));
        // upper=0 means zero characters allowed; result is empty string
        assertEquals("", WordUtils.abbreviate("0123456790", 0, 0, ""));
        // Leading space with lower=0 upper=-1: the "word" starts at index 1, not 0
        assertEquals("", WordUtils.abbreviate(" 0123456790", 0, -1, ""));
    }

    /**
     * Verifies that the upper limit correctly caps the result length.
     */
    @Test
    void testAbbreviateForUpperLimit() {
        // upper=5 caps at 5 characters (no space in that range, so hard cut)
        assertEquals("01234", WordUtils.abbreviate("0123456789", 0, 5, ""));
        // lower=2, upper=5: first space is at index 3, which is within upper
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, ""));
        // upper=-1 means no limit; entire string is returned
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }

    /**
     * Verifies the upper-limit cap together with an appended suffix string.
     */
    @Test
    void testAbbreviateForUpperLimitAndAppendedString() {
        // Abbreviated to 5 chars, then "-" appended
        assertEquals("01234-", WordUtils.abbreviate("0123456789", 0, 5, "-"));
        // No abbreviation needed (space within bounds, null append)
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, null));
        // No abbreviation (entire string fits), no suffix added
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }

    /**
     * Verifies that specifying an upper limit strictly less than the lower limit is rejected.
     */
    @Test
    void testAbbreviateUpperLessThanLowerValues() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("0123456789", 5, 2, ""));
    }

    /**
     * Regression test: previously, when the lower limit exactly matched or exceeded
     * a word boundary, the result was incorrectly computed. This covers the case where
     * lower equals the position of the next space and the upper limit is generous.
     */
    @Test
    void testAbbreviateWhenLowerLimitExceedsWordBoundary() {
        // lower=0 finds space at index 2 ("01"), but upper=40 so result is "01"
        assertEquals("01", WordUtils.abbreviate("01 23 45 67 89", 0, 40, ""));
        // lower=10 finds the space at index 10, upper=40 so result is "01 23 45 67"
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 10, 40, ""));
        // lower=40 exceeds string length; entire string is returned
        assertEquals("01 23 45 67 89", WordUtils.abbreviate("01 23 45 67 89", 40, 40, ""));
    }

    // -----------------------------------------------------------------------
    // capitalize
    // -----------------------------------------------------------------------

    /**
     * Verifies capitalize with default whitespace delimiters.
     * Only the first character of each whitespace-separated word is uppercased;
     * the rest of the word characters are left unchanged.
     */
    @Test
    void testCapitalize_String() {
        // Null / empty / blank pass-through
        assertNull(WordUtils.capitalize(null));
        assertEquals("", WordUtils.capitalize(""));
        assertEquals("  ", WordUtils.capitalize("  "));

        // Single character words
        assertEquals("I", WordUtils.capitalize("I"));
        assertEquals("I", WordUtils.capitalize("i"));

        // Multi-word strings: only first char of each word is titlecased
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }

    /**
     * Verifies capitalizeFully with default whitespace delimiters.
     * Unlike capitalize, this method also lowercases the remaining characters
     * in each word, so "HERE" becomes "Here".
     */
    @Test
    void testCapitalizeFully_String() {
        // Null / empty / blank pass-through
        assertNull(WordUtils.capitalizeFully(null));
        assertEquals("", WordUtils.capitalizeFully(""));
        assertEquals("  ", WordUtils.capitalizeFully("  "));

        // Single character
        assertEquals("I", WordUtils.capitalizeFully("I"));
        assertEquals("I", WordUtils.capitalizeFully("i"));

        // Multi-word strings: entire word is normalised (first char up, rest down)
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I Am Here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am HERE 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));

        // Single word
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet"));

        // Non-space whitespace (tab, newline) also acts as a word delimiter
        assertEquals("A\tB\nC D", WordUtils.capitalizeFully("a\tb\nc d"));
        assertEquals("And \tBut \nCleat  Dome", WordUtils.capitalizeFully("and \tbut \ncleat  dome"));

        // All-whitespace string is returned unchanged
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        // Words surrounding a full Unicode whitespace block are capitalised
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }

    /**
     * Verifies that passing an empty delimiter array means no characters are treated
     * as delimiters, so the entire string is considered one word and no capitalisation occurs.
     */
    @Test
    void testCapitalizeFullyWithEmptyDelimiters() {
        assertEquals("I am fine now", WordUtils.capitalizeFully("i am fine now", new char[] {}));
    }

    /**
     * Verifies capitalizeFully with a custom set of delimiters.
     */
    @Test
    void testCapitalizeFullyWithDelimiters_String() {
        // Null / empty / blank pass-through with empty delimiter array
        assertNull(WordUtils.capitalizeFully(null, null));
        assertEquals("", WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // Delimiters: '-', '+', ' ', '@'
        char[] chars = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalizeFully("I", chars));
        assertEquals("I", WordUtils.capitalizeFully("i", chars));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("i-am here+123", chars));
        assertEquals("I Am+Here-123", WordUtils.capitalizeFully("I Am+Here-123", chars));
        assertEquals("I+Am-Here 123", WordUtils.capitalizeFully("i+am-HERE 123", chars));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("I-AM HERE+123", chars));

        // Delimiter is only '.' : space is NOT a delimiter, so "i aM" stays as "i am"
        chars = new char[] { '.' };
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", chars));

        // null delimiter falls back to whitespace
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));

        // Single word with null/non-matching delimiter: whole word is fully capitalised
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null));
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] { '!' }));
    }

    /**
     * Verifies capitalize with a custom set of delimiters.
     * Unlike capitalizeFully, non-first characters of a word are NOT lowercased.
     */
    @Test
    void testCapitalizeWithDelimiters_String() {
        // Null / empty / blank pass-through with empty delimiter array
        assertNull(WordUtils.capitalize(null, null));
        assertEquals("", WordUtils.capitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // Delimiters: '-', '+', ' ', '@'
        char[] chars = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalize("I", chars));
        assertEquals("I", WordUtils.capitalize("i", chars));
        assertEquals("I-Am Here+123", WordUtils.capitalize("i-am here+123", chars));
        assertEquals("I Am+Here-123", WordUtils.capitalize("I Am+Here-123", chars));
        // 'HERE' is not touched because only first-char-after-delimiter is capitalised
        assertEquals("I+Am-HERE 123", WordUtils.capitalize("i+am-HERE 123", chars));
        assertEquals("I-AM HERE+123", WordUtils.capitalize("I-AM HERE+123", chars));

        // Delimiter is only '.' : 'f' after '.' is capitalised, but 'aM' is left unchanged
        chars = new char[] { '.' };
        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", chars));

        // null delimiter falls back to whitespace
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }

    // -----------------------------------------------------------------------
    // Constructor reflection check
    // -----------------------------------------------------------------------

    /**
     * Verifies that WordUtils has exactly one public, non-final constructor,
     * satisfying the JavaBean / utility-class contract.
     */
    @Test
    void testConstructor() {
        assertNotNull(new WordUtils());
        final Constructor<?>[] cons = WordUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(WordUtils.class.getModifiers()));
        assertFalse(Modifier.isFinal(WordUtils.class.getModifiers()));
    }

    // -----------------------------------------------------------------------
    // containsAllWords
    // -----------------------------------------------------------------------

    /**
     * Verifies containsAllWords with various null, empty, and normal inputs.
     * A null sentence, a null/empty word, or a partial-word match all return false.
     */
    @Test
    void testContainsAllWords_StringString() {
        // Null sentence always returns false
        assertFalse(WordUtils.containsAllWords(null));
        assertFalse(WordUtils.containsAllWords(null, ""));
        assertFalse(WordUtils.containsAllWords(null, "ab"));

        // Empty sentence always returns false
        assertFalse(WordUtils.containsAllWords(""));
        assertFalse(WordUtils.containsAllWords("", (String) null));
        assertFalse(WordUtils.containsAllWords("", ""));
        assertFalse(WordUtils.containsAllWords("", "ab"));

        // Sentence with no words array or null/empty word returns false
        assertFalse(WordUtils.containsAllWords("foo"));
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
        assertFalse(WordUtils.containsAllWords("bar", ""));

        // "by" is a substring of "zzabyycdxx" but not a whole word — returns false
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));

        // All three words appear as whole words — returns true
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));

        // One word in the search array is null — returns false
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
        // Even if "null" appears literally in the sentence, a null search word still fails
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));

        // "b" is not a whole word in "ab" — returns false
        assertFalse(WordUtils.containsAllWords("ab", "b"));
        assertFalse(WordUtils.containsAllWords("ab", "z"));

        // Special regex characters in the search word should be handled safely
        assertFalse(WordUtils.containsAllWords("ab", "["));
        assertFalse(WordUtils.containsAllWords("ab", "]"));
        assertFalse(WordUtils.containsAllWords("ab", "*"));

        // Both "ab" and "x" appear as complete words — returns true
        assertTrue(WordUtils.containsAllWords("ab x", "ab", "x"));
    }

    /**
     * Verifies that a null CharSequence word causes containsAllWords to return false.
     */
    @Test
    void testContainsAllWordsWithNull() {
        assertFalse(WordUtils.containsAllWords("M", (CharSequence) null));
    }

    // -----------------------------------------------------------------------
    // initials
    // -----------------------------------------------------------------------

    /**
     * Verifies initials extraction with default whitespace delimiters.
     * The first character of each whitespace-delimited word is collected.
     */
    @Test
    void testInitials_String() {
        // Null / empty / whitespace-only pass-through
        assertNull(WordUtils.initials(null));
        assertEquals("", WordUtils.initials(""));
        assertEquals("", WordUtils.initials("  "));

        // Single-character / single-word inputs
        assertEquals("I", WordUtils.initials("I"));
        assertEquals("i", WordUtils.initials("i"));

        // Standard multi-word inputs
        assertEquals("BJL", WordUtils.initials("Ben John Lee"));
        // Extra and embedded whitespace (spaces, newline, tab) are treated as delimiters
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"));
        // "J.Lee" is one word (no whitespace), so only "B" and "J" are initials
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"));
        // ". " in the middle: "." starts a word after the space before it
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"));
        assertEquals("iah1", WordUtils.initials("i am here 123"));
    }

    /**
     * Verifies initials extraction with an explicit delimiter array.
     * Tests four distinct delimiter configurations in sequence.
     */
    @Test
    void testInitials_String_charArray() {
        // --- null delimiter array: behaves the same as the no-delimiter overload (whitespace) ---
        char[] array = null;
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t", array));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", array));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));

        // --- empty delimiter array: no character is a delimiter, so no initials are extracted ---
        array = ArrayUtils.EMPTY_CHAR_ARRAY;
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("", WordUtils.initials("I", array));
        assertEquals("", WordUtils.initials("i", array));
        assertEquals("", WordUtils.initials("SJC", array));
        assertEquals("", WordUtils.initials("Ben John Lee", array));
        assertEquals("", WordUtils.initials("   Ben \n   John\tLee\t", array));
        assertEquals("", WordUtils.initials("Ben J.Lee", array));
        assertEquals("", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("", WordUtils.initials("i am here 123", array));

        // --- delimiter is space only: tab and newline are NOT delimiters ---
        array = " ".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", array));
        // "\n" is not a delimiter, so "John\tLee\t" is still one word starting with "J";
        // the leading "   " is spaces, so "\n" starts the second initial
        assertEquals("B\nJ", WordUtils.initials("   Ben \n   John\tLee\t", array));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));

        // --- delimiters are space and dot: "J.Lee" splits into two words ---
        array = " .".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        // Now "J.Lee" is two words: "J" and "Lee" → initials "J" and "L"
        assertEquals("BJL", WordUtils.initials("Ben J.Lee", array));
        assertEquals("BJL", WordUtils.initials(" Ben   John  . Lee", array));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));

        // --- delimiters are space, dot, and apostrophe: "O'Murphy" splits into "O" and "Murphy" ---
        array = " .'".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        assertEquals("", WordUtils.initials("  ", array));
        assertEquals("I", WordUtils.initials("I", array));
        assertEquals("i", WordUtils.initials("i", array));
        assertEquals("S", WordUtils.initials("SJC", array));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", array));
        assertEquals("BJL", WordUtils.initials("Ben J.Lee", array));
        assertEquals("BJL", WordUtils.initials(" Ben   John  . Lee", array));
        // Apostrophe splits "O'Murphy" into "O" and "Murphy" → initials "O" and "M"
        assertEquals("KOM", WordUtils.initials("Kay O'Murphy", array));
        assertEquals("iah1", WordUtils.initials("i am here 123", array));

        // --- delimiters are specific characters ('S','I','J','o','1'):
        //     these are unusual — the delimiter characters themselves trigger "next char is initial" ---
        array = "SIJo1".toCharArray();
        assertNull(WordUtils.initials(null, array));
        assertEquals("", WordUtils.initials("", array));
        // "  " has spaces as delimiters, so the space itself is a delimiter and ' ' yields initial ' '
        assertEquals(" ", WordUtils.initials("  ", array));
        // "I" is a delimiter, so no initial follows it
        assertEquals("", WordUtils.initials("I", array));
        // "i" is not a delimiter; it is the first character and not preceded by a delimiter,
        // but since we're at the start, 'i' IS captured as an initial
        assertEquals("i", WordUtils.initials("i", array));
        // "S" is delimiter, "J" is delimiter, so only "C" follows as initial
        assertEquals("C", WordUtils.initials("SJC", array));
        // "J" is delimiter → "o" follows; "o" is delimiter → "h" follows; result is "B" then "h"
        assertEquals("Bh", WordUtils.initials("Ben John Lee", array));
        assertEquals("B.", WordUtils.initials("Ben J.Lee", array));
        // Leading space is not a delimiter here; " " triggers ' ' as initial; "J" triggers "o" → 'h'... but ' '
        assertEquals(" h", WordUtils.initials(" Ben   John  . Lee", array));
        // "K" is initial; "o" is delimiter → "'" or something; only "K" captured
        assertEquals("K", WordUtils.initials("Kay O'Murphy", array));
        // "1" is delimiter → "2" follows; "i" is initial at start; result is "i" + "2"
        assertEquals("i2", WordUtils.initials("i am here 123", array));
    }

    /**
     * Verifies that surrogate-pair characters are correctly handled as initials,
     * both when using default (whitespace) delimiters and when using ASCII or
     * multi-code-unit UTF-16 characters as delimiters.
     */
    @Test
    void testInitialsSurrogatePairs() {
        // Tests with space as default delimiter
        assertEquals("𐌀𐌂", WordUtils.initials("𐌀𐌁 𐌂𐌃"));
        assertEquals("𐌀𐌂", WordUtils.initials("𐌀𐌁 𐌂𐌃", null));
        assertEquals("𐌀𐌂", WordUtils.initials("𐌀 𐌂 ", null));

        // Tests with UTF-16 as delimiters
        assertEquals("𐌀𐌂", WordUtils.initials("𐌀𐌁.𐌂𐌃", new char[] { '.' }));
        assertEquals("𐌀𐌂", WordUtils.initials("𐌀𐌁A𐌂𐌃", new char[] { 'A' }));

        // Tests with UTF-32 as delimiters
        assertEquals("𐌀𐌂",
                WordUtils.initials("𐌀𐌁𐌔𐌂𐌃", new char[] { '\uD800', '\uDF14' }));
        assertEquals("𐌀𐌂", WordUtils.initials("𐌀𐌁𐌔𐌘𐌂𐌃",
                new char[] { '\uD800', '\uDF14', '\uD800', '\uDF18' }));
    }

    // -----------------------------------------------------------------------
    // isDelimiter (deprecated API)
    // -----------------------------------------------------------------------

    /**
     * Verifies the char-based isDelimiter method:
     * null delimiter array means whitespace is the delimiter;
     * a provided array is checked for membership.
     */
    @Test
    void testIsDelimiter() {
        // null delimiters → whitespace rule: '.' is not whitespace, ' ' is
        assertFalse(WordUtils.isDelimiter('.', null));
        assertTrue(WordUtils.isDelimiter(' ', null));

        // Custom delimiter array {'.'}
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.' }));

        // Custom delimiter array {'.', '_', 'a'} — space is absent, '.' is present
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.', '_', 'a', '.' }));
    }

    /**
     * Verifies the codePoint-based isDelimiter method with the same cases as the
     * char overload, confirming that int code-point inputs behave identically.
     */
    @Test
    void testIsDelimiterCodePoint() {
        assertFalse(WordUtils.isDelimiter((int) '.', null));
        assertTrue(WordUtils.isDelimiter((int) ' ', null));

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.' }));

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.', '_', 'a', '.' }));
    }

    // -----------------------------------------------------------------------
    // wrap
    // -----------------------------------------------------------------------

    /**
     * Regression test: wrapping a very long string of repeated 43-character words
     * at column 70 previously threw StringIndexOutOfBoundsException.
     */
    @Test
    void testWrapLongRepeatedWordDoesNotThrow() {
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                70);
    }

    /**
     * Verifies swapCase inverts upper/lower case on a word-boundary basis.
     * Leading lower-case characters (or characters after whitespace) become title-case;
     * other lower-case characters become upper-case; upper/title-case become lower-case.
     */
    @Test
    void testSwapCase_String() {
        // Null / empty / blank pass-through
        assertNull(WordUtils.swapCase(null));
        assertEquals("", WordUtils.swapCase(""));
        assertEquals("  ", WordUtils.swapCase("  "));

        // Single characters
        assertEquals("i", WordUtils.swapCase("I"));
        assertEquals("I", WordUtils.swapCase("i"));

        // Multi-word strings
        assertEquals("I AM HERE 123", WordUtils.swapCase("i am here 123"));
        assertEquals("i aM hERE 123", WordUtils.swapCase("I Am Here 123"));
        assertEquals("I AM here 123", WordUtils.swapCase("i am HERE 123"));
        assertEquals("i am here 123", WordUtils.swapCase("I AM HERE 123"));

        // ǈ is the Unicode "title case" letter Lj; it should swap to its lowercase ǉ
        final String test = "This String contains a TitleCase character: ǈ";
        final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: ǉ";
        assertEquals(expect, WordUtils.swapCase(test));
    }

    /**
     * Regression test: wrapping a long run-on string at Integer.MAX_VALUE width
     * previously threw StringIndexOutOfBoundsException due to integer overflow.
     */
    @Test
    void testWrapWithMaxIntWidthDoesNotThrow() throws Exception {
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                Integer.MAX_VALUE);
    }

    /**
     * Verifies uncapitalize with default whitespace delimiters.
     * Only the first character of each whitespace-separated word is lowercased;
     * the remainder of the word is left unchanged.
     */
    @Test
    void testUncapitalize_String() {
        // Null / empty / blank pass-through
        assertNull(WordUtils.uncapitalize(null));
        assertEquals("", WordUtils.uncapitalize(""));
        assertEquals("  ", WordUtils.uncapitalize("  "));

        // Single characters
        assertEquals("i", WordUtils.uncapitalize("I"));
        assertEquals("i", WordUtils.uncapitalize("i"));

        // Multi-word strings: only first char of each word is lowercased
        assertEquals("i am here 123", WordUtils.uncapitalize("i am here 123"));
        assertEquals("i am here 123", WordUtils.uncapitalize("I Am Here 123"));
        assertEquals("i am hERE 123", WordUtils.uncapitalize("i am HERE 123"));
        assertEquals("i aM hERE 123", WordUtils.uncapitalize("I AM HERE 123"));

        // Tab and newline also act as word delimiters
        assertEquals("a\tb\nc d", WordUtils.uncapitalize("A\tB\nC D"));
        assertEquals("and \tbut \ncLEAT  dome", WordUtils.uncapitalize("And \tBut \nCLEAT  Dome"));

        // All-whitespace string is returned unchanged (via capitalizeFully — preserved from original)
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }

    /**
     * Verifies that passing an empty delimiter array means no characters are treated
     * as delimiters, so the entire string is considered one word and no uncapitalisation occurs.
     */
    @Test
    void testUncapitalizeWithEmptyDelimiters() {
        assertEquals("i am fine now", WordUtils.uncapitalize("I am fine now", new char[] {}));
    }

    /**
     * Verifies uncapitalize with a custom set of delimiters.
     */
    @Test
    void testUncapitalizeWithDelimiters_String() {
        // Null / empty / blank pass-through with empty delimiter array
        assertNull(WordUtils.uncapitalize(null, null));
        assertEquals("", WordUtils.uncapitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.uncapitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        // Delimiters: '-', '+', ' ', '@'
        char[] chars = { '-', '+', ' ', '@' };
        assertEquals("i", WordUtils.uncapitalize("I", chars));
        assertEquals("i", WordUtils.uncapitalize("i", chars));
        assertEquals("i am-here+123", WordUtils.uncapitalize("i am-here+123", chars));
        assertEquals("i+am here-123", WordUtils.uncapitalize("I+Am Here-123", chars));
        assertEquals("i-am+hERE 123", WordUtils.uncapitalize("i-am+HERE 123", chars));
        assertEquals("i aM-hERE+123", WordUtils.uncapitalize("I AM-HERE+123", chars));

        // Delimiter is only '.': space does NOT trigger uncapitalisation
        chars = new char[] { '.' };
        assertEquals("i AM.fINE", WordUtils.uncapitalize("I AM.FINE", chars));

        // null delimiter falls back to whitespace
        assertEquals("i aM.FINE", WordUtils.uncapitalize("I AM.FINE", null));
    }

    /**
     * Verifies wrap using the system line separator (no explicit newline string).
     */
    @Test
    void testWrap_StringInt() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));

        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));

        final String systemNewLine = System.lineSeparator();

        // Normal wrapping at 20 columns
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20));

        // Long word at end: URL exceeds column limit and is not broken
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump" + systemNewLine + "to the commons" + systemNewLine + "website -" + systemNewLine + "https://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20));

        // Long word in the middle: URL kept on its own line
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here," + systemNewLine + "https://commons.apache.org," + systemNewLine + "to jump to the" + systemNewLine + "commons website";
        assertEquals(expected, WordUtils.wrap(input, 20));

        // Multiple leading spaces between words: leading spaces on new line are stripped,
        // trailing spaces are kept
        input = "word1             word2                        word3";
        expected = "word1  " + systemNewLine + "word2  " + systemNewLine + "word3";
        assertEquals(expected, WordUtils.wrap(input, 7));
    }

    /**
     * Verifies wrap with an explicit newline string and a wrapLongWords flag,
     * covering normal input, unusual newline strings, short lines, and long URLs.
     */
    @Test
    void testWrap_StringIntStringBoolean() {
        // Null input always returns null regardless of other parameters
        assertNull(WordUtils.wrap(null, 20, "\n", false));
        assertNull(WordUtils.wrap(null, 20, "\n", true));
        assertNull(WordUtils.wrap(null, 20, null, true));
        assertNull(WordUtils.wrap(null, 20, null, false));
        assertNull(WordUtils.wrap(null, -1, null, true));
        assertNull(WordUtils.wrap(null, -1, null, false));

        // Empty input always returns empty string
        assertEquals("", WordUtils.wrap("", 20, "\n", false));
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
        assertEquals("", WordUtils.wrap("", 20, null, false));
        assertEquals("", WordUtils.wrap("", 20, null, true));
        assertEquals("", WordUtils.wrap("", -1, null, false));
        assertEquals("", WordUtils.wrap("", -1, null, true));

        // Normal wrapping at 20 columns: wrapLongWords has no effect when no word exceeds the limit
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // Custom newline string "<br />" instead of "\n"
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", true));

        // Short line length: words are broken one per line when wrapLength is small
        input = "Here is one line";
        expected = "Here\nis one\nline";
        assertEquals(expected, WordUtils.wrap(input, 6, "\n", false));
        expected = "Here\nis\none\nline";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false));
        // Negative wrapLength is treated as 1
        assertEquals(expected, WordUtils.wrap(input, -1, "\n", false));

        // null newline string falls back to system line separator
        final String systemNewLine = System.lineSeparator();
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, null, false));
        assertEquals(expected, WordUtils.wrap(input, 20, null, true));

        // Leading spaces on a new line are stripped; trailing spaces are kept
        input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        expected = "Here:  is  one  line\nof  text  that  is \ngoing  to  be \nwrapped  after  20 \ncolumns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // Tab character is treated like any other non-space character for wrap decisions
        input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        expected = "Here is\tone line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // Tab exactly at the wrap column boundary
        input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        expected = "Here is one line\nof\ttext that is\ngoing to be wrapped\nafter 20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // Long word (URL) at end: wrapLongWords=false keeps it on one line;
        // wrapLongWords=true breaks it at the column limit
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apac\nhe.org";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // Long word (URL) in the middle
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        expected = "Click here,\nhttps://commons.apac\nhe.org, to jump to\nthe commons website";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    /**
     * Verifies wrap with a custom regex word-break pattern instead of a space character.
     */
    @Test
    void testWrap_StringIntStringBooleanString() {
        // No wrapping needed: string is short enough
        String input = "flammable/inflammable";
        String expected = "flammable/inflammable";
        assertEquals(expected, WordUtils.wrap(input, 30, "\n", false, "/"));

        // Wrap at "/" with a very short column limit forces a break
        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false, "/"));

        // wrapLongWords=true: the word "inflammable" (11 chars) exceeds limit=9, so it is broken
        expected = "flammable\ninflammab\nle";
        assertEquals(expected, WordUtils.wrap(input, 9, "\n", true, "/"));

        // wrapLongWords=true but "inflammable" fits in 15 chars, so no mid-word break
        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));

        // No "/" delimiter in the string; wrapLongWords=true causes hard break at 15
        input = "flammableinflammable";
        expected = "flammableinflam\nmable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));
    }

    /**
     * Verifies that a zero-width regex match in the middle of a string causes
     * two consecutive line breaks (producing an empty line between segments).
     */
    @Test
    void testWrapAtMiddleTwice() {
        assertEquals("abcdef\n\nabcdef", WordUtils.wrap("abcdefggabcdef", 2, "\n", false, "(?=g)"));
    }

    /**
     * Verifies that a zero-width regex match at the very start and very end of a
     * string produces leading and trailing newlines.
     */
    @Test
    void testWrapAtStartAndEnd() {
        assertEquals("\nabcdefabcdef\n", WordUtils.wrap("nabcdefabcdefn", 2, "\n", false, "(?=n)"));
    }

    /**
     * Verifies that multiple zero-width regex matches inside a string each trigger
     * a line break at the correct position.
     */
    @Test
    void testWrapWithMultipleRegexMatchOfLength0() {
        assertEquals("abc\ndefabc\ndef", WordUtils.wrap("abcdefabcdef", 2, "\n", false, "(?=d)"));
    }

    /**
     * Verifies that a single zero-width regex match inside a string triggers exactly
     * one line break at the correct position.
     */
    @Test
    void testWrapWithRegexMatchOfLength0() {
        assertEquals("abc\ndef", WordUtils.wrap("abcdef", 2, "\n", false, "(?=d)"));
    }

    /**
     * Verifies that a zero-width regex that matches at position 0 (i.e., every position
     * starting with 'a') does not cause an infinite loop. The method must complete within 2 seconds.
     */
    @Test
    void testZeroWidthWrapOnRegex() {
        assertTimeout(Duration.ofSeconds(2), () -> assertNotNull(WordUtils.wrap("abcdef", 3, "\n", false, "(?=a)")));
    }

}
