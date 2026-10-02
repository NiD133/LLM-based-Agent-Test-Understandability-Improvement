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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

/**
 * Tests {@link WordUtils}.
 */
class WordUtilsTest {

    /** Every Unicode whitespace character, concatenated; used to verify whitespace is preserved verbatim. */
    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    // Supplementary-plane (surrogate-pair) characters used by the surrogate-pair tests.
    // Each constant is a single Unicode code point encoded as a UTF-16 surrogate pair.
    /** U+10300 GOTHIC LETTER AHSA. */
    private static final String GOTHIC_A = "𐌀";
    /** U+10301 GOTHIC LETTER BAIRKAN. */
    private static final String GOTHIC_B = "𐌁";
    /** U+10302 GOTHIC LETTER GIBA. */
    private static final String GOTHIC_C = "𐌂";
    /** U+10303 GOTHIC LETTER DAGS. */
    private static final String GOTHIC_D = "𐌃";
    /** U+10314 GOTHIC LETTER PAIRTHRA; used as a supplementary-plane delimiter. */
    private static final String GOTHIC_DELIM_1 = "𐌔";
    /** U+10318 GOTHIC LETTER QAIRTHRA; used as a second supplementary-plane delimiter. */
    private static final String GOTHIC_DELIM_2 = "𐌘";

    // -----------------------------------------------------------------------
    // abbreviate
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("abbreviate rejects an upper limit below -1")
    void testAbbreviateForLowerThanMinusOneValues() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("01 23 45 67 89", 9, -10, null));
    }

    @Test
    @DisplayName("abbreviate breaks at the first space at or after the lower limit")
    void testAbbreviateForLowerValue() {
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        assertEquals("01234", WordUtils.abbreviate("01234 56789", 5, 10, null));
        // upper == -1 means "no upper limit", so break at the first space after lower
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 9, -1, null));
        // no space between lower (9) and upper (10), so truncate hard at upper
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, null));
        // lower beyond the string length leaves the string untouched
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 15, 20, null));
    }

    @Test
    @DisplayName("abbreviate appends the suffix only when the string is actually shortened")
    void testAbbreviateForLowerValueAndAppendedString() {
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        assertEquals("01234-", WordUtils.abbreviate("01234 56789", 5, 10, "-"));
        assertEquals("01 23 45 67abc", WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc"));
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, ""));
    }

    @Test
    @DisplayName("abbreviate handles null and empty input")
    void testAbbreviateForNullAndEmptyString() {
        assertNull(WordUtils.abbreviate(null, 1, -1, ""));
        assertEquals(StringUtils.EMPTY, WordUtils.abbreviate("", 1, -1, ""));
        assertEquals("", WordUtils.abbreviate("0123456790", 0, 0, ""));
        assertEquals("", WordUtils.abbreviate(" 0123456790", 0, -1, ""));
    }

    @Test
    @DisplayName("abbreviate truncates at the upper limit when no space is available")
    void testAbbreviateForUpperLimit() {
        assertEquals("01234", WordUtils.abbreviate("0123456789", 0, 5, ""));
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, ""));
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }

    @Test
    @DisplayName("abbreviate at the upper limit also appends the suffix when shortened")
    void testAbbreviateForUpperLimitAndAppendedString() {
        assertEquals("01234-", WordUtils.abbreviate("0123456789", 0, 5, "-"));
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, null));
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }

    @Test
    @DisplayName("abbreviate rejects an upper limit below the lower limit")
    void testAbbreviateUpperLessThanLowerValues() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("0123456789", 5, 2, ""));
    }

    @Test
    @DisplayName("LANG-673: abbreviate honors lower/upper limits larger than the string")
    void testLANG673() {
        assertEquals("01", WordUtils.abbreviate("01 23 45 67 89", 0, 40, ""));
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 10, 40, ""));
        assertEquals("01 23 45 67 89", WordUtils.abbreviate("01 23 45 67 89", 40, 40, ""));
    }

    // -----------------------------------------------------------------------
    // capitalize
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("capitalize uppercases the first letter of each word, leaving the rest untouched")
    void testCapitalize_String() {
        // null / empty / blank are returned unchanged
        assertNull(WordUtils.capitalize(null));
        assertEquals("", WordUtils.capitalize(""));
        assertEquals("  ", WordUtils.capitalize("  "));

        assertEquals("I", WordUtils.capitalize("I"));
        assertEquals("I", WordUtils.capitalize("i"));
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));
        // already-uppercase tails are preserved
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }

    @Test
    @DisplayName("capitalize uses a custom delimiter set to mark word boundaries")
    void testCapitalizeWithDelimiters_String() {
        assertNull(WordUtils.capitalize(null, null));
        assertEquals("", WordUtils.capitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        char[] delimiters = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalize("I", delimiters));
        assertEquals("I", WordUtils.capitalize("i", delimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalize("i-am here+123", delimiters));
        assertEquals("I Am+Here-123", WordUtils.capitalize("I Am+Here-123", delimiters));
        assertEquals("I+Am-HERE 123", WordUtils.capitalize("i+am-HERE 123", delimiters));
        assertEquals("I-AM HERE+123", WordUtils.capitalize("I-AM HERE+123", delimiters));

        // with '.' as the only delimiter, a space is no longer a word boundary
        delimiters = new char[] { '.' };
        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", delimiters));
        // null delimiters fall back to whitespace
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }

    // -----------------------------------------------------------------------
    // capitalizeFully
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("capitalizeFully is locale-independent (regression for Turkish dotless-i)")
    @DefaultLocale(language = "tr", country = "TR")
    void testCapitalizeFully_LocaleIndependent() {
        // Turkish lower-cases 'I' (U+0049) to dotless 'i' (U+0131), which would otherwise leak into the result.
        assertEquals("Heli World", WordUtils.capitalizeFully("HELI WORLD"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));
    }

    @Test
    @DisplayName("capitalizeFully uppercases the first letter and lowercases the rest of each word")
    void testCapitalizeFully_String() {
        assertNull(WordUtils.capitalizeFully(null));
        assertEquals("", WordUtils.capitalizeFully(""));
        assertEquals("  ", WordUtils.capitalizeFully("  "));
        assertEquals("I", WordUtils.capitalizeFully("I"));
        assertEquals("I", WordUtils.capitalizeFully("i"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I Am Here 123"));
        // unlike capitalize, the tail of each word is forced to lower case
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am HERE 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet")); // single word
        // any whitespace character (tab, newline) separates words
        assertEquals("A\tB\nC D", WordUtils.capitalizeFully("a\tb\nc d"));
        assertEquals("And \tBut \nCleat  Dome", WordUtils.capitalizeFully("and \tbut \ncleat  dome"));
        // an all-whitespace string is returned verbatim
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }

    @Test
    @DisplayName("TEXT-88: capitalizeFully with an empty delimiter array makes the whole string one word")
    void testCapitalizeFully_Text88() {
        assertEquals("I am fine now", WordUtils.capitalizeFully("i am fine now", new char[] {}));
    }

    @Test
    @DisplayName("capitalizeFully uses a custom delimiter set to mark word boundaries")
    void testCapitalizeFullyWithDelimiters_String() {
        assertNull(WordUtils.capitalizeFully(null, null));
        assertEquals("", WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        char[] delimiters = { '-', '+', ' ', '@' };
        assertEquals("I", WordUtils.capitalizeFully("I", delimiters));
        assertEquals("I", WordUtils.capitalizeFully("i", delimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("i-am here+123", delimiters));
        assertEquals("I Am+Here-123", WordUtils.capitalizeFully("I Am+Here-123", delimiters));
        assertEquals("I+Am-Here 123", WordUtils.capitalizeFully("i+am-HERE 123", delimiters));
        assertEquals("I-Am Here+123", WordUtils.capitalizeFully("I-AM HERE+123", delimiters));

        // with '.' as the only delimiter, a space is no longer a word boundary
        delimiters = new char[] { '.' };
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", delimiters));
        // null delimiters fall back to whitespace
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null)); // single word
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] { '!' })); // no matching delim
    }

    // -----------------------------------------------------------------------
    // containsAllWords
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("containsAllWords requires every whole word to be present")
    void testContainsAllWords_StringString() {
        // a null/empty/blank target text never contains any word
        assertFalse(WordUtils.containsAllWords(null));
        assertFalse(WordUtils.containsAllWords(null, ""));
        assertFalse(WordUtils.containsAllWords(null, "ab"));

        assertFalse(WordUtils.containsAllWords(""));
        assertFalse(WordUtils.containsAllWords("", (String) null));
        assertFalse(WordUtils.containsAllWords("", ""));
        assertFalse(WordUtils.containsAllWords("", "ab"));

        // no search words, or a null/empty search word, yields false
        assertFalse(WordUtils.containsAllWords("foo"));
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
        assertFalse(WordUtils.containsAllWords("bar", ""));
        // "by" is a substring of "zzabyycdxx" but not a whole word
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));
        // any null among the search words fails the whole check
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("ab", "b"));
        assertFalse(WordUtils.containsAllWords("ab", "z"));
        // regex metacharacters in the search word are treated literally
        assertFalse(WordUtils.containsAllWords("ab", "["));
        assertFalse(WordUtils.containsAllWords("ab", "]"));
        assertFalse(WordUtils.containsAllWords("ab", "*"));
        assertTrue(WordUtils.containsAllWords("ab x", "ab", "x"));
    }

    @Test
    @DisplayName("containsAllWords returns false for a null CharSequence search word")
    void testContainsAllWordsWithNull() {
        assertFalse(WordUtils.containsAllWords("M", (CharSequence) null));
    }

    // -----------------------------------------------------------------------
    // initials
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("initials collects the first character of each whitespace-separated word")
    void testInitials_String() {
        assertNull(WordUtils.initials(null));
        assertEquals("", WordUtils.initials(""));
        assertEquals("", WordUtils.initials("  "));

        assertEquals("I", WordUtils.initials("I"));
        assertEquals("i", WordUtils.initials("i")); // case is preserved
        assertEquals("BJL", WordUtils.initials("Ben John Lee"));
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"));
        // '.' is not whitespace, so "J.Lee" is a single word
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"));
        assertEquals("iah1", WordUtils.initials("i am here 123"));
    }

    @Test
    @DisplayName("initials honors the supplied delimiter array (null, empty, and several explicit sets)")
    void testInitials_String_charArray() {
        // null delimiters => whitespace is used as the boundary
        char[] delimiters = null;
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals("", WordUtils.initials("  ", delimiters));
        assertEquals("I", WordUtils.initials("I", delimiters));
        assertEquals("i", WordUtils.initials("i", delimiters));
        assertEquals("S", WordUtils.initials("SJC", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t", delimiters));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", delimiters));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", delimiters));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("iah1", WordUtils.initials("i am here 123", delimiters));

        // empty delimiters => there are no word boundaries, so the result is empty
        delimiters = ArrayUtils.EMPTY_CHAR_ARRAY;
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals("", WordUtils.initials("  ", delimiters));
        assertEquals("", WordUtils.initials("I", delimiters));
        assertEquals("", WordUtils.initials("i", delimiters));
        assertEquals("", WordUtils.initials("SJC", delimiters));
        assertEquals("", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("", WordUtils.initials("   Ben \n   John\tLee\t", delimiters));
        assertEquals("", WordUtils.initials("Ben J.Lee", delimiters));
        assertEquals("", WordUtils.initials(" Ben   John  . Lee", delimiters));
        assertEquals("", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("", WordUtils.initials("i am here 123", delimiters));

        // space as the only delimiter
        delimiters = " ".toCharArray();
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals("", WordUtils.initials("  ", delimiters));
        assertEquals("I", WordUtils.initials("I", delimiters));
        assertEquals("i", WordUtils.initials("i", delimiters));
        assertEquals("S", WordUtils.initials("SJC", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", delimiters));
        // tab/newline are not delimiters here, so they become part of the "initial"
        assertEquals("B\nJ", WordUtils.initials("   Ben \n   John\tLee\t", delimiters));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", delimiters));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("iah1", WordUtils.initials("i am here 123", delimiters));

        // space and dot as delimiters
        delimiters = " .".toCharArray();
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals("", WordUtils.initials("  ", delimiters));
        assertEquals("I", WordUtils.initials("I", delimiters));
        assertEquals("i", WordUtils.initials("i", delimiters));
        assertEquals("S", WordUtils.initials("SJC", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben J.Lee", delimiters));
        assertEquals("BJL", WordUtils.initials(" Ben   John  . Lee", delimiters));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("iah1", WordUtils.initials("i am here 123", delimiters));

        // space, dot and apostrophe as delimiters
        delimiters = " .'".toCharArray();
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals("", WordUtils.initials("  ", delimiters));
        assertEquals("I", WordUtils.initials("I", delimiters));
        assertEquals("i", WordUtils.initials("i", delimiters));
        assertEquals("S", WordUtils.initials("SJC", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben J.Lee", delimiters));
        assertEquals("BJL", WordUtils.initials(" Ben   John  . Lee", delimiters));
        // apostrophe now splits "O'Murphy" into two words
        assertEquals("KOM", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("iah1", WordUtils.initials("i am here 123", delimiters));

        // letters/digits as delimiters: the character *after* each delimiter becomes an initial
        delimiters = "SIJo1".toCharArray();
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals(" ", WordUtils.initials("  ", delimiters));
        assertEquals("", WordUtils.initials("I", delimiters));
        assertEquals("i", WordUtils.initials("i", delimiters));
        assertEquals("C", WordUtils.initials("SJC", delimiters));
        assertEquals("Bh", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("B.", WordUtils.initials("Ben J.Lee", delimiters));
        assertEquals(" h", WordUtils.initials(" Ben   John  . Lee", delimiters));
        assertEquals("K", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("i2", WordUtils.initials("i am here 123", delimiters));
    }

    @Test
    @DisplayName("initials handles supplementary-plane (surrogate-pair) characters and delimiters")
    void testInitialsSurrogatePairs() {
        // Default delimiter (space): take the first code point of each word
        assertEquals(GOTHIC_A + GOTHIC_C, WordUtils.initials(GOTHIC_A + GOTHIC_B + " " + GOTHIC_C + GOTHIC_D));
        assertEquals(GOTHIC_A + GOTHIC_C, WordUtils.initials(GOTHIC_A + GOTHIC_B + " " + GOTHIC_C + GOTHIC_D, null));
        assertEquals(GOTHIC_A + GOTHIC_C, WordUtils.initials(GOTHIC_A + " " + GOTHIC_C + " ", null));

        // BMP (UTF-16) characters as delimiters
        assertEquals(GOTHIC_A + GOTHIC_C, WordUtils.initials(GOTHIC_A + GOTHIC_B + "." + GOTHIC_C + GOTHIC_D, new char[] { '.' }));
        assertEquals(GOTHIC_A + GOTHIC_C, WordUtils.initials(GOTHIC_A + GOTHIC_B + "A" + GOTHIC_C + GOTHIC_D, new char[] { 'A' }));

        // Supplementary-plane (UTF-32) characters as delimiters
        assertEquals(GOTHIC_A + GOTHIC_C,
                WordUtils.initials(GOTHIC_A + GOTHIC_B + GOTHIC_DELIM_1 + GOTHIC_C + GOTHIC_D, GOTHIC_DELIM_1.toCharArray()));
        assertEquals(GOTHIC_A + GOTHIC_C,
                WordUtils.initials(GOTHIC_A + GOTHIC_B + GOTHIC_DELIM_1 + GOTHIC_DELIM_2 + GOTHIC_C + GOTHIC_D,
                        (GOTHIC_DELIM_1 + GOTHIC_DELIM_2).toCharArray()));
    }

    // -----------------------------------------------------------------------
    // isDelimiter (deprecated)
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("isDelimiter(char, ...) treats null delimiters as whitespace")
    void testIsDelimiter() {
        // null delimiters => whitespace check
        assertFalse(WordUtils.isDelimiter('.', null));
        assertTrue(WordUtils.isDelimiter(' ', null));

        // explicit single delimiter
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.' }));

        // explicit delimiter set
        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.', '_', 'a', '.' }));
    }

    @Test
    @DisplayName("isDelimiter(int, ...) behaves like the char overload for code points")
    void testIsDelimiterCodePoint() {
        assertFalse(WordUtils.isDelimiter((int) '.', null));
        assertTrue(WordUtils.isDelimiter((int) ' ', null));

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.' }));

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.', '_', 'a', '.' }));
    }

    // -----------------------------------------------------------------------
    // swapCase
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("swapCase flips the case of each letter, title-casing word-initial lower-case letters")
    void testSwapCase_String() {
        assertNull(WordUtils.swapCase(null));
        assertEquals("", WordUtils.swapCase(""));
        assertEquals("  ", WordUtils.swapCase("  "));

        assertEquals("i", WordUtils.swapCase("I"));
        assertEquals("I", WordUtils.swapCase("i"));
        assertEquals("I AM HERE 123", WordUtils.swapCase("i am here 123"));
        assertEquals("i aM hERE 123", WordUtils.swapCase("I Am Here 123"));
        assertEquals("I AM here 123", WordUtils.swapCase("i am HERE 123"));
        assertEquals("i am here 123", WordUtils.swapCase("I AM HERE 123"));

        // a title-case character (U+01C8) swaps to its lower-case form (U+01C9)
        final String input = "This String contains a TitleCase character: ǈ";
        final String expected = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: ǉ";
        assertEquals(expected, WordUtils.swapCase(input));
    }

    // -----------------------------------------------------------------------
    // uncapitalize
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("uncapitalize lowercases the first letter of each word, leaving the rest untouched")
    void testUncapitalize_String() {
        assertNull(WordUtils.uncapitalize(null));
        assertEquals("", WordUtils.uncapitalize(""));
        assertEquals("  ", WordUtils.uncapitalize("  "));
        assertEquals("i", WordUtils.uncapitalize("I"));
        assertEquals("i", WordUtils.uncapitalize("i"));
        assertEquals("i am here 123", WordUtils.uncapitalize("i am here 123"));
        assertEquals("i am here 123", WordUtils.uncapitalize("I Am Here 123"));
        assertEquals("i am hERE 123", WordUtils.uncapitalize("i am HERE 123"));
        assertEquals("i aM hERE 123", WordUtils.uncapitalize("I AM HERE 123"));
        assertEquals("a\tb\nc d", WordUtils.uncapitalize("A\tB\nC D"));
        assertEquals("and \tbut \ncLEAT  dome", WordUtils.uncapitalize("And \tBut \nCLEAT  Dome"));
        // all-whitespace strings pass through capitalizeFully unchanged
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }

    @Test
    @DisplayName("TEXT-88: uncapitalize with an empty delimiter array makes the whole string one word")
    void testUnCapitalize_Text88() {
        assertEquals("i am fine now", WordUtils.uncapitalize("I am fine now", new char[] {}));
    }

    @Test
    @DisplayName("uncapitalize uses a custom delimiter set to mark word boundaries")
    void testUncapitalizeWithDelimiters_String() {
        assertNull(WordUtils.uncapitalize(null, null));
        assertEquals("", WordUtils.uncapitalize("", ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals("  ", WordUtils.uncapitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY));

        char[] delimiters = { '-', '+', ' ', '@' };
        assertEquals("i", WordUtils.uncapitalize("I", delimiters));
        assertEquals("i", WordUtils.uncapitalize("i", delimiters));
        assertEquals("i am-here+123", WordUtils.uncapitalize("i am-here+123", delimiters));
        assertEquals("i+am here-123", WordUtils.uncapitalize("I+Am Here-123", delimiters));
        assertEquals("i-am+hERE 123", WordUtils.uncapitalize("i-am+HERE 123", delimiters));
        assertEquals("i aM-hERE+123", WordUtils.uncapitalize("I AM-HERE+123", delimiters));

        // with '.' as the only delimiter, a space is no longer a word boundary
        delimiters = new char[] { '.' };
        assertEquals("i AM.fINE", WordUtils.uncapitalize("I AM.FINE", delimiters));
        // null delimiters fall back to whitespace
        assertEquals("i aM.FINE", WordUtils.uncapitalize("I AM.FINE", null));
    }

    // -----------------------------------------------------------------------
    // wrap
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("wrap(String, int) wraps on spaces at the given column using the system line separator")
    void testWrap_StringInt() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));

        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));

        final String systemNewLine = System.lineSeparator();

        // normal wrapping at 20 columns
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20));

        // a long word (URL) at the end is left unbroken
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump" + systemNewLine + "to the commons" + systemNewLine + "website -" + systemNewLine + "https://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20));

        // a long word (URL) in the middle is left unbroken
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here," + systemNewLine + "https://commons.apache.org," + systemNewLine + "to jump to the" + systemNewLine + "commons website";
        assertEquals(expected, WordUtils.wrap(input, 20));

        // leading spaces on a new line are stripped; trailing spaces are kept
        input = "word1             word2                        word3";
        expected = "word1  " + systemNewLine + "word2  " + systemNewLine + "word3";
        assertEquals(expected, WordUtils.wrap(input, 7));
    }

    @Test
    @DisplayName("wrap(String, int, newLine, wrapLongWords) covers custom separators and long-word handling")
    void testWrap_StringIntStringBoolean() {
        // null input always returns null, regardless of the other arguments
        assertNull(WordUtils.wrap(null, 20, "\n", false));
        assertNull(WordUtils.wrap(null, 20, "\n", true));
        assertNull(WordUtils.wrap(null, 20, null, true));
        assertNull(WordUtils.wrap(null, 20, null, false));
        assertNull(WordUtils.wrap(null, -1, null, true));
        assertNull(WordUtils.wrap(null, -1, null, false));

        // empty input always returns empty
        assertEquals("", WordUtils.wrap("", 20, "\n", false));
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
        assertEquals("", WordUtils.wrap("", 20, null, false));
        assertEquals("", WordUtils.wrap("", 20, null, true));
        assertEquals("", WordUtils.wrap("", -1, null, false));
        assertEquals("", WordUtils.wrap("", -1, null, true));

        // normal wrap: wrapLongWords makes no difference when no word is too long
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // a custom (multi-character) new-line string
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", true));

        // very short wrap lengths; -1 is treated as 1
        input = "Here is one line";
        expected = "Here\nis one\nline";
        assertEquals(expected, WordUtils.wrap(input, 6, "\n", false));
        expected = "Here\nis\none\nline";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, -1, "\n", false));

        // a null new-line string uses the system line separator
        final String systemNewLine = System.lineSeparator();
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, null, false));
        assertEquals(expected, WordUtils.wrap(input, 20, null, true));

        // extra spaces: trailing spaces are preserved on each wrapped line
        input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        expected = "Here:  is  one  line\nof  text  that  is \ngoing  to  be \nwrapped  after  20 \ncolumns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // a tab is treated as an ordinary (non-breaking) character
        input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        expected = "Here is\tone line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // a tab landing exactly at the wrap column
        input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        expected = "Here is one line\nof\ttext that is\ngoing to be wrapped\nafter 20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // long word at the end: wrapLongWords decides whether the URL is broken
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apac\nhe.org";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        // long word in the middle: wrapLongWords decides whether the URL is broken
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        expected = "Click here,\nhttps://commons.apac\nhe.org, to jump to\nthe commons website";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    @DisplayName("wrap(..., wrapOn) wraps on a custom regex instead of spaces")
    void testWrap_StringIntStringBooleanString() {
        // wide enough to fit: nothing changes
        String input = "flammable/inflammable";
        String expected = "flammable/inflammable";
        assertEquals(expected, WordUtils.wrap(input, 30, "\n", false, "/"));

        // wrap on '/' with a small width
        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false, "/"));

        // wrap on '/', also breaking long words
        expected = "flammable\ninflammab\nle";
        assertEquals(expected, WordUtils.wrap(input, 9, "\n", true, "/"));

        // wrap on '/'; width large enough that long-word breaking is not needed
        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));

        // no '/' present: only long-word breaking applies
        input = "flammableinflammable";
        expected = "flammableinflam\nmable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));
    }

    @Test
    @DisplayName("wrap supports a zero-width regex match in the middle of the input")
    void testWrapAtMiddleTwice() {
        assertEquals("abcdef\n\nabcdef", WordUtils.wrap("abcdefggabcdef", 2, "\n", false, "(?=g)"));
    }

    @Test
    @DisplayName("wrap supports a zero-width regex match at the start and end of the input")
    void testWrapAtStartAndEnd() {
        assertEquals("\nabcdefabcdef\n", WordUtils.wrap("nabcdefabcdefn", 2, "\n", false, "(?=n)"));
    }

    @Test
    @DisplayName("wrap handles multiple zero-width regex matches")
    void testWrapWithMultipleRegexMatchOfLength0() {
        assertEquals("abc\ndefabc\ndef", WordUtils.wrap("abcdefabcdef", 2, "\n", false, "(?=d)"));
    }

    @Test
    @DisplayName("wrap handles a single zero-width regex match")
    void testWrapWithRegexMatchOfLength0() {
        assertEquals("abc\ndef", WordUtils.wrap("abcdef", 2, "\n", false, "(?=d)"));
    }

    @Test
    @DisplayName("wrap with a zero-width regex completes without hanging")
    void testZeroWidthWrapOnRegex() {
        assertTimeout(Duration.ofSeconds(2), () -> assertNotNull(WordUtils.wrap("abcdef", 3, "\n", false, "(?=a)")));
    }

    // -----------------------------------------------------------------------
    // LANG / TEXT regressions for wrap
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("LANG-1292: wrapping very long runs no longer throws StringIndexOutOfBoundsException")
    void testLANG1292() {
        // Prior to fix, this was throwing StringIndexOutOfBoundsException
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                70);
    }

    @Test
    @DisplayName("TEXT-123: wrapping with Integer.MAX_VALUE width no longer throws StringIndexOutOfBoundsException")
    void testText123() throws Exception {
        // Prior to fix, this was throwing StringIndexOutOfBoundsException
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                Integer.MAX_VALUE);
    }

    // -----------------------------------------------------------------------
    // constructor
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("WordUtils exposes a single public constructor and is a non-final public class")
    void testConstructor() {
        assertNotNull(new WordUtils());
        final Constructor<?>[] cons = WordUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(WordUtils.class.getModifiers()));
        assertFalse(Modifier.isFinal(WordUtils.class.getModifiers()));
    }

}
