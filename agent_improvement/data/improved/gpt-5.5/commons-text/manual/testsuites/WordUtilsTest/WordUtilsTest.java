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

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testAbbreviateForLowerThanMinusOneValues() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("01 23 45 67 89", 9, -10, null));
    }

    @Test
    void testAbbreviateForLowerValue() {
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        assertEquals("01234", WordUtils.abbreviate("01234 56789", 5, 10, null));
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 9, -1, null));
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, null));
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 15, 20, null));
    }

    @Test
    void testAbbreviateForLowerValueAndAppendedString() {
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));
        assertEquals("01234-", WordUtils.abbreviate("01234 56789", 5, 10, "-"));
        assertEquals("01 23 45 67abc", WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc"));
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, ""));
    }

    @Test
    void testAbbreviateForNullAndEmptyString() {
        assertNull(WordUtils.abbreviate(null, 1, -1, ""));
        assertEquals(StringUtils.EMPTY, WordUtils.abbreviate("", 1, -1, ""));
        assertEquals("", WordUtils.abbreviate("0123456790", 0, 0, ""));
        assertEquals("", WordUtils.abbreviate(" 0123456790", 0, -1, ""));
    }

    @Test
    void testAbbreviateForUpperLimit() {
        assertEquals("01234", WordUtils.abbreviate("0123456789", 0, 5, ""));
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, ""));
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }

    @Test
    void testAbbreviateForUpperLimitAndAppendedString() {
        assertEquals("01234-", WordUtils.abbreviate("0123456789", 0, 5, "-"));
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, null));
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }

    @Test
    void testAbbreviateUpperLessThanLowerValues() {
        assertThrows(IllegalArgumentException.class, () -> WordUtils.abbreviate("0123456789", 5, 2, ""));
    }

    @Test
    void testCapitalize_String() {
        assertNull(WordUtils.capitalize(null));
        assertEquals("", WordUtils.capitalize(""));
        assertEquals("  ", WordUtils.capitalize("  "));

        assertEquals("I", WordUtils.capitalize("I"));
        assertEquals("I", WordUtils.capitalize("i"));
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }

    @Test
    void testCapitalizeFully_String() {
        assertNull(WordUtils.capitalizeFully(null));
        assertEquals("", WordUtils.capitalizeFully(""));
        assertEquals("  ", WordUtils.capitalizeFully("  "));
        assertEquals("I", WordUtils.capitalizeFully("I"));
        assertEquals("I", WordUtils.capitalizeFully("i"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I Am Here 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am HERE 123"));
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet")); // single word
        assertEquals("A\tB\nC D", WordUtils.capitalizeFully("a\tb\nc d"));
        assertEquals("And \tBut \nCleat  Dome", WordUtils.capitalizeFully("and \tbut \ncleat  dome"));
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));

    }

    @Test
    void testCapitalizeFully_Text88() {
        assertEquals("I am fine now", WordUtils.capitalizeFully("i am fine now", new char[] {}));
    }

    @Test
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
        delimiters = new char[] { '.' };
        assertEquals("I am.Fine", WordUtils.capitalizeFully("i aM.fine", delimiters));
        assertEquals("I Am.fine", WordUtils.capitalizeFully("i am.fine", null));
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", null)); // single word
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet", new char[] { '!' })); // no matching delim
    }

    @Test
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
        delimiters = new char[] { '.' };
        assertEquals("I aM.Fine", WordUtils.capitalize("i aM.fine", delimiters));
        assertEquals("I Am.fine", WordUtils.capitalize("i am.fine", null));
    }

    @Test
    void testConstructor() {
        assertNotNull(new WordUtils());
        final Constructor<?>[] cons = WordUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(WordUtils.class.getModifiers()));
        assertFalse(Modifier.isFinal(WordUtils.class.getModifiers()));
    }

    @Test
    void testContainsAllWords_StringString() {
        assertFalse(WordUtils.containsAllWords(null));
        assertFalse(WordUtils.containsAllWords(null, ""));
        assertFalse(WordUtils.containsAllWords(null, "ab"));

        assertFalse(WordUtils.containsAllWords(""));
        assertFalse(WordUtils.containsAllWords("", (String) null));
        assertFalse(WordUtils.containsAllWords("", ""));
        assertFalse(WordUtils.containsAllWords("", "ab"));

        assertFalse(WordUtils.containsAllWords("foo"));
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
        assertFalse(WordUtils.containsAllWords("bar", ""));
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));
        assertFalse(WordUtils.containsAllWords("ab", "b"));
        assertFalse(WordUtils.containsAllWords("ab", "z"));
        assertFalse(WordUtils.containsAllWords("ab", "["));
        assertFalse(WordUtils.containsAllWords("ab", "]"));
        assertFalse(WordUtils.containsAllWords("ab", "*"));
        assertTrue(WordUtils.containsAllWords("ab x", "ab", "x"));
    }

    @Test
    void testContainsAllWordsWithNull() {
        assertFalse(WordUtils.containsAllWords("M", (CharSequence) null));
    }

    @Test
    void testInitials_String() {
        assertNull(WordUtils.initials(null));
        assertEquals("", WordUtils.initials(""));
        assertEquals("", WordUtils.initials("  "));

        assertEquals("I", WordUtils.initials("I"));
        assertEquals("i", WordUtils.initials("i"));
        assertEquals("BJL", WordUtils.initials("Ben John Lee"));
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"));
        assertEquals("iah1", WordUtils.initials("i am here 123"));
    }

    @Test
    void testInitials_String_charArray() {
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

        delimiters = " ".toCharArray();
        assertNull(WordUtils.initials(null, delimiters));
        assertEquals("", WordUtils.initials("", delimiters));
        assertEquals("", WordUtils.initials("  ", delimiters));
        assertEquals("I", WordUtils.initials("I", delimiters));
        assertEquals("i", WordUtils.initials("i", delimiters));
        assertEquals("S", WordUtils.initials("SJC", delimiters));
        assertEquals("BJL", WordUtils.initials("Ben John Lee", delimiters));
        assertEquals("BJ", WordUtils.initials("Ben J.Lee", delimiters));
        assertEquals("B\nJ", WordUtils.initials("   Ben \n   John\tLee\t", delimiters));
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee", delimiters));
        assertEquals("KO", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("iah1", WordUtils.initials("i am here 123", delimiters));

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
        assertEquals("KOM", WordUtils.initials("Kay O'Murphy", delimiters));
        assertEquals("iah1", WordUtils.initials("i am here 123", delimiters));

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
    void testInitialsSurrogatePairs() {
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01 \uD800\uDF02\uD800\uDF03"));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01 \uD800\uDF02\uD800\uDF03", null));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00 \uD800\uDF02 ", null));

        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01.\uD800\uDF02\uD800\uDF03", new char[] { '.' }));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01A\uD800\uDF02\uD800\uDF03", new char[] { 'A' }));

        assertEquals("\uD800\uDF00\uD800\uDF02",
                WordUtils.initials("\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF02\uD800\uDF03", new char[] { '\uD800', '\uDF14' }));
        assertEquals("\uD800\uDF00\uD800\uDF02", WordUtils.initials("\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF18\uD800\uDF02\uD800\uDF03",
                new char[] { '\uD800', '\uDF14', '\uD800', '\uDF18' }));
    }

    @Test
    void testIsDelimiter() {
        assertFalse(WordUtils.isDelimiter('.', null));
        assertTrue(WordUtils.isDelimiter(' ', null));

        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.' }));

        assertFalse(WordUtils.isDelimiter(' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter('.', new char[] { '.', '_', 'a', '.' }));
    }

    @Test
    void testIsDelimiterCodePoint() {
        assertFalse(WordUtils.isDelimiter((int) '.', null));
        assertTrue(WordUtils.isDelimiter((int) ' ', null));

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.' }));
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.' }));

        assertFalse(WordUtils.isDelimiter((int) ' ', new char[] { '.', '_', 'a' }));
        assertTrue(WordUtils.isDelimiter((int) '.', new char[] { '.', '_', 'a', '.' }));
    }

    @Test
    void testLANG1292() {
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                70);
    }

    @Test
    void testLANG673() {
        assertEquals("01", WordUtils.abbreviate("01 23 45 67 89", 0, 40, ""));
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 10, 40, ""));
        assertEquals("01 23 45 67 89", WordUtils.abbreviate("01 23 45 67 89", 40, 40, ""));
    }

    @Test
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

        final String test = "This String contains a TitleCase character: \u01C8";
        final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";
        assertEquals(expect, WordUtils.swapCase(test));
    }

    @Test
    void testText123() throws Exception {
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                Integer.MAX_VALUE);
    }

    @Test
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
        assertEquals(WHITESPACE, WordUtils.capitalizeFully(WHITESPACE));
        assertEquals("A" + WHITESPACE + "B", WordUtils.capitalizeFully("a" + WHITESPACE + "b"));
    }

    @Test
    void testUnCapitalize_Text88() {
        assertEquals("i am fine now", WordUtils.uncapitalize("I am fine now", new char[] {}));
    }

    @Test
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
        delimiters = new char[] { '.' };
        assertEquals("i AM.fINE", WordUtils.uncapitalize("I AM.FINE", delimiters));
        assertEquals("i aM.FINE", WordUtils.uncapitalize("I AM.FINE", null));
    }

    @Test
    void testWrap_StringInt() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));

        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));

        final String systemNewLine = System.lineSeparator();

        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20));

        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump" + systemNewLine + "to the commons" + systemNewLine + "website -" + systemNewLine + "https://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20));

        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here," + systemNewLine + "https://commons.apache.org," + systemNewLine + "to jump to the" + systemNewLine + "commons website";
        assertEquals(expected, WordUtils.wrap(input, 20));

        input = "word1             word2                        word3";
        expected = "word1  " + systemNewLine + "word2  " + systemNewLine + "word3";
        assertEquals(expected, WordUtils.wrap(input, 7));
    }

    @Test
    void testWrap_StringIntStringBoolean() {
        assertNull(WordUtils.wrap(null, 20, "\n", false));
        assertNull(WordUtils.wrap(null, 20, "\n", true));
        assertNull(WordUtils.wrap(null, 20, null, true));
        assertNull(WordUtils.wrap(null, 20, null, false));
        assertNull(WordUtils.wrap(null, -1, null, true));
        assertNull(WordUtils.wrap(null, -1, null, false));

        assertEquals("", WordUtils.wrap("", 20, "\n", false));
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
        assertEquals("", WordUtils.wrap("", 20, null, false));
        assertEquals("", WordUtils.wrap("", 20, null, true));
        assertEquals("", WordUtils.wrap("", -1, null, false));
        assertEquals("", WordUtils.wrap("", -1, null, true));

        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", true));

        input = "Here is one line";
        expected = "Here\nis one\nline";
        assertEquals(expected, WordUtils.wrap(input, 6, "\n", false));
        expected = "Here\nis\none\nline";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, -1, "\n", false));

        final String systemNewLine = System.lineSeparator();
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, null, false));
        assertEquals(expected, WordUtils.wrap(input, 20, null, true));

        input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        expected = "Here:  is  one  line\nof  text  that  is \ngoing  to  be \nwrapped  after  20 \ncolumns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        expected = "Here is\tone line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        expected = "Here is one line\nof\ttext that is\ngoing to be wrapped\nafter 20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apac\nhe.org";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));

        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        expected = "Click here,\nhttps://commons.apac\nhe.org, to jump to\nthe commons website";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    void testWrap_StringIntStringBooleanString() {
        String input = "flammable/inflammable";
        String expected = "flammable/inflammable";
        assertEquals(expected, WordUtils.wrap(input, 30, "\n", false, "/"));

        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false, "/"));

        expected = "flammable\ninflammab\nle";
        assertEquals(expected, WordUtils.wrap(input, 9, "\n", true, "/"));

        expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));

        input = "flammableinflammable";
        expected = "flammableinflam\nmable";
        assertEquals(expected, WordUtils.wrap(input, 15, "\n", true, "/"));
    }

    @Test
    void testWrapAtMiddleTwice() {
        assertEquals("abcdef\n\nabcdef", WordUtils.wrap("abcdefggabcdef", 2, "\n", false, "(?=g)"));
    }

    @Test
    void testWrapAtStartAndEnd() {
        assertEquals("\nabcdefabcdef\n", WordUtils.wrap("nabcdefabcdefn", 2, "\n", false, "(?=n)"));
    }

    @Test
    void testWrapWithMultipleRegexMatchOfLength0() {
        assertEquals("abc\ndefabc\ndef", WordUtils.wrap("abcdefabcdef", 2, "\n", false, "(?=d)"));
    }

    @Test
    void testWrapWithRegexMatchOfLength0() {
        assertEquals("abc\ndef", WordUtils.wrap("abcdef", 2, "\n", false, "(?=d)"));
    }

    @Test
    void testZeroWidthWrapOnRegex() {
        assertTimeout(Duration.ofSeconds(2), () -> assertNotNull(WordUtils.wrap("abcdef", 3, "\n", false, "(?=a)")));
    }

}
