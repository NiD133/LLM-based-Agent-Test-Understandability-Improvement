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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet}.
 */
class CharSetTest extends AbstractLangTest {

    /** Returns the CharRange set contained in the CharSet built from the given descriptor. */
    private Set<CharRange> rangesOf(final String descriptor) {
        return CharSet.getInstance(descriptor).getCharRanges();
    }

    @Test
    void testClass() {
        assertTrue(Modifier.isPublic(CharSet.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSet.class.getModifiers()));
    }

    @Nested
    class GetInstance {

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
    }

    @Nested
    class Constructor {

        @Test
        void testConstructor_String_simple() {
            CharSet set;
            Set<CharRange> ranges;

            // null and empty string both produce an empty set
            set = CharSet.getInstance((String) null);
            ranges = set.getCharRanges();
            assertEquals("[]", set.toString());
            assertEquals(0, ranges.size());

            set = CharSet.getInstance("");
            ranges = set.getCharRanges();
            assertEquals("[]", set.toString());
            assertEquals(0, ranges.size());

            // single character
            set = CharSet.getInstance("a");
            ranges = set.getCharRanges();
            assertEquals("[a]", set.toString());
            assertEquals(1, ranges.size());
            assertEquals("a", ranges.iterator().next().toString());

            // negated single character
            set = CharSet.getInstance("^a");
            ranges = set.getCharRanges();
            assertEquals("[^a]", set.toString());
            assertEquals(1, ranges.size());
            assertEquals("^a", ranges.iterator().next().toString());

            // range
            set = CharSet.getInstance("a-e");
            ranges = set.getCharRanges();
            assertEquals("[a-e]", set.toString());
            assertEquals(1, ranges.size());
            assertEquals("a-e", ranges.iterator().next().toString());

            // negated range
            set = CharSet.getInstance("^a-e");
            ranges = set.getCharRanges();
            assertEquals("[^a-e]", set.toString());
            assertEquals(1, ranges.size());
            assertEquals("^a-e", ranges.iterator().next().toString());
        }

        @Test
        void testConstructor_String_combo() {
            CharSet set;
            Set<CharRange> ranges;

            // three individual characters
            set = CharSet.getInstance("abc");
            ranges = set.getCharRanges();
            assertEquals(3, ranges.size());
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.is('b')));
            assertTrue(ranges.contains(CharRange.is('c')));

            // two adjacent ranges
            set = CharSet.getInstance("a-ce-f");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.isIn('a', 'c')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));

            // range preceded by a single character
            set = CharSet.getInstance("ae-f");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));

            // range followed by a single character
            set = CharSet.getInstance("e-fa");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));

            // single char + two ranges + single char
            set = CharSet.getInstance("ae-fm-pz");
            ranges = set.getCharRanges();
            assertEquals(4, ranges.size());
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.isIn('e', 'f')));
            assertTrue(ranges.contains(CharRange.isIn('m', 'p')));
            assertTrue(ranges.contains(CharRange.is('z')));
        }

        @Test
        void testConstructor_String_comboNegated() {
            CharSet set;
            Set<CharRange> ranges;

            // leading ^ negates only the first character
            set = CharSet.getInstance("^abc");
            ranges = set.getCharRanges();
            assertEquals(3, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('a')));
            assertTrue(ranges.contains(CharRange.is('b')));
            assertTrue(ranges.contains(CharRange.is('c')));

            // ^ mid-string negates the character immediately following it
            set = CharSet.getInstance("b^ac");
            ranges = set.getCharRanges();
            assertEquals(3, ranges.size());
            assertTrue(ranges.contains(CharRange.is('b')));
            assertTrue(ranges.contains(CharRange.isNot('a')));
            assertTrue(ranges.contains(CharRange.is('c')));

            // two plain chars then a negated char then a plain char
            set = CharSet.getInstance("db^ac");
            ranges = set.getCharRanges();
            assertEquals(4, ranges.size());
            assertTrue(ranges.contains(CharRange.is('d')));
            assertTrue(ranges.contains(CharRange.is('b')));
            assertTrue(ranges.contains(CharRange.isNot('a')));
            assertTrue(ranges.contains(CharRange.is('c')));

            // two consecutive negated characters
            set = CharSet.getInstance("^b^a");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('b')));
            assertTrue(ranges.contains(CharRange.isNot('a')));

            // negated range and negated char mixed with a plain char
            set = CharSet.getInstance("b^a-c^z");
            ranges = set.getCharRanges();
            assertEquals(3, ranges.size());
            assertTrue(ranges.contains(CharRange.isNotIn('a', 'c')));
            assertTrue(ranges.contains(CharRange.isNot('z')));
            assertTrue(ranges.contains(CharRange.is('b')));
        }

        @Test
        void testConstructor_String_oddDash() {
            CharSet set;
            Set<CharRange> ranges;

            // a lone dash is a literal dash character, regardless of how many dashes appear
            set = CharSet.getInstance("-");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.is('-')));

            set = CharSet.getInstance("--");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.is('-')));

            set = CharSet.getInstance("---");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.is('-')));

            set = CharSet.getInstance("----");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.is('-')));

            // dash before or after a character is treated as a literal dash, not a range operator
            set = CharSet.getInstance("-a");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.is('-')));
            assertTrue(ranges.contains(CharRange.is('a')));

            set = CharSet.getInstance("a-");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.is('a')));
            assertTrue(ranges.contains(CharRange.is('-')));

            // "a--" → range from 'a' to '-'
            set = CharSet.getInstance("a--");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.isIn('a', '-')));

            // "--a" → range from '-' to 'a'
            set = CharSet.getInstance("--a");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.isIn('-', 'a')));
        }

        @Test
        void testConstructor_String_oddNegate() {
            CharSet set;
            Set<CharRange> ranges;

            // a lone ^ is a literal caret character
            set = CharSet.getInstance("^");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.is('^'))); // "^"

            // "^^" → negated caret (i.e. everything except '^')
            set = CharSet.getInstance("^^");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^"

            // "^^^" → negated caret ("^^") + literal caret ("^")
            set = CharSet.getInstance("^^^");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^"
            assertTrue(ranges.contains(CharRange.is('^'))); // "^"

            // "^^^^" → two negated-caret tokens collapse to one range
            set = CharSet.getInstance("^^^^");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^" x2

            // literal caret at the end of the string (plain char follows 'a')
            set = CharSet.getInstance("a^");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.is('a'))); // "a"
            assertTrue(ranges.contains(CharRange.is('^'))); // "^"

            // negated char followed by trailing dash → negated 'a' + literal '-'
            set = CharSet.getInstance("^a-");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('a'))); // "^a"
            assertTrue(ranges.contains(CharRange.is('-'))); // "-"

            // "^^-c" → negated range from '^' to 'c'
            set = CharSet.getInstance("^^-c");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.isNotIn('^', 'c'))); // "^^-c"

            // "^c-^" → negated range from 'c' to '^'
            set = CharSet.getInstance("^c-^");
            ranges = set.getCharRanges();
            assertEquals(1, ranges.size());
            assertTrue(ranges.contains(CharRange.isNotIn('c', '^'))); // "^c-^"

            // "^c-^d" → negated range "^c-^" plus literal 'd'
            set = CharSet.getInstance("^c-^d");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.isNotIn('c', '^'))); // "^c-^"
            assertTrue(ranges.contains(CharRange.is('d'))); // "d"

            // "^^-" → negated caret ("^^") + literal dash ("-")
            set = CharSet.getInstance("^^-");
            ranges = set.getCharRanges();
            assertEquals(2, ranges.size());
            assertTrue(ranges.contains(CharRange.isNot('^'))); // "^^"
            assertTrue(ranges.contains(CharRange.is('-'))); // "-"
        }

        @Test
        void testConstructor_String_oddCombinations() {
            CharSet set;
            Set<CharRange> ranges;

            // "a-^" is a range (a to ^), "c" is a plain char; ^ comes before a in ASCII so range includes ^.._`a
            set = CharSet.getInstance("a-^c");
            ranges = set.getCharRanges();
            assertTrue(ranges.contains(CharRange.isIn('a', '^'))); // "a-^"
            assertTrue(ranges.contains(CharRange.is('c'))); // "c"
            assertFalse(set.contains('b'));
            assertTrue(set.contains('^'));
            assertTrue(set.contains('_')); // between ^ and a
            assertTrue(set.contains('c'));

            // "^a-^" is a negated range (not a..^); "c" is a plain char
            set = CharSet.getInstance("^a-^c");
            ranges = set.getCharRanges();
            assertTrue(ranges.contains(CharRange.isNotIn('a', '^'))); // "^a-^"
            assertTrue(ranges.contains(CharRange.is('c'))); // "c"
            assertTrue(set.contains('b'));
            assertFalse(set.contains('^'));
            assertFalse(set.contains('_')); // between ^ and a

            // "a- " is a range and "^-- " is a negated range; together they cover everything
            set = CharSet.getInstance("a- ^-- "); //contains everything
            ranges = set.getCharRanges();
            assertTrue(ranges.contains(CharRange.isIn('a', ' '))); // "a- "
            assertTrue(ranges.contains(CharRange.isNotIn('-', ' '))); // "^-- "
            assertTrue(set.contains('#'));
            assertTrue(set.contains('^'));
            assertTrue(set.contains('a'));
            assertTrue(set.contains('*'));
            assertTrue(set.contains('A'));

            // "^-b" → range from '^' to 'b' (^ treated as a literal start of range here)
            set = CharSet.getInstance("^-b");
            ranges = set.getCharRanges();
            assertTrue(ranges.contains(CharRange.isIn('^', 'b'))); // "^-b"
            assertTrue(set.contains('b'));
            assertTrue(set.contains('_')); // between ^ and a
            assertFalse(set.contains('A'));
            assertTrue(set.contains('^'));

            // "b-^" → range from '^' to 'b' (reversed endpoints are normalized)
            set = CharSet.getInstance("b-^");
            ranges = set.getCharRanges();
            assertTrue(ranges.contains(CharRange.isIn('^', 'b'))); // "b-^"
            assertTrue(set.contains('b'));
            assertTrue(set.contains('^'));
            assertTrue(set.contains('a')); // between ^ and b
            assertFalse(set.contains('c'));
        }
    }

    @Nested
    class Contains {

        @Test
        void testContains_Char() {
            final CharSet btod = CharSet.getInstance("b-d");
            final CharSet dtob = CharSet.getInstance("d-b");   // reversed endpoints, same range as b-d
            final CharSet bcd = CharSet.getInstance("bcd");
            final CharSet bd = CharSet.getInstance("bd");
            final CharSet notbtod = CharSet.getInstance("^b-d");

            // range b-d
            assertFalse(btod.contains('a'));
            assertTrue(btod.contains('b'));
            assertTrue(btod.contains('c'));
            assertTrue(btod.contains('d'));
            assertFalse(btod.contains('e'));

            // enumerated chars b, c, d
            assertFalse(bcd.contains('a'));
            assertTrue(bcd.contains('b'));
            assertTrue(bcd.contains('c'));
            assertTrue(bcd.contains('d'));
            assertFalse(bcd.contains('e'));

            // enumerated chars b and d only (no c)
            assertFalse(bd.contains('a'));
            assertTrue(bd.contains('b'));
            assertFalse(bd.contains('c'));
            assertTrue(bd.contains('d'));
            assertFalse(bd.contains('e'));

            // negated range b-d
            assertTrue(notbtod.contains('a'));
            assertFalse(notbtod.contains('b'));
            assertFalse(notbtod.contains('c'));
            assertFalse(notbtod.contains('d'));
            assertTrue(notbtod.contains('e'));

            // reversed-endpoint range is normalized to b-d
            assertFalse(dtob.contains('a'));
            assertTrue(dtob.contains('b'));
            assertTrue(dtob.contains('c'));
            assertTrue(dtob.contains('d'));
            assertFalse(dtob.contains('e'));

            final Set<CharRange> ranges = dtob.getCharRanges();
            assertEquals("[b-d]", dtob.toString());
            assertEquals(1, ranges.size());
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
    }

    @Nested
    class Statics {

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

    @Nested
    class EqualsAndHashCode {

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
}
