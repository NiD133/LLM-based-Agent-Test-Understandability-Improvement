/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math4.legacy.stat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Test;

/**
 * Test cases for the {@link Frequency} class.
 */
public final class FrequencyTest {

    /** Tolerance used when comparing percentage (double) results. */
    private static final double TOLERANCE = 10E-15d;

    /**
     * Adding the same long value several times must accumulate its count, and
     * cumulative frequencies must include every value less than or equal to the
     * queried one.
     */
    @Test
    public void testLongCountsAndCumulativeFrequencies() {
        final Frequency<Long> freq = new Frequency<>();
        assertEquals("total count", 0, freq.getSumFreq());

        // Add the value 1 three times and the value 2 once.
        freq.addValue(1L);
        freq.addValue(2L);
        freq.addValue(1L);
        freq.addValue(1L);

        // Per-value counts.
        assertEquals("one frequency count", 3, freq.getCount(1L));
        assertEquals("two frequency count", 1, freq.getCount(2L));
        assertEquals("three frequency count", 0, freq.getCount(3L));
        assertEquals("total count", 4, freq.getSumFreq());

        // Cumulative frequency = number of observations <= the queried value.
        assertEquals("zero cumulative frequency", 0, freq.getCumFreq(0L));
        assertEquals("one cumulative frequency", 3, freq.getCumFreq(1L));
        assertEquals("two cumulative frequency", 4, freq.getCumFreq(2L));
        assertEquals("Long argument cum freq", 4, freq.getCumFreq(Long.valueOf(2)));
        assertEquals("above-max cumulative frequency", 4, freq.getCumFreq(5L));
        assertEquals("below-min cumulative frequency", 0, freq.getCumFreq(-1L));

        // clear() resets the table to empty.
        freq.clear();
        assertEquals("total count after clear", 0, freq.getSumFreq());
    }

    /**
     * With the default (case-sensitive) ordering, distinct casings are distinct
     * values, so cumulative percentages reflect natural String ordering.
     */
    @Test
    public void testCaseSensitiveStringCumulativePercentages() {
        final Frequency<String> freq = new Frequency<>();
        freq.addValue("one");
        freq.addValue("One");
        freq.addValue("oNe");
        freq.addValue("Z");

        assertEquals("count of \"one\"", 1, freq.getCount("one"));
        assertEquals("Z cumulative pct", 0.5, freq.getCumPct("Z"), TOLERANCE);
        assertEquals("z cumulative pct", 1.0, freq.getCumPct("z"), TOLERANCE);
        assertEquals("Ot cumulative pct", 0.25, freq.getCumPct("Ot"), TOLERANCE);
    }

    /**
     * Integer percentages: getPct is the share of a single value, getCumPct is
     * the share of all values less than or equal to the queried one.
     */
    @Test
    public void testIntegerPercentages() {
        final Frequency<Integer> freq = new Frequency<>();
        // The value 1 is added three times; 2 and -1 once each (5 observations).
        freq.addValue(1);
        freq.addValue(Integer.valueOf(1));
        freq.addValue(1);
        freq.addValue(2);
        freq.addValue(Integer.valueOf(-1));

        assertEquals("count of 1 (primitive arg)", 3, freq.getCount(1));
        assertEquals("count of 1 (boxed arg)", 3, freq.getCount(Integer.valueOf(1)));

        assertEquals("cum pct at 0", 0.2, freq.getCumPct(0), TOLERANCE);
        assertEquals("pct of 1", 0.6, freq.getPct(Integer.valueOf(1)), TOLERANCE);
        assertEquals("cum pct below min", 0, freq.getCumPct(-2), TOLERANCE);
        assertEquals("cum pct above max", 1, freq.getCumPct(10), TOLERANCE);
    }

    /**
     * A case-insensitive comparator collapses different casings into a single
     * value, so "one"/"One"/"oNe" share one count.
     */
    @Test
    public void testCaseInsensitiveStringCounts() {
        final Frequency<String> freq = new Frequency<>(String.CASE_INSENSITIVE_ORDER);
        freq.addValue("one");
        freq.addValue("One");
        freq.addValue("oNe");
        freq.addValue("Z");

        assertEquals("count of \"one\"", 3, freq.getCount("one"));
        assertEquals("Z cumulative pct -- case insensitive", 1, freq.getCumPct("Z"), TOLERANCE);
        assertEquals("z cumulative pct -- case insensitive", 1, freq.getCumPct("z"), TOLERANCE);
    }

    /**
     * Characters behave like any other comparable value: an empty table returns
     * zero counts and NaN percentages; after adding values the counts and
     * (cumulative) percentages are well defined.
     */
    @Test
    public void testCharacterCountsAndPercentages() {
        final Frequency<Character> freq = new Frequency<>();

        // Empty table.
        assertEquals(0L, freq.getCount('a'));
        assertEquals(0L, freq.getCumFreq('b'));
        TestUtils.assertEquals(Double.NaN, freq.getPct('a'), 0.0);
        TestUtils.assertEquals(Double.NaN, freq.getCumPct('b'), 0.0);

        // Add one occurrence each of 'a', 'b', 'c', 'd'.
        freq.addValue('a');
        freq.addValue('b');
        freq.addValue('c');
        freq.addValue('d');

        assertEquals(1L, freq.getCount('a'));
        assertEquals(2L, freq.getCumFreq('b'));
        assertEquals(0.25, freq.getPct('a'), 0.0);
        assertEquals(0.5, freq.getCumPct('b'), 0.0);
        assertEquals(1.0, freq.getCumPct('e'), 0.0);
    }

    /** Percentage and cumulative-percentage of long values. */
    @Test
    public void testPcts() {
        final Frequency<Long> freq = new Frequency<>();
        // Four observations: 1, 2, 3, 3.
        freq.addValue(1L);
        freq.addValue(2L);
        freq.addValue(3L);
        freq.addValue(3L);

        assertEquals("two pct", 0.25, freq.getPct(Long.valueOf(2)), TOLERANCE);
        assertEquals("two cum pct", 0.50, freq.getCumPct(Long.valueOf(2)), TOLERANCE);
        assertEquals("three cum pct", 1.0, freq.getCumPct(3L), TOLERANCE);
    }

    /** Values that are comparable but not numeric (chars) can still be added. */
    @Test
    public void testAdd() {
        final Frequency<Character> freq = new Frequency<>();
        freq.addValue('a');
        freq.addValue('b');

        assertEquals("a pct", 0.5, freq.getPct('a'), TOLERANCE);
        assertEquals("b cum pct", 1.0, freq.getCumPct('b'), TOLERANCE);
    }

    /** An empty table reports zero counts/frequencies and NaN percentages. */
    @Test
    public void testEmptyTable() {
        final Frequency<Integer> freq = new Frequency<>();
        assertEquals("freq sum, empty table", 0, freq.getSumFreq());
        assertEquals("count, empty table", 0, freq.getCount(0));
        assertEquals("count, empty table", 0, freq.getCount(Integer.valueOf(0)));
        assertEquals("cum freq, empty table", 0, freq.getCumFreq(0));
        assertTrue("pct, empty table", Double.isNaN(freq.getPct(0)));
        assertTrue("pct, empty table", Double.isNaN(freq.getPct(Integer.valueOf(0))));
        assertTrue("cum pct, empty table", Double.isNaN(freq.getCumPct(0)));
        assertTrue("cum pct, empty table", Double.isNaN(freq.getCumPct(Integer.valueOf(0))));
    }

    /** toString() produces at least a header line plus one data line. */
    @Test
    public void testToString() throws Exception {
        final Frequency<Long> freq = new Frequency<>();
        freq.addValue(1L);
        freq.addValue(2L);

        final String text = freq.toString();
        assertNotNull(text);

        final BufferedReader reader = new BufferedReader(new StringReader(text));
        assertNotNull("header line", reader.readLine());
        assertNotNull("first data line", reader.readLine());
    }

    /** incrementValue can both add and remove counts; iterated keys stay Integer. */
    @Test
    public void testIntegerValues() {
        final Frequency<Integer> freq = new Frequency<>();
        freq.addValue(Integer.valueOf(1));
        freq.addValue(1);
        freq.addValue(2);
        freq.addValue(Integer.valueOf(2));

        assertEquals("Integer 1 count", 2, freq.getCount(1));
        assertEquals("Integer 1 count", 2, freq.getCount(Integer.valueOf(1)));
        assertEquals("Integer 1 cumPct", 0.5, freq.getCumPct(1), TOLERANCE);
        assertEquals("Integer 1 cumPct", 0.5, freq.getCumPct(Integer.valueOf(1)), TOLERANCE);

        // Decrement 1 below zero (clamped to 0) and create 3 with a count of 5.
        freq.incrementValue(1, -2);
        freq.incrementValue(3, 5);

        assertEquals("Integer 1 count", 0, freq.getCount(1));
        assertEquals("Integer 3 count", 5, freq.getCount(3));

        final Iterator<?> it = freq.valuesIterator();
        while (it.hasNext()) {
            assertTrue(it.next() instanceof Integer);
        }
    }

    /** getUniqueCount counts distinct values, regardless of how often each occurs. */
    @Test
    public void testGetUniqueCount() {
        final Frequency<Long> freq = new Frequency<>();
        assertEquals(0, freq.getUniqueCount());

        freq.addValue(1L);
        assertEquals(1, freq.getUniqueCount());

        freq.addValue(1L);
        assertEquals(1, freq.getUniqueCount());

        freq.addValue(2L);
        assertEquals(2, freq.getUniqueCount());
    }

    /** incrementValue raises and lowers a value's count, clamping at zero. */
    @Test
    public void testIncrement() {
        final Frequency<Long> freq = new Frequency<>();
        assertEquals(0, freq.getUniqueCount());

        freq.incrementValue(1L, 1);
        assertEquals(1, freq.getCount(1L));

        freq.incrementValue(1L, 4);
        assertEquals(5, freq.getCount(1L));

        freq.incrementValue(1L, -5);
        assertEquals(0, freq.getCount(1L));
    }

    /** Merging another Frequency sums the per-value counts of both tables. */
    @Test
    public void testMerge() {
        final Frequency<Long> freq = new Frequency<>();
        assertEquals(0, freq.getUniqueCount());
        freq.addValue(1L);
        freq.addValue(2L);
        freq.addValue(1L);
        freq.addValue(2L);

        assertEquals(2, freq.getUniqueCount());
        assertEquals(2, freq.getCount(1L));
        assertEquals(2, freq.getCount(2L));

        final Frequency<Long> other = new Frequency<>();
        other.addValue(1L);
        other.addValue(3L);
        other.addValue(3L);

        assertEquals(2, other.getUniqueCount());
        assertEquals(1, other.getCount(1L));
        assertEquals(2, other.getCount(3L));

        freq.merge(other);

        assertEquals(3, freq.getUniqueCount());
        assertEquals(3, freq.getCount(1L));
        assertEquals(2, freq.getCount(2L));
        assertEquals(2, freq.getCount(3L));
    }

    /** Merging a collection of Frequencies folds them all into the target. */
    @Test
    public void testMergeCollection() {
        final Frequency<Long> freq = new Frequency<>();
        assertEquals(0, freq.getUniqueCount());
        freq.addValue(1L);

        assertEquals(1, freq.getUniqueCount());
        assertEquals(1, freq.getCount(1L));
        assertEquals(0, freq.getCount(2L));

        final Frequency<Long> withTwo = new Frequency<>();
        withTwo.addValue(2L);

        final Frequency<Long> withThree = new Frequency<>();
        withThree.addValue(3L);

        final List<Frequency<Long>> toMerge = new ArrayList<>();
        toMerge.add(withTwo);
        toMerge.add(withThree);
        freq.merge(toMerge);

        assertEquals(3, freq.getUniqueCount());
        assertEquals(1, freq.getCount(1L));
        assertEquals(1, freq.getCount(2L));
        assertEquals(1, freq.getCount(3L));
    }

    /** getMode returns all values tied for the highest count, in value order. */
    @Test
    public void testMode() {
        final Frequency<String> freq = new Frequency<>();

        // Empty table has no mode.
        assertEquals(0, freq.getMode().size());

        // Single value is the sole mode.
        freq.addValue("3");
        List<String> mode = freq.getMode();
        assertEquals(1, mode.size());
        assertEquals("3", mode.get(0));

        // Two values tied at count 1 -> both are modes, in value order.
        freq.addValue("2");
        mode = freq.getMode();
        assertEquals(2, mode.size());
        assertEquals("2", mode.get(0));
        assertEquals("3", mode.get(1));

        // "2" now has the strictly highest count -> unique mode.
        freq.addValue("2");
        mode = freq.getMode();
        assertEquals(1, mode.size());
        assertEquals("2", mode.get(0));
        assertFalse(mode.contains("1"));
        assertTrue(mode.contains("2"));
    }

    /** Mode ordering for Double values places NaN last (its natural ordering). */
    @Test
    public void testModeDoubleNan() {
        final Frequency<Double> freq = new Frequency<>();
        freq.addValue(Double.valueOf(Double.NaN));
        freq.addValue(Double.valueOf(Double.NaN));
        freq.addValue(Double.valueOf(Double.NaN));
        freq.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.POSITIVE_INFINITY));

        final List<Double> mode = freq.getMode();
        assertEquals(3, mode.size());
        assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY), mode.get(0));
        assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), mode.get(1));
        assertEquals(Double.valueOf(Double.NaN), mode.get(2));
    }

    /** Mode ordering for Float values places NaN last (its natural ordering). */
    @Test
    public void testModeFloatNan() {
        final Frequency<Float> freq = new Frequency<>();
        freq.addValue(Float.valueOf(Float.NaN));
        freq.addValue(Float.valueOf(Float.NaN));
        freq.addValue(Float.valueOf(Float.NaN));
        freq.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.POSITIVE_INFINITY));

        final List<Float> mode = freq.getMode();
        assertEquals(3, mode.size());
        assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        assertEquals(Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        assertEquals(Float.valueOf(Float.NaN), mode.get(2));
    }

    /**
     * The comparator affects ordering (cumulative frequency, mode order) but is
     * ignored for equality: two tables with the same counts are equal even with
     * opposite comparators. See MATH-1689.
     */
    @Test
    public void testEqualsIgnoresComparator() {
        final Frequency<Integer> ascending = new Frequency<>();
        final Frequency<Integer> descending = new Frequency<Integer>(Comparator.reverseOrder());
        ascending.addValue(1);
        ascending.addValue(2);
        descending.addValue(1);
        descending.addValue(2);

        // Ordering differs between the two comparators...
        assertEquals(1, ascending.getCumFreq(1));
        assertEquals(2, descending.getCumFreq(1));
        assertEquals("[1, 2]", ascending.getMode().toString());
        assertEquals("[2, 1]", descending.getMode().toString());

        // ...but equality ignores the comparator.
        assertEquals(ascending, descending);
    }
}
