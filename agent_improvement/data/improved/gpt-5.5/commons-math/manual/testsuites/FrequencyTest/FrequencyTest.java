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

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link Frequency} class.
 */
public final class FrequencyTest {
    private static final long ONE_LONG = 1L;
    private static final long TWO_LONG = 2L;
    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    /** test freq counts */
    @Test
    public void testCounts() {
        assertLongFrequencyCounts();
        assertStringFrequencyCounts();
        assertIntegerFrequencyCounts();
        assertCaseInsensitiveStringFrequencyCounts();
        assertCharacterFrequencyCounts();
    }

    /** test pcts */
    @Test
    public void testPcts() {
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);
        frequency.addValue(THREE_LONG);
        frequency.addValue(THREE_LONG);

        Assert.assertEquals("two pct", 0.25, frequency.getPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("two cum pct", 0.50, frequency.getCumPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("three cum pct", 1.0, frequency.getCumPct(THREE_LONG), TOLERANCE);
    }

    /** test adding incomparable values */
    @Test
    public void testAdd() {
        Frequency<Character> frequency = new Frequency<>();
        char aChar = 'a';
        char bChar = 'b';
        frequency.addValue(aChar);
        frequency.addValue(bChar);

        Assert.assertEquals("a pct", 0.5, frequency.getPct(aChar), TOLERANCE);
        Assert.assertEquals("b cum pct", 1.0, frequency.getCumPct(bChar), TOLERANCE);
    }

    /** test empty table */
    @Test
    public void testEmptyTable() {
        Frequency<Integer> frequency = new Frequency<>();

        Assert.assertEquals("freq sum, empty table", 0, frequency.getSumFreq());
        Assert.assertEquals("count, empty table", 0, frequency.getCount(0));
        Assert.assertEquals("count, empty table", 0, frequency.getCount(Integer.valueOf(0)));
        Assert.assertEquals("cum freq, empty table", 0, frequency.getCumFreq(0));
        Assert.assertTrue("pct, empty table", Double.isNaN(frequency.getPct(0)));
        Assert.assertTrue("pct, empty table", Double.isNaN(frequency.getPct(Integer.valueOf(0))));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(frequency.getCumPct(0)));
        Assert.assertTrue("cum pct, empty table", Double.isNaN(frequency.getCumPct(Integer.valueOf(0))));
    }

    /**
     * Tests toString()
     */
    @Test
    public void testToString() throws Exception {
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);

        String table = frequency.toString();
        Assert.assertNotNull(table);

        BufferedReader reader = new BufferedReader(new StringReader(table));
        String line = reader.readLine(); // header line
        Assert.assertNotNull(line);

        line = reader.readLine(); // one's or two's line
        Assert.assertNotNull(line);
    }

    @Test
    public void testIntegerValues() {
        Frequency<Integer> frequency = new Frequency<>();
        frequency.addValue(Integer.valueOf(1));
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(Integer.valueOf(2));

        Assert.assertEquals("Integer 1 count", 2, frequency.getCount(1));
        Assert.assertEquals("Integer 1 count", 2, frequency.getCount(Integer.valueOf(1)));
        Assert.assertEquals("Integer 1 cumPct", 0.5, frequency.getCumPct(1), TOLERANCE);
        Assert.assertEquals("Integer 1 cumPct", 0.5, frequency.getCumPct(Integer.valueOf(1)), TOLERANCE);

        frequency.incrementValue(ONE, -2);
        frequency.incrementValue(THREE, 5);

        Assert.assertEquals("Integer 1 count", 0, frequency.getCount(1));
        Assert.assertEquals("Integer 3 count", 5, frequency.getCount(3));

        Iterator<?> iterator = frequency.valuesIterator();
        while (iterator.hasNext()) {
            Assert.assertTrue(iterator.next() instanceof Integer);
        }
    }

    @Test
    public void testGetUniqueCount() {
        Frequency<Long> frequency = new Frequency<>();

        Assert.assertEquals(0, frequency.getUniqueCount());
        frequency.addValue(ONE_LONG);
        Assert.assertEquals(1, frequency.getUniqueCount());
        frequency.addValue(ONE_LONG);
        Assert.assertEquals(1, frequency.getUniqueCount());
        frequency.addValue(TWO_LONG);
        Assert.assertEquals(2, frequency.getUniqueCount());
    }

    @Test
    public void testIncrement() {
        Frequency<Long> frequency = new Frequency<>();

        Assert.assertEquals(0, frequency.getUniqueCount());
        frequency.incrementValue(ONE_LONG, 1);
        Assert.assertEquals(1, frequency.getCount(ONE_LONG));

        frequency.incrementValue(ONE_LONG, 4);
        Assert.assertEquals(5, frequency.getCount(ONE_LONG));

        frequency.incrementValue(ONE_LONG, -5);
        Assert.assertEquals(0, frequency.getCount(ONE_LONG));
    }

    @Test
    public void testMerge() {
        Frequency<Long> frequency = new Frequency<>();
        Assert.assertEquals(0, frequency.getUniqueCount());
        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);
        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);

        Assert.assertEquals(2, frequency.getUniqueCount());
        Assert.assertEquals(2, frequency.getCount(ONE_LONG));
        Assert.assertEquals(2, frequency.getCount(TWO_LONG));

        Frequency<Long> otherFrequency = new Frequency<>();
        otherFrequency.addValue(ONE_LONG);
        otherFrequency.addValue(THREE_LONG);
        otherFrequency.addValue(THREE_LONG);

        Assert.assertEquals(2, otherFrequency.getUniqueCount());
        Assert.assertEquals(1, otherFrequency.getCount(ONE_LONG));
        Assert.assertEquals(2, otherFrequency.getCount(THREE_LONG));

        frequency.merge(otherFrequency);

        Assert.assertEquals(3, frequency.getUniqueCount());
        Assert.assertEquals(3, frequency.getCount(ONE_LONG));
        Assert.assertEquals(2, frequency.getCount(TWO_LONG));
        Assert.assertEquals(2, frequency.getCount(THREE_LONG));
    }

    @Test
    public void testMergeCollection() {
        Frequency<Long> frequency = new Frequency<>();
        Assert.assertEquals(0, frequency.getUniqueCount());
        frequency.addValue(ONE_LONG);

        Assert.assertEquals(1, frequency.getUniqueCount());
        Assert.assertEquals(1, frequency.getCount(ONE_LONG));
        Assert.assertEquals(0, frequency.getCount(TWO_LONG));

        Frequency<Long> secondFrequency = new Frequency<Long>();
        secondFrequency.addValue(TWO_LONG);

        Frequency<Long> thirdFrequency = new Frequency<Long>();
        thirdFrequency.addValue(THREE_LONG);

        List<Frequency<Long>> frequenciesToMerge = new ArrayList<>();
        frequenciesToMerge.add(secondFrequency);
        frequenciesToMerge.add(thirdFrequency);
        frequency.merge(frequenciesToMerge);

        Assert.assertEquals(3, frequency.getUniqueCount());
        Assert.assertEquals(1, frequency.getCount(ONE_LONG));
        Assert.assertEquals(1, frequency.getCount(TWO_LONG));
        Assert.assertEquals(1, frequency.getCount(THREE_LONG));
    }

    @Test
    public void testMode() {
        Frequency<String> frequency = new Frequency<>();

        List<String> mode = frequency.getMode();
        Assert.assertEquals(0, mode.size());

        frequency.addValue("3");
        mode = frequency.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("3", mode.get(0));

        frequency.addValue("2");
        mode = frequency.getMode();
        Assert.assertEquals(2, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertEquals("3", mode.get(1));

        frequency.addValue("2");
        mode = frequency.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertFalse(mode.contains("1"));
        Assert.assertTrue(mode.contains("2"));
    }

    @Test
    public void testModeDoubleNan() {
        Frequency<Double> frequency = new Frequency<>();
        frequency.addValue(Double.valueOf(Double.NaN));
        frequency.addValue(Double.valueOf(Double.NaN));
        frequency.addValue(Double.valueOf(Double.NaN));
        frequency.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        frequency.addValue(Double.valueOf(Double.POSITIVE_INFINITY));

        List<Double> mode = frequency.getMode();
        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Double.valueOf(Double.NaN), mode.get(2));
    }

    @Test
    public void testModeFloatNan() {
        Frequency<Float> frequency = new Frequency<>();
        frequency.addValue(Float.valueOf(Float.NaN));
        frequency.addValue(Float.valueOf(Float.NaN));
        frequency.addValue(Float.valueOf(Float.NaN));
        frequency.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        frequency.addValue(Float.valueOf(Float.POSITIVE_INFINITY));

        List<Float> mode = frequency.getMode();
        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Float.valueOf(Float.NaN), mode.get(2));
    }

    /**
     * The Frequency class ignores the comparator for equality.
     * See MATH-1689.
     */
    @Test
    public void testEqualsIgnoresComparator() {
        Frequency<Integer> ascending = new Frequency<>();
        Frequency<Integer> descending = new Frequency<Integer>(Comparator.reverseOrder());
        ascending.addValue(1);
        ascending.addValue(2);
        descending.addValue(1);
        descending.addValue(2);

        Assert.assertEquals(1, ascending.getCumFreq(1));
        Assert.assertEquals(2, descending.getCumFreq(1));
        Assert.assertEquals("[1, 2]", ascending.getMode().toString());
        Assert.assertEquals("[2, 1]", descending.getMode().toString());
        Assert.assertEquals(ascending, descending);
    }

    private void assertLongFrequencyCounts() {
        Frequency<Long> frequency = new Frequency<>();
        Assert.assertEquals("total count", 0, frequency.getSumFreq());
        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);
        frequency.addValue(1L);
        frequency.addValue(ONE_LONG);

        Assert.assertEquals("one frequency count", 3, frequency.getCount(1L));
        Assert.assertEquals("two frequency count", 1, frequency.getCount(2L));
        Assert.assertEquals("three frequency count", 0, frequency.getCount(3L));
        Assert.assertEquals("total count", 4, frequency.getSumFreq());
        Assert.assertEquals("zero cumulative frequency", 0, frequency.getCumFreq(0L));
        Assert.assertEquals("one cumulative frequency", 3, frequency.getCumFreq(1L));
        Assert.assertEquals("two cumulative frequency", 4, frequency.getCumFreq(2L));
        Assert.assertEquals("Integer argument cum freq", 4, frequency.getCumFreq(Long.valueOf(2)));
        Assert.assertEquals("five cumulative frequency", 4, frequency.getCumFreq(5L));
        Assert.assertEquals("foo cumulative frequency", 0, frequency.getCumFreq(-1L));

        frequency.clear();
        Assert.assertEquals("total count", 0, frequency.getSumFreq());
    }

    private void assertStringFrequencyCounts() {
        Frequency<String> frequency = new Frequency<>();
        frequency.addValue("one");
        frequency.addValue("One");
        frequency.addValue("oNe");
        frequency.addValue("Z");

        Assert.assertEquals("one cumulative frequency", 1, frequency.getCount("one"));
        Assert.assertEquals("Z cumulative pct", 0.5, frequency.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("z cumulative pct", 1.0, frequency.getCumPct("z"), TOLERANCE);
        Assert.assertEquals("Ot cumulative pct", 0.25, frequency.getCumPct("Ot"), TOLERANCE);
    }

    private void assertIntegerFrequencyCounts() {
        Frequency<Integer> frequency = new Frequency<>();
        frequency.addValue(1);
        frequency.addValue(Integer.valueOf(1));
        frequency.addValue(ONE);
        frequency.addValue(2);
        frequency.addValue(Integer.valueOf(-1));

        Assert.assertEquals("1 count", 3, frequency.getCount(1));
        Assert.assertEquals("1 count", 3, frequency.getCount(Integer.valueOf(1)));
        Assert.assertEquals("0 cum pct", 0.2, frequency.getCumPct(0), TOLERANCE);
        Assert.assertEquals("1 pct", 0.6, frequency.getPct(Integer.valueOf(1)), TOLERANCE);
        Assert.assertEquals("-2 cum pct", 0, frequency.getCumPct(-2), TOLERANCE);
        Assert.assertEquals("10 cum pct", 1, frequency.getCumPct(10), TOLERANCE);
    }

    private void assertCaseInsensitiveStringFrequencyCounts() {
        Frequency<String> frequency = new Frequency<>(String.CASE_INSENSITIVE_ORDER);
        frequency.addValue("one");
        frequency.addValue("One");
        frequency.addValue("oNe");
        frequency.addValue("Z");

        Assert.assertEquals("one count", 3, frequency.getCount("one"));
        Assert.assertEquals("Z cumulative pct -- case insensitive", 1, frequency.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("z cumulative pct -- case insensitive", 1, frequency.getCumPct("z"), TOLERANCE);
    }

    private void assertCharacterFrequencyCounts() {
        Frequency<Character> frequency = new Frequency<>();
        Assert.assertEquals(0L, frequency.getCount('a'));
        Assert.assertEquals(0L, frequency.getCumFreq('b'));
        TestUtils.assertEquals(Double.NaN, frequency.getPct('a'), 0.0);
        TestUtils.assertEquals(Double.NaN, frequency.getCumPct('b'), 0.0);

        frequency.addValue('a');
        frequency.addValue('b');
        frequency.addValue('c');
        frequency.addValue('d');

        Assert.assertEquals(1L, frequency.getCount('a'));
        Assert.assertEquals(2L, frequency.getCumFreq('b'));
        Assert.assertEquals(0.25, frequency.getPct('a'), 0.0);
        Assert.assertEquals(0.5, frequency.getCumPct('b'), 0.0);
        Assert.assertEquals(1.0, frequency.getCumPct('e'), 0.0);
    }
}
