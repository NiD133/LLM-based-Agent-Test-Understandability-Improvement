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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link StringTokenizer}.
 */
class StringTokenizerTest {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    private void assertDelimiterMatchesSpace(final StringTokenizer tokenizer) {
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ", 0, 0, 1));
    }

    private void assertDoubleQuoteMatcher(final StringTokenizer tokenizer) {
        assertEquals(1, tokenizer.getQuoteMatcher().isMatch("\"".toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getQuoteMatcher().isMatch("\"", 0, 0, 1));
    }

    private void assertNextTokens(final StringTokenizer tokenizer, final String... expectedTokens) {
        for (final String expectedToken : expectedTokens) {
            assertEquals(expectedToken, tokenizer.next());
        }
        assertFalse(tokenizer.hasNext());
    }

    private void assertTokenArray(final StringTokenizer tokenizer, final String... expectedTokens) {
        final String[] actualTokens = tokenizer.getTokenArray();
        assertEquals(expectedTokens.length, actualTokens.length, Arrays.toString(actualTokens));
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], actualTokens[i],
                    "token[" + i + "] was '" + actualTokens[i] + "' but was expected to be '" + expectedTokens[i] + "'");
        }
    }

    private void checkClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    @Test
    void test1() {
        final String input = "a;b;c;\"d;\"\"e\";f; ; ;  ";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        assertTokenArray(tokenizer, "a", "b", "c", "d;\"e", "f", "", "", "");
    }

    @Test
    void test2() {
        final String input = "a;b;c ;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        assertTokenArray(tokenizer, "a", "b", "c ", "d;\"e", "f", " ", " ", "");
    }

    @Test
    void test3() {
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        assertTokenArray(tokenizer, "a", "b", " c", "d;\"e", "f", " ", " ", "");
    }

    @Test
    void test4() {
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(true);

        assertTokenArray(tokenizer, "a", "b", "c", "d;\"e", "f");
    }

    @Test
    void test5() {
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertTokenArray(tokenizer, "a", "b", "c", "d;\"e", "f", null, null, null);
    }

    @Test
    void test6() {
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        final String[] tokens = tokenizer.getTokenArray();

        final String[] expected = {"a", "b", " c", "d;\"e", "f", null, null, null };

        int nextCount = 0;
        while (tokenizer.hasNext()) {
            tokenizer.next();
            nextCount++;
        }

        int previousCount = 0;
        while (tokenizer.hasPrevious()) {
            tokenizer.previous();
            previousCount++;
        }

        assertEquals(expected.length, tokens.length, Arrays.toString(tokens));
        assertEquals(nextCount, expected.length, "could not cycle through entire token list using the 'hasNext' and 'next' methods");
        assertEquals(previousCount, expected.length, "could not cycle through entire token list using the 'hasPrevious' and 'previous' methods");
    }

    @Test
    void test7() {
        final String input = "a   b c \"d e\" f ";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        assertTokenArray(tokenizer, "a", "", "", "b", "c", "d e", "f", "");
    }

    @Test
    void test8() {
        final String input = "a   b c \"d e\" f ";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(true);

        assertTokenArray(tokenizer, "a", "b", "c", "d e", "f");
    }

    @Test
    void testBasic1() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testBasic2() {
        final String input = "a \nb\fc";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testBasic3() {
        final String input = "a \nb\u0001\fc";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        assertNextTokens(tokenizer, "a", "b\u0001", "c");
    }

    @Test
    void testBasic4() {
        final String input = "a \"b\" c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        assertNextTokens(tokenizer, "a", "\"b\"", "c");
    }

    @Test
    void testBasic5() {
        final String input = "a:b':c";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        assertNextTokens(tokenizer, "a", "b'", "c");
    }

    @Test
    void testBasicDelim1() {
        final String input = "a:b:c";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testBasicDelim2() {
        final String input = "a:b:c";
        final StringTokenizer tokenizer = new StringTokenizer(input, ',');
        assertNextTokens(tokenizer, "a:b:c");
    }

    @Test
    void testBasicEmpty1() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);
        assertNextTokens(tokenizer, "a", "", "b", "c");
    }

    @Test
    void testBasicEmpty2() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", null, "b", "c");
    }

    @Test
    void testBasicIgnoreTrimmed1() {
        final String input = "a: bIGNOREc : ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "bc", null);
    }

    @Test
    void testBasicIgnoreTrimmed2() {
        final String input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "bc", null);
    }

    @Test
    void testBasicIgnoreTrimmed3() {
        final String input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "  bc  ", "  ");
    }

    @Test
    void testBasicIgnoreTrimmed4() {
        final String input = "IGNOREaIGNORE: IGNORE 'bIGNOREc'IGNORE'd' IGNORE : IGNORE ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "bIGNOREcd", null);
    }

    @Test
    void testBasicQuoted1() {
        final String input = "a 'b' c";
        final StringTokenizer tokenizer = new StringTokenizer(input, ' ', '\'');
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testBasicQuoted2() {
        final String input = "a:'b':";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "b", null);
    }

    @Test
    void testBasicQuoted3() {
        final String input = "a:'b''c'";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "b'c");
    }

    @Test
    void testBasicQuoted4() {
        final String input = "a: 'b' 'c' :d";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "b c", "d");
    }

    @Test
    void testBasicQuoted5() {
        final String input = "a: 'b'x'c' :d";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "bxc", "d");
    }

    @Test
    void testBasicQuoted6() {
        final String input = "a:'b'\"c':d";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());
        assertNextTokens(tokenizer, "a", "b\"c:d");
    }

    @Test
    void testBasicQuoted7() {
        final String input = "a:\"There's a reason here\":b";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());
        assertNextTokens(tokenizer, "a", "There's a reason here", "b");
    }

    @Test
    void testBasicQuotedTrimmed1() {
        final String input = "a: 'b' :";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "b", null);
    }

    @Test
    void testBasicTrimmed1() {
        final String input = "a: b :  ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "b", null);
    }

    @Test
    void testBasicTrimmed2() {
        final String input = "a:  b  :";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.stringMatcher("  "));
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        assertNextTokens(tokenizer, "a", "b", null);
    }

    @Test
    void testChaining() {
        final StringTokenizer tokenizer = new StringTokenizer();
        assertEquals(tokenizer, tokenizer.reset());
        assertEquals(tokenizer, tokenizer.reset(""));
        assertEquals(tokenizer, tokenizer.reset(ArrayUtils.EMPTY_CHAR_ARRAY));
        assertEquals(tokenizer, tokenizer.setDelimiterChar(' '));
        assertEquals(tokenizer, tokenizer.setDelimiterString(" "));
        assertEquals(tokenizer, tokenizer.setDelimiterMatcher(null));
        assertEquals(tokenizer, tokenizer.setQuoteChar(' '));
        assertEquals(tokenizer, tokenizer.setQuoteMatcher(null));
        assertEquals(tokenizer, tokenizer.setIgnoredChar(' '));
        assertEquals(tokenizer, tokenizer.setIgnoredMatcher(null));
        assertEquals(tokenizer, tokenizer.setTrimmerMatcher(null));
        assertEquals(tokenizer, tokenizer.setEmptyTokenAsNull(false));
        assertEquals(tokenizer, tokenizer.setIgnoreEmptyTokens(false));
    }

    /**
     * Tests that the {@link StringTokenizer#clone()} clone method catches {@link CloneNotSupportedException} and
     * returns {@code null}.
     */
    @Test
    void testCloneNotSupportedException() {
        final Object notCloned = new StringTokenizer() {

            @Override
            Object cloneReset() throws CloneNotSupportedException {
                throw new CloneNotSupportedException("test");
            }
        }.clone();
        assertNull(notCloned);
    }

    @Test
    void testCloneNull() {
        final StringTokenizer tokenizer = new StringTokenizer((char[]) null);
        assertNull(tokenizer.nextToken());
        tokenizer.reset();
        assertNull(tokenizer.nextToken());

        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();
        tokenizer.reset();
        assertNull(tokenizer.nextToken());
        assertNull(clonedTokenizer.nextToken());
    }

    @Test
    void testCloneReset() {
        final char[] input = { 'a' };
        final StringTokenizer tokenizer = new StringTokenizer(input);
        assertEquals("a", tokenizer.nextToken());
        tokenizer.reset(input);
        assertEquals("a", tokenizer.nextToken());

        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();
        input[0] = 'b';
        tokenizer.reset(input);
        assertEquals("b", tokenizer.nextToken());
        assertEquals("a", clonedTokenizer.nextToken());
    }

    @Test
    void testConstructor_charArray() {
        StringTokenizer tokenizer = new StringTokenizer("a b".toCharArray());
        assertNextTokens(tokenizer, "a", "b");

        tokenizer = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY);
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((char[]) null);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testConstructor_charArray_char() {
        StringTokenizer tokenizer = new StringTokenizer("a b".toCharArray(), ' ');
        assertDelimiterMatchesSpace(tokenizer);
        assertNextTokens(tokenizer, "a", "b");

        tokenizer = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, ' ');
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((char[]) null, ' ');
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testConstructor_charArray_char_char() {
        StringTokenizer tokenizer = new StringTokenizer("a b".toCharArray(), ' ', '"');
        assertDelimiterMatchesSpace(tokenizer);
        assertDoubleQuoteMatcher(tokenizer);
        assertNextTokens(tokenizer, "a", "b");

        tokenizer = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, ' ', '"');
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((char[]) null, ' ', '"');
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testConstructor_String() {
        StringTokenizer tokenizer = new StringTokenizer("a b");
        assertNextTokens(tokenizer, "a", "b");

        tokenizer = new StringTokenizer("");
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((String) null);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testConstructor_String_char() {
        StringTokenizer tokenizer = new StringTokenizer("a b", ' ');
        assertDelimiterMatchesSpace(tokenizer);
        assertNextTokens(tokenizer, "a", "b");

        tokenizer = new StringTokenizer("", ' ');
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((String) null, ' ');
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testConstructor_String_char_char() {
        StringTokenizer tokenizer = new StringTokenizer("a b", ' ', '"');
        assertDelimiterMatchesSpace(tokenizer);
        assertDoubleQuoteMatcher(tokenizer);
        assertNextTokens(tokenizer, "a", "b");

        tokenizer = new StringTokenizer("", ' ', '"');
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((String) null, ' ', '"');
        assertFalse(tokenizer.hasNext());
    }

    private void testCSV(final String data) {
        testXSVAbc(StringTokenizer.getCSVInstance(data));
        testXSVAbc(StringTokenizer.getCSVInstance(data.toCharArray()));
    }

    @Test
    void testCSVEmpty() {
        testEmpty(StringTokenizer.getCSVInstance());
        testEmpty(StringTokenizer.getCSVInstance(""));
    }

    @Test
    void testCSVSimple() {
        testCSV(CSV_SIMPLE_FIXTURE);
    }

    @Test
    void testCSVSimpleNeedsTrim() {
        testCSV("   " + CSV_SIMPLE_FIXTURE);
        testCSV("   \n\t  " + CSV_SIMPLE_FIXTURE);
        testCSV("   \n  " + CSV_SIMPLE_FIXTURE + "\n\n\r");
    }

    @Test
    void testDelimMatcher() {
        final String input = "a/b\\c";
        final StringMatcher delimiterMatcher = StringMatcherFactory.INSTANCE.charSetMatcher('/', '\\');

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiterMatcher);
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testDelimMatcherQuoteMatcher() {
        final String input = "`a`;`b`;`c`";
        final StringMatcher delimiterMatcher = StringMatcherFactory.INSTANCE.charSetMatcher(';');
        final StringMatcher quoteMatcher = StringMatcherFactory.INSTANCE.charSetMatcher('`');

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiterMatcher, quoteMatcher);
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testDelimString() {
        final String input = "a##b##c";
        final StringTokenizer tokenizer = new StringTokenizer(input, "##");
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    void testEmpty(final StringTokenizer tokenizer) {
        checkClone(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    @Test
    void testGetContent() {
        final String input = "a   b c \"d e\" f ";
        StringTokenizer tokenizer = new StringTokenizer(input);
        assertEquals(input, tokenizer.getContent());

        tokenizer = new StringTokenizer(input.toCharArray());
        assertEquals(input, tokenizer.getContent());

        tokenizer = new StringTokenizer();
        assertNull(tokenizer.getContent());
    }

    @Test
    void testIteration() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c");
        assertFalse(tokenizer.hasPrevious());
        assertThrows(NoSuchElementException.class, tokenizer::previous);
        assertTrue(tokenizer.hasNext());

        assertEquals("a", tokenizer.next());
        assertThrows(UnsupportedOperationException.class, tokenizer::remove);
        assertThrows(UnsupportedOperationException.class, () -> tokenizer.set("x"));
        assertThrows(UnsupportedOperationException.class, () -> tokenizer.add("y"));
        assertTrue(tokenizer.hasPrevious());
        assertTrue(tokenizer.hasNext());

        assertEquals("b", tokenizer.next());
        assertTrue(tokenizer.hasPrevious());
        assertTrue(tokenizer.hasNext());

        assertEquals("c", tokenizer.next());
        assertTrue(tokenizer.hasPrevious());
        assertFalse(tokenizer.hasNext());

        assertThrows(NoSuchElementException.class, tokenizer::next);
        assertTrue(tokenizer.hasPrevious());
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testListArray() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        final String[] array = tokenizer.getTokenArray();
        final List<String> list = tokenizer.getTokenList();

        assertEquals(Arrays.asList(array), list);
        assertEquals(3, list.size());

        list.set(0, "z");
        list.remove(1);
        list.set(1, "y");
        list.add("x");

        assertEquals(Arrays.asList("z", "y", "x"), list);
        assertEquals(Arrays.asList(array), tokenizer.getTokenList());
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }

    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        tokenizer.setEmptyTokenAsNull(true);

        assertNull(tokenizer.previousToken());
    }

    @Test
    void testReset() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c");
        assertNextTokens(tokenizer, "a", "b", "c");

        tokenizer.reset();
        assertNextTokens(tokenizer, "a", "b", "c");
    }

    @Test
    void testReset_charArray() {
        final StringTokenizer tokenizer = new StringTokenizer("x x x");

        final char[] array = {'a', 'b', 'c' };
        tokenizer.reset(array);
        assertNextTokens(tokenizer, "abc");

        tokenizer.reset((char[]) null);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testReset_String() {
        final StringTokenizer tokenizer = new StringTokenizer("x x x");
        tokenizer.reset("d e");
        assertNextTokens(tokenizer, "d", "e");

        tokenizer.reset((String) null);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testStringTokenizerQuoteMatcher() {
        final char[] chars = {'\'', 'a', 'c', '\'', 'd' };
        final StringTokenizer tokenizer = new StringTokenizer(chars, StringMatcherFactory.INSTANCE.commaMatcher(),
                StringMatcherFactory.INSTANCE.quoteMatcher());
        assertEquals("acd", tokenizer.next());
    }

    @Test
    void testStringTokenizerStringMatcher() {
        final char[] chars = {'a', 'b', 'c', 'd' };
        final StringTokenizer tokenizer = new StringTokenizer(chars, "bc");
        assertEquals("a", tokenizer.next());
        assertEquals("d", tokenizer.next());
    }

    @Test
    void testStringTokenizerStrMatcher() {
        final char[] chars = {'a', ',', 'c' };
        final StringTokenizer tokenizer = new StringTokenizer(chars, StringMatcherFactory.INSTANCE.commaMatcher());
        assertEquals("a", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }

    @Test
    void testTokenizeSubclassInputChange() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c d e") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                return super.tokenize("w x y z".toCharArray(), 2, 5);
            }
        };
        assertEquals("x", tokenizer.next());
        assertEquals("y", tokenizer.next());
    }

    @Test
    void testTokenizeSubclassOutputChange() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                final List<String> list = super.tokenize(chars, offset, count);
                Collections.reverse(list);
                return list;
            }
        };
        assertEquals("c", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("a", tokenizer.next());
    }

    @Test
    void testToString() {
        final StringTokenizer tokenizer = new StringTokenizer("a b c d e");
        assertEquals("StringTokenizer[not tokenized yet]", tokenizer.toString());
        tokenizer.next();
        assertEquals("StringTokenizer[a, b, c, d, e]", tokenizer.toString());
    }

    @Test
    void testTSV() {
        testXSVAbc(StringTokenizer.getTSVInstance(TSV_SIMPLE_FIXTURE));
        testXSVAbc(StringTokenizer.getTSVInstance(TSV_SIMPLE_FIXTURE.toCharArray()));
    }

    @Test
    void testTSVEmpty() {
        testEmpty(StringTokenizer.getTSVInstance());
        testEmpty(StringTokenizer.getTSVInstance(""));
    }

    void testXSVAbc(final StringTokenizer tokenizer) {
        checkClone(tokenizer);
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(3, tokenizer.size());
    }
}
