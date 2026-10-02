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

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharRange}.
 *
 * <p>Throughout this suite a {@link CharRange} is described by its factory method:</p>
 * <ul>
 *   <li>{@code is(c)} / {@code isIn(start, end)} build a <em>normal</em> range that contains the listed characters.</li>
 *   <li>{@code isNot(c)} / {@code isNotIn(start, end)} build a <em>negated</em> range that contains every character
 *       <em>except</em> the listed ones.</li>
 *   <li>{@code isIn} / {@code isNotIn} automatically reorder reversed endpoints, so {@code a-e} equals {@code e-a}.</li>
 *   <li>{@link CharRange#toString()} renders a range as {@code start}, {@code start-end}, or with a leading {@code ^}
 *       when negated (for example {@code "a"}, {@code "a-e"}, {@code "^a-e"}).</li>
 * </ul>
 */
class CharRangeTest extends AbstractLangTest {

    @Test
    void testClass() {
        // class changed to non-public in 3.0
        assertFalse(Modifier.isPublic(CharRange.class.getModifiers()));
        assertTrue(Modifier.isFinal(CharRange.class.getModifiers()));
    }

    @Test
    void testConstructorAccessors_is() {
        // is('a') -> the single character 'a'
        final CharRange rangea = CharRange.is('a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isIn_Normal() {
        // isIn('a', 'e') -> the contiguous range 'a' through 'e'
        final CharRange rangea = CharRange.isIn('a', 'e');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isIn_Reversed() {
        // reversed endpoints are normalized so 'e','a' becomes the range 'a'-'e'
        final CharRange rangea = CharRange.isIn('e', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isIn_Same() {
        // a range whose endpoints coincide collapses to a single character
        final CharRange rangea = CharRange.isIn('a', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNot() {
        // isNot('a') -> every character except 'a'
        final CharRange rangea = CharRange.isNot('a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNotIn_Normal() {
        // isNotIn('a', 'e') -> every character except those in 'a'-'e'
        final CharRange rangea = CharRange.isNotIn('a', 'e');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNotIn_Reversed() {
        // reversed endpoints are normalized even for negated ranges
        final CharRange rangea = CharRange.isNotIn('e', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNotIn_Same() {
        // a negated range with coinciding endpoints negates a single character
        final CharRange rangea = CharRange.isNotIn('a', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a", rangea.toString());
    }

    @Test
    void testContains_Char() {
        // Single character 'c': contains only 'c'.
        CharRange range = CharRange.is('c');
        assertFalse(range.contains('b'));
        assertTrue(range.contains('c'));
        assertFalse(range.contains('d'));
        assertFalse(range.contains('e'));

        // Range 'c'-'d': contains 'c' and 'd', nothing on either side.
        range = CharRange.isIn('c', 'd');
        assertFalse(range.contains('b'));
        assertTrue(range.contains('c'));
        assertTrue(range.contains('d'));
        assertFalse(range.contains('e'));

        // Reversed endpoints behave the same as 'c'-'d'.
        range = CharRange.isIn('d', 'c');
        assertFalse(range.contains('b'));
        assertTrue(range.contains('c'));
        assertTrue(range.contains('d'));
        assertFalse(range.contains('e'));

        // Negated 'c'-'d': contains everything except 'c' and 'd',
        // including the extreme code points.
        range = CharRange.isNotIn('c', 'd');
        assertTrue(range.contains('b'));
        assertFalse(range.contains('c'));
        assertFalse(range.contains('d'));
        assertTrue(range.contains('e'));
        assertTrue(range.contains((char) 0));
        assertTrue(range.contains(Character.MAX_VALUE));
    }

    @Test
    void testContains_Charrange() {
        // Single-character ranges.
        final CharRange a = CharRange.is('a');
        final CharRange b = CharRange.is('b');
        final CharRange c = CharRange.is('c');
        final CharRange c2 = CharRange.is('c');
        final CharRange d = CharRange.is('d');
        final CharRange e = CharRange.is('e');
        // Multi-character ranges, named after their endpoints.
        final CharRange cd = CharRange.isIn('c', 'd');
        final CharRange bd = CharRange.isIn('b', 'd');
        final CharRange bc = CharRange.isIn('b', 'c');
        final CharRange ab = CharRange.isIn('a', 'b');
        final CharRange de = CharRange.isIn('d', 'e');
        final CharRange ef = CharRange.isIn('e', 'f');
        final CharRange ae = CharRange.isIn('a', 'e');

        // normal/normal: a normal range contains another only when it fully covers it.
        assertFalse(c.contains(b));
        assertTrue(c.contains(c));
        assertTrue(c.contains(c2));
        assertFalse(c.contains(d));

        assertFalse(c.contains(cd));
        assertFalse(c.contains(bd));
        assertFalse(c.contains(bc));
        assertFalse(c.contains(ab));
        assertFalse(c.contains(de));

        assertTrue(cd.contains(c));
        assertTrue(bd.contains(c));
        assertTrue(bc.contains(c));
        assertFalse(ab.contains(c));
        assertFalse(de.contains(c));

        // 'a'-'e' covers every sub-range that lies within its bounds.
        assertTrue(ae.contains(b));
        assertTrue(ae.contains(ab));
        assertTrue(ae.contains(bc));
        assertTrue(ae.contains(cd));
        assertTrue(ae.contains(de));

        // Negated single-character ranges.
        final CharRange notb = CharRange.isNot('b');
        final CharRange notc = CharRange.isNot('c');
        final CharRange notd = CharRange.isNot('d');
        // Negated multi-character ranges, named after their endpoints.
        final CharRange notab = CharRange.isNotIn('a', 'b');
        final CharRange notbc = CharRange.isNotIn('b', 'c');
        final CharRange notbd = CharRange.isNotIn('b', 'd');
        final CharRange notcd = CharRange.isNotIn('c', 'd');
        final CharRange notde = CharRange.isNotIn('d', 'e');
        final CharRange notae = CharRange.isNotIn('a', 'e');
        // 'all' is the full code-point range; 'allbutfirst' drops the first code point.
        final CharRange all = CharRange.isIn((char) 0, Character.MAX_VALUE);
        final CharRange allbutfirst = CharRange.isIn((char) 1, Character.MAX_VALUE);

        // normal/negated: only the all-encompassing range can contain a negated range.
        assertFalse(c.contains(notc));
        assertFalse(c.contains(notbd));
        assertTrue(all.contains(notc));
        assertTrue(all.contains(notbd));
        assertFalse(allbutfirst.contains(notc));
        assertFalse(allbutfirst.contains(notbd));

        // negated/normal: a negated range contains a normal range only when that
        // range avoids the excluded characters entirely.
        assertTrue(notc.contains(a));
        assertTrue(notc.contains(b));
        assertFalse(notc.contains(c));
        assertTrue(notc.contains(d));
        assertTrue(notc.contains(e));

        assertTrue(notc.contains(ab));
        assertFalse(notc.contains(bc));
        assertFalse(notc.contains(bd));
        assertFalse(notc.contains(cd));
        assertTrue(notc.contains(de));
        assertFalse(notc.contains(ae));
        assertFalse(notc.contains(all));
        assertFalse(notc.contains(allbutfirst));

        assertTrue(notbd.contains(a));
        assertFalse(notbd.contains(b));
        assertFalse(notbd.contains(c));
        assertFalse(notbd.contains(d));
        assertTrue(notbd.contains(e));

        assertTrue(notcd.contains(ab));
        assertFalse(notcd.contains(bc));
        assertFalse(notcd.contains(bd));
        assertFalse(notcd.contains(cd));
        assertFalse(notcd.contains(de));
        assertFalse(notcd.contains(ae));
        assertTrue(notcd.contains(ef));
        assertFalse(notcd.contains(all));
        assertFalse(notcd.contains(allbutfirst));

        // negated/negated: a negated range contains another negated range only when
        // its excluded interval is a subset of the other's excluded interval.
        assertFalse(notc.contains(notb));
        assertTrue(notc.contains(notc));
        assertFalse(notc.contains(notd));

        assertFalse(notc.contains(notab));
        assertTrue(notc.contains(notbc));
        assertTrue(notc.contains(notbd));
        assertTrue(notc.contains(notcd));
        assertFalse(notc.contains(notde));

        assertFalse(notbd.contains(notb));
        assertFalse(notbd.contains(notc));
        assertFalse(notbd.contains(notd));

        assertFalse(notbd.contains(notab));
        assertFalse(notbd.contains(notbc));
        assertTrue(notbd.contains(notbd));
        assertFalse(notbd.contains(notcd));
        assertFalse(notbd.contains(notde));
        assertTrue(notbd.contains(notae));
    }

    @Test
    void testContainsNullArg() {
        // contains(CharRange) rejects a null argument with a named NPE.
        final CharRange range = CharRange.is('a');
        final NullPointerException e = assertNullPointerException(() -> range.contains(null));
        assertEquals("range", e.getMessage());
    }

    @Test
    void testEquals_Object() {
        final CharRange rangea = CharRange.is('a');
        final CharRange rangeae = CharRange.isIn('a', 'e');
        final CharRange rangebf = CharRange.isIn('b', 'f');

        // Never equal to null.
        assertNotEquals(null, rangea);

        // Equal to itself and to a freshly built range with the same definition.
        assertEquals(rangea, rangea);
        assertEquals(rangea, CharRange.is('a'));
        assertEquals(rangeae, rangeae);
        assertEquals(rangeae, CharRange.isIn('a', 'e'));
        assertEquals(rangebf, rangebf);
        assertEquals(rangebf, CharRange.isIn('b', 'f'));

        // Ranges with different definitions are not equal, in either direction.
        assertNotEquals(rangea, rangeae);
        assertNotEquals(rangea, rangebf);
        assertNotEquals(rangeae, rangea);
        assertNotEquals(rangeae, rangebf);
        assertNotEquals(rangebf, rangea);
        assertNotEquals(rangebf, rangeae);
    }

    @Test
    void testHashCode() {
        final CharRange rangea = CharRange.is('a');
        final CharRange rangeae = CharRange.isIn('a', 'e');
        final CharRange rangebf = CharRange.isIn('b', 'f');

        // Equal ranges (including freshly built equivalents) share a hash code.
        assertEquals(rangea.hashCode(), rangea.hashCode());
        assertEquals(rangea.hashCode(), CharRange.is('a').hashCode());
        assertEquals(rangeae.hashCode(), rangeae.hashCode());
        assertEquals(rangeae.hashCode(), CharRange.isIn('a', 'e').hashCode());
        assertEquals(rangebf.hashCode(), rangebf.hashCode());
        assertEquals(rangebf.hashCode(), CharRange.isIn('b', 'f').hashCode());

        // Different ranges produce different hash codes, in either direction.
        assertNotEquals(rangea.hashCode(), rangeae.hashCode());
        assertNotEquals(rangea.hashCode(), rangebf.hashCode());
        assertNotEquals(rangeae.hashCode(), rangea.hashCode());
        assertNotEquals(rangeae.hashCode(), rangebf.hashCode());
        assertNotEquals(rangebf.hashCode(), rangea.hashCode());
        assertNotEquals(rangebf.hashCode(), rangeae.hashCode());
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-1802
     */
    @Test
    void testHashCodeLang1802() {
        // Test various combinations of different ranges
        final CharRange range1 = CharRange.is('a');
        final CharRange range2 = CharRange.is('b');
        final CharRange range3 = CharRange.isIn('a', 'z');
        final CharRange range4 = CharRange.isIn('b', 'z');
        final CharRange range5 = CharRange.isNot('a');
        final CharRange range6 = CharRange.isNotIn('a', 'z');
        final CharRange range7 = CharRange.isNotIn('b', 'z');
        final CharRange range8 = CharRange.isIn((char) 1, (char) 2);
        final CharRange range9 = CharRange.isNotIn((char) 1, (char) 2);
        // Previously problematic cases from LANG-1802 should now have different hash codes
        final CharRange a1 = CharRange.isNotIn((char) 1, (char) 2);
        final CharRange a2 = CharRange.isIn((char) 2, (char) 2);
        assertNotEquals(a1, a2, "Different ranges should not be equal");
        assertNotEquals(a1.hashCode(), a2.hashCode(), "Different ranges should have different hash codes");
        final CharRange b1 = CharRange.isIn((char) 5, (char) 5);
        final CharRange b2 = CharRange.isNotIn((char) 4, (char) 5);
        assertNotEquals(b1, b2, "Different ranges should not be equal");
        assertNotEquals(b1.hashCode(), b2.hashCode(), "Different ranges should have different hash codes");
        // Test that negated and non-negated ranges with same bounds have different hash codes
        final CharRange normal = CharRange.isIn('x', 'y');
        final CharRange negated = CharRange.isNotIn('x', 'y');
        assertNotEquals(normal, negated, "Negated and normal ranges should not be equal");
        assertNotEquals(normal.hashCode(), negated.hashCode(), "Negated and normal ranges should have different hash codes");
        // Test that ranges with different start/end produce different hash codes
        assertNotEquals(range1.hashCode(), range2.hashCode(), "is('a') vs is('b')");
        assertNotEquals(range1.hashCode(), range3.hashCode(), "is('a') vs isIn('a', 'z')");
        assertNotEquals(range3.hashCode(), range4.hashCode(), "isIn('a', 'z') vs isIn('b', 'z')");
        assertNotEquals(range1.hashCode(), range5.hashCode(), "is('a') vs isNot('a')");
        assertNotEquals(range3.hashCode(), range6.hashCode(), "isIn('a', 'z') vs isNotIn('a', 'z')");
        assertNotEquals(range6.hashCode(), range7.hashCode(), "isNotIn('a', 'z') vs isNotIn('b', 'z')");
        assertNotEquals(range8.hashCode(), range9.hashCode(), "isIn(1, 2) vs isNotIn(1, 2)");
        // Test that equal ranges have equal hash codes
        final CharRange sameAsRange1 = CharRange.is('a');
        assertEquals(range1, sameAsRange1, "Equal ranges should be equal");
        assertEquals(range1.hashCode(), sameAsRange1.hashCode(), "Equal ranges should have equal hash codes");
    }

    @Test
    void testIterator() {
        final CharRange a = CharRange.is('a');
        final CharRange ad = CharRange.isIn('a', 'd');
        final CharRange nota = CharRange.isNot('a');
        // Negating the full code-point range yields an empty set.
        final CharRange emptySet = CharRange.isNotIn((char) 0, Character.MAX_VALUE);
        // Negating everything but the first/last code point leaves a single character.
        final CharRange notFirst = CharRange.isNotIn((char) 1, Character.MAX_VALUE);
        final CharRange notLast = CharRange.isNotIn((char) 0, (char) (Character.MAX_VALUE - 1));

        // is('a') iterates over exactly one character: 'a'.
        final Iterator<Character> aIt = a.iterator();
        assertNotNull(aIt);
        assertTrue(aIt.hasNext());
        assertEquals(Character.valueOf('a'), aIt.next());
        assertFalse(aIt.hasNext());

        // isIn('a', 'd') iterates 'a', 'b', 'c', 'd' in order.
        final Iterator<Character> adIt = ad.iterator();
        assertNotNull(adIt);
        assertTrue(adIt.hasNext());
        assertEquals(Character.valueOf('a'), adIt.next());
        assertEquals(Character.valueOf('b'), adIt.next());
        assertEquals(Character.valueOf('c'), adIt.next());
        assertEquals(Character.valueOf('d'), adIt.next());
        assertFalse(adIt.hasNext());

        // isNot('a') iterates every character except 'a'.
        final Iterator<Character> notaIt = nota.iterator();
        assertNotNull(notaIt);
        assertTrue(notaIt.hasNext());
        while (notaIt.hasNext()) {
            final Character c = notaIt.next();
            assertNotEquals('a', c.charValue());
        }

        // The empty set has no elements; next() fails immediately.
        final Iterator<Character> emptySetIt = emptySet.iterator();
        assertNotNull(emptySetIt);
        assertFalse(emptySetIt.hasNext());
        assertThrows(NoSuchElementException.class, emptySetIt::next);

        // Only the first code point remains; next() yields it then fails.
        final Iterator<Character> notFirstIt = notFirst.iterator();
        assertNotNull(notFirstIt);
        assertTrue(notFirstIt.hasNext());
        assertEquals(Character.valueOf((char) 0), notFirstIt.next());
        assertFalse(notFirstIt.hasNext());
        assertThrows(NoSuchElementException.class, notFirstIt::next);

        // Only the last code point remains; next() yields it then fails.
        final Iterator<Character> notLastIt = notLast.iterator();
        assertNotNull(notLastIt);
        assertTrue(notLastIt.hasNext());
        assertEquals(Character.valueOf(Character.MAX_VALUE), notLastIt.next());
        assertFalse(notLastIt.hasNext());
        assertThrows(NoSuchElementException.class, notLastIt::next);
    }

    @Test
    void testIteratorRemove() {
        // The iterator is read-only; remove() is unsupported.
        final CharRange a = CharRange.is('a');
        final Iterator<Character> aIt = a.iterator();
        assertThrows(UnsupportedOperationException.class, aIt::remove);
    }

    @Test
    void testSerialization() {
        // A round-tripped clone is equal to the original for each range flavour.
        CharRange range = CharRange.is('a');
        assertEquals(range, SerializationUtils.clone(range));
        range = CharRange.isIn('a', 'e');
        assertEquals(range, SerializationUtils.clone(range));
        range = CharRange.isNotIn('a', 'e');
        assertEquals(range, SerializationUtils.clone(range));
    }
}
