/*
 * Improved for understandability — same runtime behaviour as the EvoSuite original.
 */

package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math4.legacy.stat.Frequency;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest extends Frequency_ESTest_scaffolding {

    // -------------------------------------------------------------------------
    // equals / hashCode
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void test00_twoEmptyFrequenciesAreEqual() throws Throwable {
        Frequency<Integer> freq1 = new Frequency<Integer>();
        Frequency<Integer> freq2 = new Frequency<Integer>();
        assertTrue(freq1.equals(freq2));
    }

    @Test(timeout = 4000)
    public void test01_frequencyIsEqualToItself() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        assertTrue(freq.equals(freq));
    }

    @Test(timeout = 4000)
    public void test02_frequencyIsNotEqualToArbitraryObject() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        Object other = new Object();
        assertFalse(freq.equals(other));
    }

    @Test(timeout = 4000)
    public void test03_hashCodeDoesNotThrow() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        freq.hashCode();
    }

    // -------------------------------------------------------------------------
    // merge
    // -------------------------------------------------------------------------

    /** Merging a collection that contains the frequency itself should succeed. */
    @Test(timeout = 4000)
    public void test04_mergeWithCollectionContainingSelf() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        freq.incrementValue(Integer.valueOf(0), 0L);

        LinkedList<Frequency<Integer>> collection = new LinkedList<Frequency<Integer>>();
        collection.add(freq);
        freq.merge((Collection<Frequency<Integer>>) collection);

        assertEquals(1, collection.size());
    }

    // -------------------------------------------------------------------------
    // getMode
    // -------------------------------------------------------------------------

    /** The value with the highest count should be returned as the mode. */
    @Test(timeout = 4000)
    public void test05_getModeReturnsValueWithHighestCount() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        Integer valueWithNoCount = Integer.valueOf(166);
        Integer valueWithCount   = Integer.valueOf(0);
        freq.incrementValue(valueWithNoCount, 0L);
        freq.incrementValue(valueWithCount,   1L);

        List<Integer> mode = freq.getMode();

        assertTrue(mode.contains(0));
        assertEquals(1, mode.size());
    }

    // -------------------------------------------------------------------------
    // getCumPct
    // -------------------------------------------------------------------------

    /** Cumulative percentage on an empty table should be NaN. */
    @Test(timeout = 4000)
    public void test06_getCumPctOnEmptyFrequencyReturnsNaN() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        double cumPct = freq.getCumPct(Integer.valueOf(0));
        assertEquals(Double.NaN, cumPct, 0.01);
    }

    // -------------------------------------------------------------------------
    // getCumFreq
    // -------------------------------------------------------------------------

    /**
     * When two values (one very negative, one positive) are added and we query the
     * cumulative frequency for a value between them (0), it should be 0 because
     * no entries at or below 0 have positive counts.
     */
    @Test(timeout = 4000)
    public void test07_getCumFreqForValueBetweenEntriesWithNoPositiveCount() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        Integer veryNegative = Integer.valueOf(-2146457125);
        Integer positive     = Integer.valueOf(221);
        freq.incrementValue(veryNegative, 0L);
        freq.incrementValue(positive,    -1);   // negative delta keeps count at 0

        long cumFreq = freq.getCumFreq(Integer.valueOf(0));
        assertEquals(0L, cumFreq);
    }

    /**
     * Cumulative frequency for the smallest value whose count is zero should be 0.
     */
    @Test(timeout = 4000)
    public void test08_getCumFreqForSmallestValueWithZeroCount() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        Integer veryNegative = Integer.valueOf(-2146457125);
        freq.incrementValue(veryNegative, 0L);
        freq.incrementValue(Integer.valueOf(0), -1);

        long cumFreq = freq.getCumFreq(veryNegative);
        assertEquals(0L, cumFreq);
    }

    /**
     * Querying cumulative frequency for a value (obtained via Integer.getInteger)
     * that comes before the single added value should return 0.
     */
    @Test(timeout = 4000)
    public void test09_getCumFreqForKeyBeforeAddedValue() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        freq.addValue(Integer.valueOf(1587));
        // Integer.getInteger looks up system property; falls back to default 123
        Integer queriedKey = Integer.getInteger("Value \t Freq. \t Pct. \t Cum Pct. \n", 123);

        long cumFreq = freq.getCumFreq(queriedKey);
        assertEquals(0L, cumFreq);
    }

    /**
     * Cumulative frequency for a value greater than the only entry should equal
     * the total count (1).
     */
    @Test(timeout = 4000)
    public void test10_getCumFreqForValueBeyondAllEntriesEqualsTotal() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        freq.addValue(Integer.valueOf(1587));

        long cumFreq = freq.getCumFreq(Integer.valueOf(1905));
        assertEquals(1L, cumFreq);
    }

    /** getCumFreq with a null argument should return 0 without throwing. */
    @Test(timeout = 4000)
    public void test12_getCumFreqWithNullReturnsZero() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        long cumFreq = freq.getCumFreq((Integer) null);
        assertEquals(0L, cumFreq);
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    /**
     * toString should produce a tab-separated table with the correct header and
     * one data row when a custom comparator is used.
     */
    @Test(timeout = 4000)
    public void test11_toStringWithCustomComparatorFormatsTableCorrectly() throws Throwable {
        Comparator<Integer> comparator = (Comparator<Integer>) mock(Comparator.class,
                new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(comparator).compare(anyInt(), anyInt());

        Frequency<Integer> freq = new Frequency<Integer>(comparator);
        freq.addValue(Integer.valueOf(46));

        String result = freq.toString();
        assertEquals("Value \t Freq. \t Pct. \t Cum Pct. \n46\t1\t100%\t100%\n", result);
    }

    // -------------------------------------------------------------------------
    // getPct
    // -------------------------------------------------------------------------

    /** Percentage on an empty table should be NaN. */
    @Test(timeout = 4000)
    public void test13_getPctOnEmptyFrequencyReturnsNaN() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        double pct = freq.getPct(Integer.valueOf(0));
        assertEquals(Double.NaN, pct, 0.01);
    }

    // -------------------------------------------------------------------------
    // getCount
    // -------------------------------------------------------------------------

    /** Count for a value not yet added should be 0. */
    @Test(timeout = 4000)
    public void test14_getCountForAbsentValueReturnsZero() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        long count = freq.getCount(Integer.valueOf(0));
        assertEquals(0L, count);
    }

    // -------------------------------------------------------------------------
    // clear
    // -------------------------------------------------------------------------

    /** clear() on an empty frequency should not throw. */
    @Test(timeout = 4000)
    public void test15_clearOnEmptyFrequencyDoesNotThrow() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        freq.clear();
    }

    // -------------------------------------------------------------------------
    // getUniqueCount
    // -------------------------------------------------------------------------

    /** A newly created frequency should have zero unique values. */
    @Test(timeout = 4000)
    public void test16_getUniqueCountOnEmptyFrequencyReturnsZero() throws Throwable {
        Frequency<Integer> freq = new Frequency<Integer>();
        int uniqueCount = freq.getUniqueCount();
        assertEquals(0, uniqueCount);
    }
}
