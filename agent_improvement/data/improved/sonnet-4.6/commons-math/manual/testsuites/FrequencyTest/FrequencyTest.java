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

    // Named constants for repeated literal values used across tests
    private static final long ONE_LONG   = 1L;
    private static final long TWO_LONG   = 2L;
    private static final long THREE_LONG = 3L;
    private static final int  ONE   = 1;
    private static final int  TWO   = 2;
    private static final int  THREE = 3;

    /** Acceptable floating-point delta for percentage comparisons. */
    private static final double TOLERANCE = 10E-15d;

    /**
     * Verifies frequency counts, cumulative frequencies, and cumulative
     * percentages across Long, String, Integer, and Character element types.
     * Also verifies that {@code clear()} resets the table to zero.
     */
    @Test
    public void testCounts() {
        // --- Long frequency table ---
        Frequency<Long> longFreq = new Frequency<>();
        Assert.assertEquals("initial total count should be 0", 0, longFreq.getSumFreq());

        longFreq.addValue(ONE_LONG);
        longFreq.addValue(TWO_LONG);
        longFreq.addValue(1L);       // same as ONE_LONG
        longFreq.addValue(ONE_LONG);

        Assert.assertEquals("count of 1L should be 3",  3, longFreq.getCount(1L));
        Assert.assertEquals("count of 2L should be 1",  1, longFreq.getCount(2L));
        Assert.assertEquals("count of 3L should be 0",  0, longFreq.getCount(3L));
        Assert.assertEquals("total count should be 4",  4, longFreq.getSumFreq());

        Assert.assertEquals("cumFreq(0L) should be 0",  0, longFreq.getCumFreq(0L));
        Assert.assertEquals("cumFreq(1L) should be 3",  3, longFreq.getCumFreq(1L));
        Assert.assertEquals("cumFreq(2L) should be 4",  4, longFreq.getCumFreq(2L));
        Assert.assertEquals("cumFreq(Long.valueOf(2)) should be 4", 4, longFreq.getCumFreq(Long.valueOf(2)));
        Assert.assertEquals("cumFreq(5L) beyond max should be 4",  4, longFreq.getCumFreq(5L));
        Assert.assertEquals("cumFreq(-1L) below min should be 0",  0, longFreq.getCumFreq(-1L));

        longFreq.clear();
        Assert.assertEquals("total count after clear should be 0", 0, longFreq.getSumFreq());

        // --- String frequency table (case-sensitive, natural ordering) ---
        Frequency<String> caseSensitiveStringFreq = new Frequency<>();
        caseSensitiveStringFreq.addValue("one");
        caseSensitiveStringFreq.addValue("One");
        caseSensitiveStringFreq.addValue("oNe");
        caseSensitiveStringFreq.addValue("Z");

        Assert.assertEquals("count of \"one\" (case-sensitive) should be 1", 1,    caseSensitiveStringFreq.getCount("one"));
        Assert.assertEquals("cumPct(\"Z\") should be 0.5",  0.5,  caseSensitiveStringFreq.getCumPct("Z"),  TOLERANCE);
        Assert.assertEquals("cumPct(\"z\") should be 1.0",  1.0,  caseSensitiveStringFreq.getCumPct("z"),  TOLERANCE);
        Assert.assertEquals("cumPct(\"Ot\") should be 0.25", 0.25, caseSensitiveStringFreq.getCumPct("Ot"), TOLERANCE);

        // --- Integer frequency table ---
        Frequency<Integer> intFreq = new Frequency<>();
        intFreq.addValue(1);
        intFreq.addValue(Integer.valueOf(1));
        intFreq.addValue(ONE);
        intFreq.addValue(2);
        intFreq.addValue(Integer.valueOf(-1));

        Assert.assertEquals("count of 1 should be 3",           3,   intFreq.getCount(1));
        Assert.assertEquals("count of Integer(1) should be 3",  3,   intFreq.getCount(Integer.valueOf(1)));
        Assert.assertEquals("cumPct(0) should be 0.2",          0.2, intFreq.getCumPct(0),               TOLERANCE);
        Assert.assertEquals("pct(Integer(1)) should be 0.6",    0.6, intFreq.getPct(Integer.valueOf(1)), TOLERANCE);
        Assert.assertEquals("cumPct(-2) below min should be 0", 0,   intFreq.getCumPct(-2),              TOLERANCE);
        Assert.assertEquals("cumPct(10) above max should be 1", 1,   intFreq.getCumPct(10),              TOLERANCE);

        // --- String frequency table (case-insensitive comparator) ---
        Frequency<String> caseInsensitiveStringFreq = new Frequency<>(String.CASE_INSENSITIVE_ORDER);
        caseInsensitiveStringFreq.addValue("one");
        caseInsensitiveStringFreq.addValue("One");
        caseInsensitiveStringFreq.addValue("oNe");
        caseInsensitiveStringFreq.addValue("Z");

        Assert.assertEquals("count of \"one\" (case-insensitive) should be 3", 3, caseInsensitiveStringFreq.getCount("one"));
        Assert.assertEquals("cumPct(\"Z\") case-insensitive should be 1",      1, caseInsensitiveStringFreq.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("cumPct(\"z\") case-insensitive should be 1",      1, caseInsensitiveStringFreq.getCumPct("z"), TOLERANCE);

        // --- Character frequency table ---
        Frequency<Character> charFreq = new Frequency<>();

        // Empty table: counts and percentages before any values are added
        Assert.assertEquals(0L, charFreq.getCount('a'));
        Assert.assertEquals(0L, charFreq.getCumFreq('b'));
        TestUtils.assertEquals(Double.NaN, charFreq.getPct('a'),    0.0);
        TestUtils.assertEquals(Double.NaN, charFreq.getCumPct('b'), 0.0);

        charFreq.addValue('a');
        charFreq.addValue('b');
        charFreq.addValue('c');
        charFreq.addValue('d');

        Assert.assertEquals(1L,   charFreq.getCount('a'));
        Assert.assertEquals(2L,   charFreq.getCumFreq('b'));
        Assert.assertEquals(0.25, charFreq.getPct('a'),    0.0);
        Assert.assertEquals(0.5,  charFreq.getCumPct('b'), 0.0);
        Assert.assertEquals(1.0,  charFreq.getCumPct('e'), 0.0);
    }

    /**
     * Verifies that {@code getPct} and {@code getCumPct} return correct
     * proportions when the table contains three distinct Long values.
     */
    @Test
    public void testPcts() {
        Frequency<Long> freq = new Frequency<>();
        freq.addValue(ONE_LONG);
        freq.addValue(TWO_LONG);
        freq.addValue(THREE_LONG);
        freq.addValue(THREE_LONG);

        Assert.assertEquals("pct(2L) should be 0.25",    0.25, freq.getPct(Long.valueOf(2)),    TOLERANCE);
        Assert.assertEquals("cumPct(2L) should be 0.50", 0.50, freq.getCumPct(Long.valueOf(2)), TOLERANCE);
        Assert.assertEquals("cumPct(3L) should be 1.0",  1.0,  freq.getCumPct(THREE_LONG),      TOLERANCE);
    }

    /**
     * Verifies that Character values are stored and their percentages
     * and cumulative percentages are computed correctly.
     */
    @Test
    public void testAdd() {
        Frequency<Character> freq = new Frequency<>();
        char aChar = 'a';
        char bChar = 'b';
        freq.addValue(aChar);
        freq.addValue(bChar);

        Assert.assertEquals("pct('a') should be 0.5",    0.5, freq.getPct(aChar),    TOLERANCE);
        Assert.assertEquals("cumPct('b') should be 1.0", 1.0, freq.getCumPct(bChar), TOLERANCE);
    }

    /**
     * Verifies that an empty Frequency table returns zero counts, zero
     * cumulative frequencies, and NaN for all percentage queries.
     */
    @Test
    public void testEmptyTable() {
        Frequency<Integer> freq = new Frequency<>();

        Assert.assertEquals("sumFreq on empty table should be 0",         0, freq.getSumFreq());
        Assert.assertEquals("count(0) on empty table should be 0",        0, freq.getCount(0));
        Assert.assertEquals("count(Integer(0)) on empty table should be 0", 0, freq.getCount(Integer.valueOf(0)));
        Assert.assertEquals("cumFreq(0) on empty table should be 0",      0, freq.getCumFreq(0));

        Assert.assertTrue("pct(0) on empty table should be NaN",          Double.isNaN(freq.getPct(0)));
        Assert.assertTrue("pct(Integer(0)) on empty table should be NaN", Double.isNaN(freq.getPct(Integer.valueOf(0))));
        Assert.assertTrue("cumPct(0) on empty table should be NaN",       Double.isNaN(freq.getCumPct(0)));
        Assert.assertTrue("cumPct(Integer(0)) on empty table should be NaN", Double.isNaN(freq.getCumPct(Integer.valueOf(0))));
    }

    /**
     * Verifies that {@code toString()} produces a non-null string with at
     * least a header line and one data line.
     */
    @Test
    public void testToString() throws Exception {
        Frequency<Long> freq = new Frequency<>();
        freq.addValue(ONE_LONG);
        freq.addValue(TWO_LONG);

        String tableOutput = freq.toString();
        Assert.assertNotNull(tableOutput);

        BufferedReader reader = new BufferedReader(new StringReader(tableOutput));
        String headerLine = reader.readLine();
        Assert.assertNotNull("header line should not be null", headerLine);

        String firstDataLine = reader.readLine();
        Assert.assertNotNull("first data line should not be null", firstDataLine);
    }

    /**
     * Verifies that Integer autoboxing is handled uniformly, that
     * {@code incrementValue} adjusts counts correctly (including to zero),
     * and that {@code valuesIterator} returns Integer instances.
     */
    @Test
    public void testIntegerValues() {
        Frequency<Integer> freq = new Frequency<>();
        freq.addValue(Integer.valueOf(1));
        freq.addValue(1);
        freq.addValue(2);
        freq.addValue(Integer.valueOf(2));

        Assert.assertEquals("count of 1 after two adds should be 2",          2,   freq.getCount(1));
        Assert.assertEquals("count of Integer(1) after two adds should be 2", 2,   freq.getCount(Integer.valueOf(1)));
        Assert.assertEquals("cumPct(1) should be 0.5",                        0.5, freq.getCumPct(1),               TOLERANCE);
        Assert.assertEquals("cumPct(Integer(1)) should be 0.5",               0.5, freq.getCumPct(Integer.valueOf(1)), TOLERANCE);

        // Decrement 1's count to zero; add 5 occurrences of 3
        freq.incrementValue(ONE,   -2);
        freq.incrementValue(THREE,  5);

        Assert.assertEquals("count of 1 after decrement by 2 should be 0", 0, freq.getCount(1));
        Assert.assertEquals("count of 3 after increment by 5 should be 5", 5, freq.getCount(3));

        Iterator<?> it = freq.valuesIterator();
        while (it.hasNext()) {
            Assert.assertTrue("all values should be Integer instances", it.next() instanceof Integer);
        }
    }

    /**
     * Verifies that {@code getUniqueCount} reflects the number of distinct
     * values, and does not increment when a duplicate is added.
     */
    @Test
    public void testGetUniqueCount() {
        Frequency<Long> freq = new Frequency<>();

        Assert.assertEquals("unique count on empty table should be 0", 0, freq.getUniqueCount());

        freq.addValue(ONE_LONG);
        Assert.assertEquals("unique count after adding ONE_LONG should be 1", 1, freq.getUniqueCount());

        freq.addValue(ONE_LONG);  // duplicate — unique count must not increase
        Assert.assertEquals("unique count after duplicate ONE_LONG should still be 1", 1, freq.getUniqueCount());

        freq.addValue(TWO_LONG);
        Assert.assertEquals("unique count after adding TWO_LONG should be 2", 2, freq.getUniqueCount());
    }

    /**
     * Verifies that {@code incrementValue} adjusts a value's count by the
     * given delta, including positive, larger positive, and negative deltas.
     */
    @Test
    public void testIncrement() {
        Frequency<Long> freq = new Frequency<>();
        Assert.assertEquals("unique count on empty table should be 0", 0, freq.getUniqueCount());

        freq.incrementValue(ONE_LONG, 1);
        Assert.assertEquals("count of ONE_LONG after +1 should be 1", 1, freq.getCount(ONE_LONG));

        freq.incrementValue(ONE_LONG, 4);
        Assert.assertEquals("count of ONE_LONG after further +4 should be 5", 5, freq.getCount(ONE_LONG));

        freq.incrementValue(ONE_LONG, -5);
        Assert.assertEquals("count of ONE_LONG after -5 should be 0", 0, freq.getCount(ONE_LONG));
    }

    /**
     * Verifies that {@code merge(Frequency)} combines counts from another
     * table into the receiver, summing counts for shared keys.
     */
    @Test
    public void testMerge() {
        Frequency<Long> primary = new Frequency<>();
        Assert.assertEquals("unique count on empty primary should be 0", 0, primary.getUniqueCount());

        primary.addValue(ONE_LONG);
        primary.addValue(TWO_LONG);
        primary.addValue(ONE_LONG);
        primary.addValue(TWO_LONG);

        Assert.assertEquals("primary unique count should be 2",    2, primary.getUniqueCount());
        Assert.assertEquals("primary count(ONE) should be 2",      2, primary.getCount(ONE_LONG));
        Assert.assertEquals("primary count(TWO) should be 2",      2, primary.getCount(TWO_LONG));

        Frequency<Long> secondary = new Frequency<>();
        secondary.addValue(ONE_LONG);
        secondary.addValue(THREE_LONG);
        secondary.addValue(THREE_LONG);

        Assert.assertEquals("secondary unique count should be 2",  2, secondary.getUniqueCount());
        Assert.assertEquals("secondary count(ONE) should be 1",    1, secondary.getCount(ONE_LONG));
        Assert.assertEquals("secondary count(THREE) should be 2",  2, secondary.getCount(THREE_LONG));

        primary.merge(secondary);

        Assert.assertEquals("merged unique count should be 3",     3, primary.getUniqueCount());
        Assert.assertEquals("merged count(ONE) should be 3",       3, primary.getCount(ONE_LONG));
        Assert.assertEquals("merged count(TWO) should be 2",       2, primary.getCount(TWO_LONG));
        Assert.assertEquals("merged count(THREE) should be 2",     2, primary.getCount(THREE_LONG));
    }

    /**
     * Verifies that {@code merge(Collection)} merges multiple Frequency
     * tables at once, combining counts from all tables in the collection.
     */
    @Test
    public void testMergeCollection() {
        Frequency<Long> primary = new Frequency<>();
        Assert.assertEquals("unique count on empty primary should be 0", 0, primary.getUniqueCount());

        primary.addValue(ONE_LONG);
        Assert.assertEquals("unique count after ONE should be 1",   1, primary.getUniqueCount());
        Assert.assertEquals("count(ONE) after ONE should be 1",     1, primary.getCount(ONE_LONG));
        Assert.assertEquals("count(TWO) before merge should be 0",  0, primary.getCount(TWO_LONG));

        Frequency<Long> withTwo   = new Frequency<Long>();
        withTwo.addValue(TWO_LONG);

        Frequency<Long> withThree = new Frequency<Long>();
        withThree.addValue(THREE_LONG);

        List<Frequency<Long>> others = new ArrayList<>();
        others.add(withTwo);
        others.add(withThree);
        primary.merge(others);

        Assert.assertEquals("unique count after collection merge should be 3", 3, primary.getUniqueCount());
        Assert.assertEquals("count(ONE) after merge should be 1",              1, primary.getCount(ONE_LONG));
        Assert.assertEquals("count(TWO) after merge should be 1",              1, primary.getCount(TWO_LONG));
        Assert.assertEquals("count(THREE) after merge should be 1",            1, primary.getCount(THREE_LONG));
    }

    /**
     * Verifies that {@code getMode()} returns the most-frequent value(s) in
     * natural (sorted) order, and updates as new values are added.
     */
    @Test
    public void testMode() {
        Frequency<String> freq = new Frequency<>();
        List<String> mode;

        mode = freq.getMode();
        Assert.assertEquals("mode of empty table should be empty list", 0, mode.size());

        freq.addValue("3");
        mode = freq.getMode();
        Assert.assertEquals("mode size should be 1 with one value",   1,   mode.size());
        Assert.assertEquals("mode should contain \"3\"",              "3", mode.get(0));

        freq.addValue("2");
        mode = freq.getMode();
        Assert.assertEquals("mode size should be 2 when tied",        2,   mode.size());
        Assert.assertEquals("mode[0] should be \"2\" (sorted first)", "2", mode.get(0));
        Assert.assertEquals("mode[1] should be \"3\"",                "3", mode.get(1));

        freq.addValue("2");  // "2" now has count 2, breaking the tie
        mode = freq.getMode();
        Assert.assertEquals("mode size should be 1 after tie broken", 1,   mode.size());
        Assert.assertEquals("mode should be \"2\"",                   "2", mode.get(0));
        Assert.assertFalse("mode should not contain \"1\"", mode.contains("1"));
        Assert.assertTrue("mode should contain \"2\"",      mode.contains("2"));
    }

    /**
     * Verifies that {@code getMode()} handles Double special values
     * (NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY) correctly.
     * Each special value appears 3 times, so all three share the mode.
     */
    @Test
    public void testModeDoubleNan() {
        Frequency<Double> freq = new Frequency<>();

        freq.addValue(Double.valueOf(Double.NaN));
        freq.addValue(Double.valueOf(Double.NaN));
        freq.addValue(Double.valueOf(Double.NaN));
        freq.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.POSITIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.NEGATIVE_INFINITY));
        freq.addValue(Double.valueOf(Double.POSITIVE_INFINITY));

        List<Double> mode = freq.getMode();
        Assert.assertEquals("mode size should be 3 (all tied at count 3)",        3, mode.size());
        Assert.assertEquals("mode[0] should be NEGATIVE_INFINITY", Double.valueOf(Double.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals("mode[1] should be POSITIVE_INFINITY", Double.valueOf(Double.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals("mode[2] should be NaN",               Double.valueOf(Double.NaN),               mode.get(2));
    }

    /**
     * Verifies that {@code getMode()} handles Float special values
     * (NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY) correctly.
     * Each special value appears 3 times, so all three share the mode.
     */
    @Test
    public void testModeFloatNan() {
        Frequency<Float> freq = new Frequency<>();

        freq.addValue(Float.valueOf(Float.NaN));
        freq.addValue(Float.valueOf(Float.NaN));
        freq.addValue(Float.valueOf(Float.NaN));
        freq.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.POSITIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.NEGATIVE_INFINITY));
        freq.addValue(Float.valueOf(Float.POSITIVE_INFINITY));

        List<Float> mode = freq.getMode();
        Assert.assertEquals("mode size should be 3 (all tied at count 3)",       3, mode.size());
        Assert.assertEquals("mode[0] should be NEGATIVE_INFINITY", Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals("mode[1] should be POSITIVE_INFINITY", Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals("mode[2] should be NaN",               Float.valueOf(Float.NaN),               mode.get(2));
    }

    /**
     * Verifies that two Frequency tables with different comparators but the
     * same values are considered equal (MATH-1689), while their cumulative
     * frequencies and mode orderings differ due to the comparator.
     */
    @Test
    public void testEqualsIgnoresComparator() {
        Frequency<Integer> ascending  = new Frequency<>();
        Frequency<Integer> descending = new Frequency<Integer>(Comparator.reverseOrder());
        ascending.addValue(1);
        ascending.addValue(2);
        descending.addValue(1);
        descending.addValue(2);

        // cumFreq is comparator-dependent: ascending sees [1] then [1,2]; descending sees [2] then [1,2]
        Assert.assertEquals("ascending cumFreq(1) should be 1",  1, ascending.getCumFreq(1));
        Assert.assertEquals("descending cumFreq(1) should be 2", 2, descending.getCumFreq(1));

        Assert.assertEquals("ascending mode order should be [1, 2]",  "[1, 2]", ascending.getMode().toString());
        Assert.assertEquals("descending mode order should be [2, 1]", "[2, 1]", descending.getMode().toString());

        // equals() ignores the comparator — tables with identical value→count mappings are equal
        Assert.assertEquals("tables with same counts should be equal regardless of comparator",
                            ascending, descending);
    }
}
