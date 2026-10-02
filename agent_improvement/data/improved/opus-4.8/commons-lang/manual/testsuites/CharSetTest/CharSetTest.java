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

    /**
     * Parses {@code definition} and returns the resulting set of {@link CharRange} objects.
     * Centralizes the {@code getInstance(...).getCharRanges()} call repeated throughout these tests.
     */
    private static Set<CharRange> rangesOf(final String definition) {
        return CharSet.getInstance(definition).getCharRanges();
    }

    @Test
    void testClass() {
        assertTrue(Modifier.isPublic(CharSet.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSet.class.getModifiers()));
    }

    @Test
    void testConstructor_String_combo() {
        // "abc" -> three single-character ranges
        Set<CharRange> ranges = rangesOf("abc");
        assertEquals(3, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.is('b')));
        assertTrue(ranges.contains(CharRange.is('c')));

        // "a-ce-f" -> two adjacent multi-character ranges
        ranges = rangesOf("a-ce-f");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', 'c')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));

        // "ae-f" -> a single character followed by a range
        ranges = rangesOf("ae-f");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));

        // "e-fa" -> a range followed by a single character
        ranges = rangesOf("e-fa");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));

        // "ae-fm-pz" -> singles and ranges interleaved
        ranges = rangesOf("ae-fm-pz");
        assertEquals(4, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
        assertTrue(ranges.contains(CharRange.isIn('m', 'p')));
        assertTrue(ranges.contains(CharRange.is('z')));
    }

    @Test
    void testConstructor_String_comboNegated() {
        // "^abc" -> negated single 'a', then plain 'b' and 'c'
        Set<CharRange> ranges = rangesOf("^abc");
        assertEquals(3, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('a')));
        assertTrue(ranges.contains(CharRange.is('b')));
        assertTrue(ranges.contains(CharRange.is('c')));

        // "b^ac" -> negation may appear mid-string
        ranges = rangesOf("b^ac");
        assertEquals(3, ranges.size());
        assertTrue(ranges.contains(CharRange.is('b')));
        assertTrue(ranges.contains(CharRange.isNot('a')));
        assertTrue(ranges.contains(CharRange.is('c')));

        // "db^ac" -> leading single before a mid-string negation
        ranges = rangesOf("db^ac");
        assertEquals(4, ranges.size());
        assertTrue(ranges.contains(CharRange.is('d')));
        assertTrue(ranges.contains(CharRange.is('b')));
        assertTrue(ranges.contains(CharRange.isNot('a')));
        assertTrue(ranges.contains(CharRange.is('c')));

        // "^b^a" -> two negated singles
        ranges = rangesOf("^b^a");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('b')));
        assertTrue(ranges.contains(CharRange.isNot('a')));

        // "b^a-c^z" -> single, negated range, then negated single
        ranges = rangesOf("b^a-c^z");
        assertEquals(3, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('a', 'c')));
        assertTrue(ranges.contains(CharRange.isNot('z')));
        assertTrue(ranges.contains(CharRange.is('b')));
    }

    @Test
    void testConstructor_String_oddCombinations() {
        // "a-^c" parses as range "a-^" plus single "c"
        CharSet set = CharSet.getInstance("a-^c");
        Set<CharRange> ranges = set.getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('a', '^'))); // "a-^"
        assertTrue(ranges.contains(CharRange.is('c'))); // "c"
        assertFalse(set.contains('b'));
        assertTrue(set.contains('^'));
        assertTrue(set.contains('_')); // between ^ and a
        assertTrue(set.contains('c'));

        // "^a-^c" parses as negated range "^a-^" plus single "c"
        set = CharSet.getInstance("^a-^c");
        ranges = set.getCharRanges();
        assertTrue(ranges.contains(CharRange.isNotIn('a', '^'))); // "^a-^"
        assertTrue(ranges.contains(CharRange.is('c'))); // "c"
        assertTrue(set.contains('b'));
        assertFalse(set.contains('^'));
        assertFalse(set.contains('_')); // between ^ and a

        // "a- ^-- " parses as range "a- " plus negated range "^-- "; together covers everything
        set = CharSet.getInstance("a- ^-- ");
        ranges = set.getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('a', ' '))); // "a- "
        assertTrue(ranges.contains(CharRange.isNotIn('-', ' '))); // "^-- "
        assertTrue(set.contains('#'));
        assertTrue(set.contains('^'));
        assertTrue(set.contains('a'));
        assertTrue(set.contains('*'));
        assertTrue(set.contains('A'));

        // "^-b" parses as the plain range "^-b" (the leading ^ is the range start, not a negation)
        set = CharSet.getInstance("^-b");
        ranges = set.getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('^', 'b'))); // "^-b"
        assertTrue(set.contains('b'));
        assertTrue(set.contains('_')); // between ^ and a
        assertFalse(set.contains('A'));
        assertTrue(set.contains('^'));

        // "b-^" parses as range "b-^", reversed internally to "^-b"
        set = CharSet.getInstance("b-^");
        ranges = set.getCharRanges();
        assertTrue(ranges.contains(CharRange.isIn('^', 'b'))); // "b-^"
        assertTrue(set.contains('b'));
        assertTrue(set.contains('^'));
        assertTrue(set.contains('a')); // between ^ and b
        assertFalse(set.contains('c'));
    }

    @Test
    void testConstructor_String_oddDash() {
        // A lone or repeated dash collapses to the single character '-'
        Set<CharRange> ranges = rangesOf("-");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));

        ranges = rangesOf("--");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));

        ranges = rangesOf("---");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));

        ranges = rangesOf("----");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));

        // "-a" -> two separate single characters
        ranges = rangesOf("-a");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('-')));
        assertTrue(ranges.contains(CharRange.is('a')));

        // "a-" -> two separate single characters
        ranges = rangesOf("a-");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a')));
        assertTrue(ranges.contains(CharRange.is('-')));

        // "a--" -> range from 'a' to '-'
        ranges = rangesOf("a--");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('a', '-')));

        // "--a" -> range from '-' to 'a'
        ranges = rangesOf("--a");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isIn('-', 'a')));
    }

    @Test
    void testConstructor_String_oddNegate() {
        // "^" alone is the literal single character '^'
        Set<CharRange> ranges = rangesOf("^");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.is('^'))); // "^"

        // "^^" -> negated '^'
        ranges = rangesOf("^^");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^"

        // "^^^" -> negated '^' followed by literal '^'
        ranges = rangesOf("^^^");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^"
        assertTrue(ranges.contains(CharRange.is('^'))); // "^"

        // "^^^^" -> two identical negated '^' that collapse into one
        ranges = rangesOf("^^^^");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^" x2

        // "a^" -> literal 'a' and literal '^'
        ranges = rangesOf("a^");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.is('a'))); // "a"
        assertTrue(ranges.contains(CharRange.is('^'))); // "^"

        // "^a-" -> negated 'a' followed by literal '-'
        ranges = rangesOf("^a-");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('a'))); // "^a"
        assertTrue(ranges.contains(CharRange.is('-'))); // "-"

        // "^^-c" -> negated range from '^' to 'c'
        ranges = rangesOf("^^-c");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('^', 'c'))); // "^^-c"

        // "^c-^" -> negated range from 'c' to '^'
        ranges = rangesOf("^c-^");
        assertEquals(1, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('c', '^'))); // "^c-^"

        // "^c-^d" -> negated range "^c-^" plus literal 'd'
        ranges = rangesOf("^c-^d");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNotIn('c', '^'))); // "^c-^"
        assertTrue(ranges.contains(CharRange.is('d'))); // "d"

        // "^^-" -> negated '^' followed by literal '-'
        ranges = rangesOf("^^-");
        assertEquals(2, ranges.size());
        assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^"
        assertTrue(ranges.contains(CharRange.is('-'))); // "-"
    }

    @Test
    void testConstructor_String_simple() {
        // null and "" both yield the empty set
        CharSet set = CharSet.getInstance((String) null);
        assertEquals("[]", set.toString());
        assertEquals(0, set.getCharRanges().size());

        set = CharSet.getInstance("");
        assertEquals("[]", set.toString());
        assertEquals(0, set.getCharRanges().size());

        // single literal character
        set = CharSet.getInstance("a");
        assertEquals("[a]", set.toString());
        assertEquals(1, set.getCharRanges().size());
        assertEquals("a", set.getCharRanges().iterator().next().toString());

        // negated single character
        set = CharSet.getInstance("^a");
        assertEquals("[^a]", set.toString());
        assertEquals(1, set.getCharRanges().size());
        assertEquals("^a", set.getCharRanges().iterator().next().toString());

        // plain range
        set = CharSet.getInstance("a-e");
        assertEquals("[a-e]", set.toString());
        assertEquals(1, set.getCharRanges().size());
        assertEquals("a-e", set.getCharRanges().iterator().next().toString());

        // negated range
        set = CharSet.getInstance("^a-e");
        assertEquals("[^a-e]", set.toString());
        assertEquals(1, set.getCharRanges().size());
        assertEquals("^a-e", set.getCharRanges().iterator().next().toString());
    }

    @Test
    void testContains_Char() {
        final CharSet btod = CharSet.getInstance("b-d");
        final CharSet dtob = CharSet.getInstance("d-b");
        final CharSet bcd = CharSet.getInstance("bcd");
        final CharSet bd = CharSet.getInstance("bd");
        final CharSet notbtod = CharSet.getInstance("^b-d");

        // range "b-d" contains b, c, d but not the surrounding characters
        assertFalse(btod.contains('a'));
        assertTrue(btod.contains('b'));
        assertTrue(btod.contains('c'));
        assertTrue(btod.contains('d'));
        assertFalse(btod.contains('e'));

        // explicit set "bcd" behaves the same as the range
        assertFalse(bcd.contains('a'));
        assertTrue(bcd.contains('b'));
        assertTrue(bcd.contains('c'));
        assertTrue(bcd.contains('d'));
        assertFalse(bcd.contains('e'));

        // "bd" only contains b and d, not c in between
        assertFalse(bd.contains('a'));
        assertTrue(bd.contains('b'));
        assertFalse(bd.contains('c'));
        assertTrue(bd.contains('d'));
        assertFalse(bd.contains('e'));

        // "^b-d" is the complement of "b-d"
        assertTrue(notbtod.contains('a'));
        assertFalse(notbtod.contains('b'));
        assertFalse(notbtod.contains('c'));
        assertFalse(notbtod.contains('d'));
        assertTrue(notbtod.contains('e'));

        // "d-b" is reversed to "b-d" and therefore behaves identically
        assertFalse(dtob.contains('a'));
        assertTrue(dtob.contains('b'));
        assertTrue(dtob.contains('c'));
        assertTrue(dtob.contains('d'));
        assertFalse(dtob.contains('e'));

        assertEquals("[b-d]", dtob.toString());
        assertEquals(1, dtob.getCharRanges().size());
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

        // "abc" equals itself and an equally-defined set, but not a differently-defined one
        assertEquals(abc, abc);
        assertEquals(abc, abc2);
        assertNotEquals(abc, atoc);
        assertNotEquals(abc, notatoc);

        // "a-c" is not equal to the enumerated "abc" even though they match the same characters
        assertNotEquals(atoc, abc);
        assertEquals(atoc, atoc);
        assertEquals(atoc, atoc2);
        assertNotEquals(atoc, notatoc);

        // "^a-c" is distinct from both of the above
        assertNotEquals(notatoc, abc);
        assertNotEquals(notatoc, atoc);
        assertEquals(notatoc, notatoc);
        assertEquals(notatoc, notatoc2);
    }

    @Test
    void testGetInstance() {
        // Known patterns are interned and return the shared constant instance
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

        // equal sets must produce equal hash codes
        assertEquals(abc.hashCode(), abc.hashCode());
        assertEquals(abc.hashCode(), abc2.hashCode());
        assertEquals(atoc.hashCode(), atoc.hashCode());
        assertEquals(atoc.hashCode(), atoc2.hashCode());
        assertEquals(notatoc.hashCode(), notatoc.hashCode());
        assertEquals(notatoc.hashCode(), notatoc2.hashCode());
    }

    @Test
    void testJavadocExamples() {
        // Mirrors the negation examples documented on CharSet.getInstance
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
        // A round-tripped clone must equal the original
        CharSet set = CharSet.getInstance("a");
        assertEquals(set, SerializationUtils.clone(set));
        set = CharSet.getInstance("a-e");
        assertEquals(set, SerializationUtils.clone(set));
        set = CharSet.getInstance("be-f^a-z");
        assertEquals(set, SerializationUtils.clone(set));
    }

    @Test
    void testStatics() {
        // EMPTY has no ranges
        Set<CharRange> ranges = CharSet.EMPTY.getCharRanges();
        assertEquals(0, ranges.size());

        // ASCII_ALPHA combines the lower and upper case ranges
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
