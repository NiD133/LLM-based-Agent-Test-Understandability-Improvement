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
        final CharRange rangea = CharRange.is('a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isIn_Normal() {
        final CharRange rangea = CharRange.isIn('a', 'e');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isIn_Reversed() {
        final CharRange rangea = CharRange.isIn('e', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isIn_Same() {
        final CharRange rangea = CharRange.isIn('a', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNot() {
        final CharRange rangea = CharRange.isNot('a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNotIn_Normal() {
        final CharRange rangea = CharRange.isNotIn('a', 'e');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNotIn_Reversed() {
        final CharRange rangea = CharRange.isNotIn('e', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a-e", rangea.toString());
    }

    @Test
    void testConstructorAccessors_isNotIn_Same() {
        final CharRange rangea = CharRange.isNotIn('a', 'a');
        assertEquals('a', rangea.getStart());
        assertEquals('a', rangea.getEnd());
        assertTrue(rangea.isNegated());
        assertEquals("^a", rangea.toString());
    }

    @Test
    void testContains_Char() {
        // Single character range 'c': only 'c' is contained
        final CharRange singleC = CharRange.is('c');
        assertFalse(singleC.contains('b'));
        assertTrue(singleC.contains('c'));
        assertFalse(singleC.contains('d'));
        assertFalse(singleC.contains('e'));

        // Range 'c'-'d' (normal order): 'c' and 'd' are contained, neighbours are not
        final CharRange rangeCD = CharRange.isIn('c', 'd');
        assertFalse(rangeCD.contains('b'));
        assertTrue(rangeCD.contains('c'));
        assertTrue(rangeCD.contains('d'));
        assertFalse(rangeCD.contains('e'));

        // Range 'd'-'c' (reversed order): constructor normalises to 'c'-'d', same result
        final CharRange rangeDC = CharRange.isIn('d', 'c');
        assertFalse(rangeDC.contains('b'));
        assertTrue(rangeDC.contains('c'));
        assertTrue(rangeDC.contains('d'));
        assertFalse(rangeDC.contains('e'));

        // Negated range 'c'-'d': everything outside 'c'-'d' is contained
        final CharRange notInCD = CharRange.isNotIn('c', 'd');
        assertTrue(notInCD.contains('b'));
        assertFalse(notInCD.contains('c'));
        assertFalse(notInCD.contains('d'));
        assertTrue(notInCD.contains('e'));
        assertTrue(notInCD.contains((char) 0));
        assertTrue(notInCD.contains(Character.MAX_VALUE));
    }

    @Test
    void testContains_Charrange() {
        final CharRange a = CharRange.is('a');
        final CharRange b = CharRange.is('b');
        final CharRange c = CharRange.is('c');
        final CharRange c2 = CharRange.is('c');
        final CharRange d = CharRange.is('d');
        final CharRange e = CharRange.is('e');
        final CharRange cd = CharRange.isIn('c', 'd');
        final CharRange bd = CharRange.isIn('b', 'd');
        final CharRange bc = CharRange.isIn('b', 'c');
        final CharRange ab = CharRange.isIn('a', 'b');
        final CharRange de = CharRange.isIn('d', 'e');
        final CharRange ef = CharRange.isIn('e', 'f');
        final CharRange ae = CharRange.isIn('a', 'e');

        // normal range contains normal range
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

        assertTrue(ae.contains(b));
        assertTrue(ae.contains(ab));
        assertTrue(ae.contains(bc));
        assertTrue(ae.contains(cd));
        assertTrue(ae.contains(de));

        final CharRange notb = CharRange.isNot('b');
        final CharRange notc = CharRange.isNot('c');
        final CharRange notd = CharRange.isNot('d');
        final CharRange notab = CharRange.isNotIn('a', 'b');
        final CharRange notbc = CharRange.isNotIn('b', 'c');
        final CharRange notbd = CharRange.isNotIn('b', 'd');
        final CharRange notcd = CharRange.isNotIn('c', 'd');
        final CharRange notde = CharRange.isNotIn('d', 'e');
        final CharRange notae = CharRange.isNotIn('a', 'e');
        final CharRange all = CharRange.isIn((char) 0, Character.MAX_VALUE);
        final CharRange allbutfirst = CharRange.isIn((char) 1, Character.MAX_VALUE);

        // normal range contains negated range: only the full Unicode range covers any negated range
        assertFalse(c.contains(notc));
        assertFalse(c.contains(notbd));
        assertTrue(all.contains(notc));
        assertTrue(all.contains(notbd));
        assertFalse(allbutfirst.contains(notc));
        assertFalse(allbutfirst.contains(notbd));

        // negated range contains normal range: the excluded gap determines what fits inside
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

        // negated range contains negated range: outer excluded gap must be a subset of inner excluded gap
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
        final CharRange range = CharRange.is('a');
        final NullPointerException e = assertNullPointerException(() -> range.contains(null));
        assertEquals("range", e.getMessage());
    }

    @Test
    void testEquals_Object() {
        final CharRange rangea = CharRange.is('a');
        final CharRange rangeae = CharRange.isIn('a', 'e');
        final CharRange rangenotbf = CharRange.isIn('b', 'f');

        assertNotEquals(null, rangea);

        assertEquals(rangea, rangea);
        assertEquals(rangea, CharRange.is('a'));
        assertEquals(rangeae, rangeae);
        assertEquals(rangeae, CharRange.isIn('a', 'e'));
        assertEquals(rangenotbf, rangenotbf);
        assertEquals(rangenotbf, CharRange.isIn('b', 'f'));

        assertNotEquals(rangea, rangeae);
        assertNotEquals(rangea, rangenotbf);
        assertNotEquals(rangeae, rangea);
        assertNotEquals(rangeae, rangenotbf);
        assertNotEquals(rangenotbf, rangea);
        assertNotEquals(rangenotbf, rangeae);
    }

    @Test
    void testHashCode() {
        final CharRange rangea = CharRange.is('a');
        final CharRange rangeae = CharRange.isIn('a', 'e');
        final CharRange rangenotbf = CharRange.isIn('b', 'f');

        assertEquals(rangea.hashCode(), rangea.hashCode());
        assertEquals(rangea.hashCode(), CharRange.is('a').hashCode());
        assertEquals(rangeae.hashCode(), rangeae.hashCode());
        assertEquals(rangeae.hashCode(), CharRange.isIn('a', 'e').hashCode());
        assertEquals(rangenotbf.hashCode(), rangenotbf.hashCode());
        assertEquals(rangenotbf.hashCode(), CharRange.isIn('b', 'f').hashCode());

        assertNotEquals(rangea.hashCode(), rangeae.hashCode());
        assertNotEquals(rangea.hashCode(), rangenotbf.hashCode());
        assertNotEquals(rangeae.hashCode(), rangea.hashCode());
        assertNotEquals(rangeae.hashCode(), rangenotbf.hashCode());
        assertNotEquals(rangenotbf.hashCode(), rangea.hashCode());
        assertNotEquals(rangenotbf.hashCode(), rangeae.hashCode());
    }

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-1802
     */
    @Test
    void testHashCodeLang1802() {
        // Ranges used to verify hash-code uniqueness across different start/end/negated combinations
        final CharRange singleA   = CharRange.is('a');
        final CharRange singleB   = CharRange.is('b');
        final CharRange rangeAtoZ = CharRange.isIn('a', 'z');
        final CharRange rangeBtoZ = CharRange.isIn('b', 'z');
        final CharRange notA      = CharRange.isNot('a');
        final CharRange notInAtoZ = CharRange.isNotIn('a', 'z');
        final CharRange notInBtoZ = CharRange.isNotIn('b', 'z');
        final CharRange in1to2    = CharRange.isIn((char) 1, (char) 2);
        final CharRange notIn1to2 = CharRange.isNotIn((char) 1, (char) 2);

        // Previously problematic cases from LANG-1802: these pairs had colliding hash codes before the fix
        final CharRange notIn1to2Again = CharRange.isNotIn((char) 1, (char) 2);
        final CharRange in2to2         = CharRange.isIn((char) 2, (char) 2);
        assertNotEquals(notIn1to2Again, in2to2, "Different ranges should not be equal");
        assertNotEquals(notIn1to2Again.hashCode(), in2to2.hashCode(), "Different ranges should have different hash codes");

        final CharRange in5to5    = CharRange.isIn((char) 5, (char) 5);
        final CharRange notIn4to5 = CharRange.isNotIn((char) 4, (char) 5);
        assertNotEquals(in5to5, notIn4to5, "Different ranges should not be equal");
        assertNotEquals(in5to5.hashCode(), notIn4to5.hashCode(), "Different ranges should have different hash codes");

        // Negated and non-negated ranges with the same bounds must not share a hash code
        final CharRange normalXY  = CharRange.isIn('x', 'y');
        final CharRange negatedXY = CharRange.isNotIn('x', 'y');
        assertNotEquals(normalXY, negatedXY, "Negated and normal ranges should not be equal");
        assertNotEquals(normalXY.hashCode(), negatedXY.hashCode(), "Negated and normal ranges should have different hash codes");

        // Ranges that differ in start, end, or negation flag must produce different hash codes
        assertNotEquals(singleA.hashCode(), singleB.hashCode(), "is('a') vs is('b')");
        assertNotEquals(singleA.hashCode(), rangeAtoZ.hashCode(), "is('a') vs isIn('a', 'z')");
        assertNotEquals(rangeAtoZ.hashCode(), rangeBtoZ.hashCode(), "isIn('a', 'z') vs isIn('b', 'z')");
        assertNotEquals(singleA.hashCode(), notA.hashCode(), "is('a') vs isNot('a')");
        assertNotEquals(rangeAtoZ.hashCode(), notInAtoZ.hashCode(), "isIn('a', 'z') vs isNotIn('a', 'z')");
        assertNotEquals(notInAtoZ.hashCode(), notInBtoZ.hashCode(), "isNotIn('a', 'z') vs isNotIn('b', 'z')");
        assertNotEquals(in1to2.hashCode(), notIn1to2.hashCode(), "isIn(1, 2) vs isNotIn(1, 2)");

        // Equal ranges must have equal hash codes
        final CharRange anotherSingleA = CharRange.is('a');
        assertEquals(singleA, anotherSingleA, "Equal ranges should be equal");
        assertEquals(singleA.hashCode(), anotherSingleA.hashCode(), "Equal ranges should have equal hash codes");
    }

    @Test
    void testIterator() {
        final CharRange a        = CharRange.is('a');
        final CharRange ad       = CharRange.isIn('a', 'd');
        final CharRange nota     = CharRange.isNot('a');
        final CharRange emptySet = CharRange.isNotIn((char) 0, Character.MAX_VALUE);
        final CharRange notFirst = CharRange.isNotIn((char) 1, Character.MAX_VALUE);
        final CharRange notLast  = CharRange.isNotIn((char) 0, (char) (Character.MAX_VALUE - 1));

        // Single character 'a': iterator yields exactly one element
        final Iterator<Character> aIt = a.iterator();
        assertNotNull(aIt);
        assertTrue(aIt.hasNext());
        assertEquals(Character.valueOf('a'), aIt.next());
        assertFalse(aIt.hasNext());

        // Range 'a'-'d': iterator yields a, b, c, d in order
        final Iterator<Character> adIt = ad.iterator();
        assertNotNull(adIt);
        assertTrue(adIt.hasNext());
        assertEquals(Character.valueOf('a'), adIt.next());
        assertEquals(Character.valueOf('b'), adIt.next());
        assertEquals(Character.valueOf('c'), adIt.next());
        assertEquals(Character.valueOf('d'), adIt.next());
        assertFalse(adIt.hasNext());

        // Negated 'a': iterator yields every character except 'a'
        final Iterator<Character> notaIt = nota.iterator();
        assertNotNull(notaIt);
        assertTrue(notaIt.hasNext());
        while (notaIt.hasNext()) {
            final Character ch = notaIt.next();
            assertNotEquals('a', ch.charValue());
        }

        // Empty set (negation of entire Unicode range): iterator has no elements
        final Iterator<Character> emptySetIt = emptySet.iterator();
        assertNotNull(emptySetIt);
        assertFalse(emptySetIt.hasNext());
        assertThrows(NoSuchElementException.class, emptySetIt::next);

        // Negation of [1, MAX_VALUE]: only char(0) is yielded
        final Iterator<Character> notFirstIt = notFirst.iterator();
        assertNotNull(notFirstIt);
        assertTrue(notFirstIt.hasNext());
        assertEquals(Character.valueOf((char) 0), notFirstIt.next());
        assertFalse(notFirstIt.hasNext());
        assertThrows(NoSuchElementException.class, notFirstIt::next);

        // Negation of [0, MAX_VALUE-1]: only MAX_VALUE is yielded
        final Iterator<Character> notLastIt = notLast.iterator();
        assertNotNull(notLastIt);
        assertTrue(notLastIt.hasNext());
        assertEquals(Character.valueOf(Character.MAX_VALUE), notLastIt.next());
        assertFalse(notLastIt.hasNext());
        assertThrows(NoSuchElementException.class, notLastIt::next);
    }

    @Test
    void testIteratorRemove() {
        final CharRange a = CharRange.is('a');
        final Iterator<Character> aIt = a.iterator();
        assertThrows(UnsupportedOperationException.class, aIt::remove);
    }

    @Test
    void testSerialization() {
        CharRange range = CharRange.is('a');
        assertEquals(range, SerializationUtils.clone(range));
        range = CharRange.isIn('a', 'e');
        assertEquals(range, SerializationUtils.clone(range));
        range = CharRange.isNotIn('a', 'e');
        assertEquals(range, SerializationUtils.clone(range));
    }
}
