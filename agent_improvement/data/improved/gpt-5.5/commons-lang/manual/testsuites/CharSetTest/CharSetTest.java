/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet}.
 */
class CharSetTest extends AbstractLangTest {

    private static CharSet assertRanges(final String pattern, final CharRange... expectedRanges) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();
        assertEquals(expectedRanges.length, ranges.size());
        for (final CharRange expectedRange : expectedRanges) {
            assertTrue(ranges.contains(expectedRange));
        }
        return set;
    }

    private static void assertContains(final CharSet set, final char ch, final boolean expected) {
        assertEquals(expected, set.contains(ch));
    }

    private static void assertSingleRangeString(final String pattern, final String expectedSetString, final String expectedRangeString) {
        final CharSet set = CharSet.getInstance(pattern);
        final Set<CharRange> ranges = set.getCharRanges();
        assertEquals(expectedSetString, set.toString());
        assertEquals(1, ranges.size());
        assertEquals(expectedRangeString, ranges.iterator().next().toString());
    }

    @Test
    void testClass() {
        assertTrue(Modifier.isPublic(CharSet.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSet.class.getModifiers()));
    }

    @Test
    void testConstructor_String_combo() {
        assertRanges("abc",
                CharRange.is('a'),
                CharRange.is('b'),
                CharRange.is('c'));

        assertRanges("a-ce-f",
                CharRange.isIn('a', 'c'),
                CharRange.isIn('e', 'f'));

        assertRanges("ae-f",
                CharRange.is('a'),
                CharRange.isIn('e', 'f'));

        assertRanges("e-fa",
                CharRange.is('a'),
                CharRange.isIn('e', 'f'));

        assertRanges("ae-fm-pz",
                CharRange.is('a'),
                CharRange.isIn('e', 'f'),
                CharRange.isIn('m', 'p'),
                CharRange.is('z'));
    }

    @Test
    void testConstructor_String_comboNegated() {
        assertRanges("^abc",
                CharRange.isNot('a'),
                CharRange.is('b'),
                CharRange.is('c'));

        assertRanges("b^ac",
                CharRange.is('b'),
                CharRange.isNot('a'),
                CharRange.is('c'));

        assertRanges("db^ac",
                CharRange.is('d'),
                CharRange.is('b'),
                CharRange.isNot('a'),
                CharRange.is('c'));

        assertRanges("^b^a",
                CharRange.isNot('b'),
                CharRange.isNot('a'));

        assertRanges("b^a-c^z",
                CharRange.isNotIn('a', 'c'),
                CharRange.isNot('z'),
                CharRange.is('b'));
    }

    @Test
    void testConstructor_String_oddCombinations() {
        CharSet set = assertRanges("a-^c",
                CharRange.isIn('a', '^'),
                CharRange.is('c'));
        assertContains(set, 'b', false);
        assertContains(set, '^', true);
        assertContains(set, '_', true);
        assertContains(set, 'c', true);

        set = assertRanges("^a-^c",
                CharRange.isNotIn('a', '^'),
                CharRange.is('c'));
        assertContains(set, 'b', true);
        assertContains(set, '^', false);
        assertContains(set, '_', false);

        set = assertRanges("a- ^-- ",
                CharRange.isIn('a', ' '),
                CharRange.isNotIn('-', ' '));
        assertContains(set, '#', true);
        assertContains(set, '^', true);
        assertContains(set, 'a', true);
        assertContains(set, '*', true);
        assertContains(set, 'A', true);

        set = assertRanges("^-b",
                CharRange.isIn('^', 'b'));
        assertContains(set, 'b', true);
        assertContains(set, '_', true);
        assertContains(set, 'A', false);
        assertContains(set, '^', true);

        set = assertRanges("b-^",
                CharRange.isIn('^', 'b'));
        assertContains(set, 'b', true);
        assertContains(set, '^', true);
        assertContains(set, 'a', true);
        assertContains(set, 'c', false);
    }

    @Test
    void testConstructor_String_oddDash() {
        assertRanges("-", CharRange.is('-'));
        assertRanges("--", CharRange.is('-'));
        assertRanges("---", CharRange.is('-'));
        assertRanges("----", CharRange.is('-'));
        assertRanges("-a", CharRange.is('-'), CharRange.is('a'));
        assertRanges("a-", CharRange.is('a'), CharRange.is('-'));
        assertRanges("a--", CharRange.isIn('a', '-'));
        assertRanges("--a", CharRange.isIn('-', 'a'));
    }

    @Test
    void testConstructor_String_oddNegate() {
        assertRanges("^", CharRange.is('^'));
        assertRanges("^^", CharRange.isNot('^'));
        assertRanges("^^^", CharRange.isNot('^'), CharRange.is('^'));
        assertRanges("^^^^", CharRange.isNot('^'));
        assertRanges("a^", CharRange.is('a'), CharRange.is('^'));
        assertRanges("^a-", CharRange.isNot('a'), CharRange.is('-'));
        assertRanges("^^-c", CharRange.isNotIn('^', 'c'));
        assertRanges("^c-^", CharRange.isNotIn('c', '^'));
        assertRanges("^c-^d", CharRange.isNotIn('c', '^'), CharRange.is('d'));
        assertRanges("^^-", CharRange.isNot('^'), CharRange.is('-'));
    }

    @Test
    void testConstructor_String_simple() {
        CharSet set = CharSet.getInstance((String) null);
        Set<CharRange> ranges = set.getCharRanges();
        assertEquals("[]", set.toString());
        assertEquals(0, ranges.size());

        set = CharSet.getInstance("");
        ranges = set.getCharRanges();
        assertEquals("[]", set.toString());
        assertEquals(0, ranges.size());

        assertSingleRangeString("a", "[a]", "a");
        assertSingleRangeString("^a", "[^a]", "^a");
        assertSingleRangeString("a-e", "[a-e]", "a-e");
        assertSingleRangeString("^a-e", "[^a-e]", "^a-e");
    }

    @Test
    void testContains_Char() {
        final CharSet btod = CharSet.getInstance("b-d");
        final CharSet dtob = CharSet.getInstance("d-b");
        final CharSet bcd = CharSet.getInstance("bcd");
        final CharSet bd = CharSet.getInstance("bd");
        final CharSet notbtod = CharSet.getInstance("^b-d");

        assertContains(btod, 'a', false);
        assertContains(btod, 'b', true);
        assertContains(btod, 'c', true);
        assertContains(btod, 'd', true);
        assertContains(btod, 'e', false);

        assertContains(bcd, 'a', false);
        assertContains(bcd, 'b', true);
        assertContains(bcd, 'c', true);
        assertContains(bcd, 'd', true);
        assertContains(bcd, 'e', false);

        assertContains(bd, 'a', false);
        assertContains(bd, 'b', true);
        assertContains(bd, 'c', false);
        assertContains(bd, 'd', true);
        assertContains(bd, 'e', false);

        assertContains(notbtod, 'a', true);
        assertContains(notbtod, 'b', false);
        assertContains(notbtod, 'c', false);
        assertContains(notbtod, 'd', false);
        assertContains(notbtod, 'e', true);

        assertContains(dtob, 'a', false);
        assertContains(dtob, 'b', true);
        assertContains(dtob, 'c', true);
        assertContains(dtob, 'd', true);
        assertContains(dtob, 'e', false);

        final Set<CharRange> ranges = dtob.getCharRanges();
        assertEquals("[b-d]", dtob.toString());
        assertEquals(1, ranges.size());
    }

    @Test
    void testEquals_Object() {
        final CharSet abc = CharSet.getInstance("abc");
        final CharSet abc2 = CharSet.getInstance("abc");
        final CharSet atoc = CharSet.getInstance("a-c");
        final CharSet atoc2 = CharSet.getInstance("a-c");
        final CharSet notatoc = CharSet.getInstance("^a-c");
        final CharSet notatoc2 = CharSet.getInstance("^a-c");

        assertNotEquals(null, abc);

        assertEquals(abc, abc);
        assertEquals(abc, abc2);
        assertNotEquals(abc, atoc);
        assertNotEquals(abc, notatoc);

        assertNotEquals(atoc, abc);
        assertEquals(atoc, atoc);
        assertEquals(atoc, atoc2);
        assertNotEquals(atoc, notatoc);

        assertNotEquals(notatoc, abc);
        assertNotEquals(notatoc, atoc);
        assertEquals(notatoc, notatoc);
        assertEquals(notatoc, notatoc2);
    }

    @Test
    void testGetInstance() {
        assertSame(CharSet.EMPTY, CharSet.getInstance((String) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance((String[]) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(""));
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"));
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"));
        assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"));
        assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"));
        assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"));
    }

    @Test
    void testGetInstance_Stringarray() {
        assertEquals("[]", CharSet.getInstance((String[]) null).toString());
        assertEquals("[]", CharSet.getInstance().toString());
        assertEquals("[]", CharSet.getInstance(new String[] {null}).toString());
        assertEquals("[a-e]", CharSet.getInstance("a-e").toString());
    }

    @Test
    void testHashCode() {
        final CharSet abc = CharSet.getInstance("abc");
        final CharSet abc2 = CharSet.getInstance("abc");
        final CharSet atoc = CharSet.getInstance("a-c");
        final CharSet atoc2 = CharSet.getInstance("a-c");
        final CharSet notatoc = CharSet.getInstance("^a-c");
        final CharSet notatoc2 = CharSet.getInstance("^a-c");

        assertEquals(abc.hashCode(), abc.hashCode());
        assertEquals(abc.hashCode(), abc2.hashCode());
        assertEquals(atoc.hashCode(), atoc.hashCode());
        assertEquals(atoc.hashCode(), atoc2.hashCode());
        assertEquals(notatoc.hashCode(), notatoc.hashCode());
        assertEquals(notatoc.hashCode(), notatoc2.hashCode());
    }

    @Test
    void testJavadocExamples() {
        assertFalse(CharSet.getInstance("^a-c").contains('a'));
        assertTrue(CharSet.getInstance("^a-c").contains('d'));
        assertTrue(CharSet.getInstance("^^a-c").contains('a'));
        assertFalse(CharSet.getInstance("^^a-c").contains('^'));
        assertTrue(CharSet.getInstance("^a-cd-f").contains('d'));
        assertTrue(CharSet.getInstance("a-c^").contains('^'));
        assertTrue(CharSet.getInstance("^", "a-c").contains('^'));
    }

    @Test
    void testSerialization() {
        CharSet set = CharSet.getInstance("a");
        assertEquals(set, SerializationUtils.clone(set));
        set = CharSet.getInstance("a-e");
        assertEquals(set, SerializationUtils.clone(set));
        set = CharSet.getInstance("be-f^a-z");
        assertEquals(set, SerializationUtils.clone(set));
    }

    @Test
    void testStatics() {
        Set<CharRange> ranges;

        ranges = CharSet.EMPTY.getCharRanges();
        assertEquals(0, ranges.size());

        ranges = CharSet.ASCII_ALPHA.getCharRanges();
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', 'z')));
        assertTrue(ranges.contains(CharRange.isIn('A', 'Z')));

        ranges = CharSet.ASCII_ALPHA_LOWER.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', 'z')));

        ranges = CharSet.ASCII_ALPHA_UPPER.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('A', 'Z')));

        ranges = CharSet.ASCII_NUMERIC.getCharRanges();
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('0', '9')));
    }
}
