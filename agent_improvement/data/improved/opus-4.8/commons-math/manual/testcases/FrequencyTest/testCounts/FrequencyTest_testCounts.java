package org.apache.commons.math4.legacy.stat;

import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testCounts {

    /** Tolerance used when comparing percentage (double) results. */
    private static final double TOLERANCE = 10E-15d;

    /**
     * Exercises the frequency-counting API end to end: raw counts, cumulative
     * counts and percentages, the effect of a custom comparator, and the
     * behaviour of an empty distribution.
     */
    @Test
    public void testCounts() {
        verifyLongCountsAndCumulativeFrequencies();
        verifyStringCountsAndCumulativePercentages();
        verifyIntegerCountsAndPercentages();
        verifyCaseInsensitiveStringComparator();
        verifyCharacterCountsIncludingEmptyDistribution();
    }

    /**
     * Counts of {@code Long} values, including cumulative frequencies for
     * present values, an absent value, and values below/above the observed
     * range. Also checks that clearing resets the total count.
     */
    private void verifyLongCountsAndCumulativeFrequencies() {
        Frequency<Long> fLong = new Frequency<>();
        Assert.assertEquals("total count", 0, fLong.getSumFreq());

        // Add the value 1 three times (using equivalent literals) and 2 once.
        fLong.addValue(1L);
        fLong.addValue(2L);
        fLong.addValue(1L);
        fLong.addValue(1L);

        Assert.assertEquals("one frequency count", 3, fLong.getCount(1L));
        Assert.assertEquals("two frequency count", 1, fLong.getCount(2L));
        Assert.assertEquals("three frequency count", 0, fLong.getCount(3L));
        Assert.assertEquals("total count", 4, fLong.getSumFreq());

        Assert.assertEquals("zero cumulative frequency", 0, fLong.getCumFreq(0L));
        Assert.assertEquals("one cumulative frequency", 3, fLong.getCumFreq(1L));
        Assert.assertEquals("two cumulative frequency", 4, fLong.getCumFreq(2L));
        Assert.assertEquals("Integer argument cum freq", 4, fLong.getCumFreq(Long.valueOf(2)));
        Assert.assertEquals("five cumulative frequency", 4, fLong.getCumFreq(5L));
        Assert.assertEquals("foo cumulative frequency", 0, fLong.getCumFreq(-1L));

        fLong.clear();
        Assert.assertEquals("total count", 0, fLong.getSumFreq());
    }

    /**
     * User-guide example: case-sensitive {@code String} counting. The four
     * distinct values "one", "One", "oNe" and "Z" each occur once, so
     * cumulative percentages depend on natural (case-sensitive) ordering.
     */
    private void verifyStringCountsAndCumulativePercentages() {
        Frequency<String> fString = new Frequency<>();
        fString.addValue("one");
        fString.addValue("One");
        fString.addValue("oNe");
        fString.addValue("Z");

        Assert.assertEquals("one cumulative frequency", 1, fString.getCount("one"));
        Assert.assertEquals("Z cumulative pct", 0.5, fString.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("z cumulative pct", 1.0, fString.getCumPct("z"), TOLERANCE);
        Assert.assertEquals("Ot cumulative pct", 0.25, fString.getCumPct("Ot"), TOLERANCE);
    }

    /**
     * User-guide example: {@code Integer} counting and percentages. The value 1
     * is added three times, plus 2 and -1 once each (five values total).
     */
    private void verifyIntegerCountsAndPercentages() {
        Frequency<Integer> fInteger = new Frequency<>();
        fInteger.addValue(1);
        fInteger.addValue(Integer.valueOf(1));
        fInteger.addValue(1);
        fInteger.addValue(2);
        fInteger.addValue(Integer.valueOf(-1));

        Assert.assertEquals("1 count", 3, fInteger.getCount(1));
        Assert.assertEquals("1 count", 3, fInteger.getCount(Integer.valueOf(1)));
        Assert.assertEquals("0 cum pct", 0.2, fInteger.getCumPct(0), TOLERANCE);
        Assert.assertEquals("1 pct", 0.6, fInteger.getPct(Integer.valueOf(1)), TOLERANCE);
        Assert.assertEquals("-2 cum pct", 0, fInteger.getCumPct(-2), TOLERANCE);
        Assert.assertEquals("10 cum pct", 1, fInteger.getCumPct(10), TOLERANCE);
    }

    /**
     * Same four strings as the case-sensitive example, but with a
     * case-insensitive comparator so that "one", "One" and "oNe" collapse into
     * a single value with count 3.
     */
    private void verifyCaseInsensitiveStringComparator() {
        Frequency<String> fString = new Frequency<>(String.CASE_INSENSITIVE_ORDER);
        fString.addValue("one");
        fString.addValue("One");
        fString.addValue("oNe");
        fString.addValue("Z");

        Assert.assertEquals("one count", 3, fString.getCount("one"));
        Assert.assertEquals("Z cumulative pct -- case insensitive", 1, fString.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("z cumulative pct -- case insensitive", 1, fString.getCumPct("z"), TOLERANCE);
    }

    /**
     * {@code Character} counting. First checks the behaviour of an empty
     * distribution (zero counts and {@code NaN} percentages), then adds four
     * distinct characters and verifies counts and percentages.
     */
    private void verifyCharacterCountsIncludingEmptyDistribution() {
        Frequency<Character> fChar = new Frequency<>();

        // Empty distribution: no counts, percentages are undefined (NaN).
        Assert.assertEquals(0L, fChar.getCount('a'));
        Assert.assertEquals(0L, fChar.getCumFreq('b'));
        TestUtils.assertEquals(Double.NaN, fChar.getPct('a'), 0.0);
        TestUtils.assertEquals(Double.NaN, fChar.getCumPct('b'), 0.0);

        fChar.addValue('a');
        fChar.addValue('b');
        fChar.addValue('c');
        fChar.addValue('d');

        Assert.assertEquals(1L, fChar.getCount('a'));
        Assert.assertEquals(2L, fChar.getCumFreq('b'));
        Assert.assertEquals(0.25, fChar.getPct('a'), 0.0);
        Assert.assertEquals(0.5, fChar.getCumPct('b'), 0.0);
        Assert.assertEquals(1.0, fChar.getCumPct('e'), 0.0);
    }
}
