package org.apache.commons.math4.legacy.stat;

import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link Frequency#getCount}, {@link Frequency#getSumFreq},
 * {@link Frequency#getCumFreq}, {@link Frequency#getPct}, and
 * {@link Frequency#getCumPct} across Long, Integer, String, and Character types.
 */
public class FrequencyTest_testCounts {

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testCounts() {
        verifyLongFrequencyCounts();
        verifyStringFrequencyCounts_caseSensitive();
        verifyIntegerFrequencyCounts();
        verifyStringFrequencyCounts_caseInsensitive();
        verifyCharacterFrequencyCounts();
    }

    /** Verifies count, cumulative freq, and clear behaviour for Long values. */
    private void verifyLongFrequencyCounts() {
        Frequency<Long> freq = new Frequency<>();

        Assert.assertEquals("empty frequency table should have sum 0", 0, freq.getSumFreq());

        // Add: 1L appears 3 times (via constant and literal), 2L appears once
        freq.addValue(1L);
        freq.addValue(2L);
        freq.addValue(1L);
        freq.addValue(1L);

        Assert.assertEquals("count of 1L should be 3", 3, freq.getCount(1L));
        Assert.assertEquals("count of 2L should be 1", 1, freq.getCount(2L));
        Assert.assertEquals("count of 3L (not added) should be 0", 0, freq.getCount(3L));
        Assert.assertEquals("total observations should be 4", 4, freq.getSumFreq());

        // Cumulative frequencies: values sorted ascending as 1L, 2L
        Assert.assertEquals("cumFreq below minimum (0L) should be 0", 0, freq.getCumFreq(0L));
        Assert.assertEquals("cumFreq at 1L should be 3",              3, freq.getCumFreq(1L));
        Assert.assertEquals("cumFreq at 2L should be 4",              4, freq.getCumFreq(2L));
        Assert.assertEquals("cumFreq(Long.valueOf(2)) should equal cumFreq(2L)", 4, freq.getCumFreq(Long.valueOf(2)));
        Assert.assertEquals("cumFreq above maximum (5L) should be 4", 4, freq.getCumFreq(5L));
        Assert.assertEquals("cumFreq below minimum (-1L) should be 0", 0, freq.getCumFreq(-1L));

        freq.clear();
        Assert.assertEquals("sum should be 0 after clear", 0, freq.getSumFreq());
    }

    /**
     * Verifies case-sensitive String frequency: each variant ("one", "One", "oNe")
     * is treated as a distinct value.
     */
    private void verifyStringFrequencyCounts_caseSensitive() {
        Frequency<String> freq = new Frequency<>();
        freq.addValue("one");
        freq.addValue("One");
        freq.addValue("oNe");
        freq.addValue("Z");

        // Only exact "one" matches; "One" and "oNe" are different keys
        Assert.assertEquals("count of exact \"one\" should be 1", 1, freq.getCount("one"));

        // Sorted order (natural): "One" < "Z" < "oNe" < "one"  (uppercase letters before lower)
        Assert.assertEquals("cumPct at \"Z\" should be 0.5",  0.5,  freq.getCumPct("Z"),  TOLERANCE);
        Assert.assertEquals("cumPct at \"z\" (past all) should be 1.0", 1.0, freq.getCumPct("z"), TOLERANCE);
        Assert.assertEquals("cumPct at \"Ot\" (between \"One\" and \"Z\") should be 0.25", 0.25, freq.getCumPct("Ot"), TOLERANCE);
    }

    /** Verifies count, cumulative percentage, and percentage for Integer values. */
    private void verifyIntegerFrequencyCounts() {
        Frequency<Integer> freq = new Frequency<>();
        // Add: 1 appears 3 times, 2 appears once, -1 appears once  → 5 total
        freq.addValue(1);
        freq.addValue(Integer.valueOf(1));
        freq.addValue(1);   // constant ONE == 1
        freq.addValue(2);
        freq.addValue(Integer.valueOf(-1));

        Assert.assertEquals("count of 1 should be 3",                    3, freq.getCount(1));
        Assert.assertEquals("count of Integer.valueOf(1) should be 3",   3, freq.getCount(Integer.valueOf(1)));

        // Sorted order: -1, 1, 2  → cumulative counts: 1, 4, 5
        Assert.assertEquals("cumPct at 0 (between -1 and 1) should be 0.2",   0.2, freq.getCumPct(0),               TOLERANCE);
        Assert.assertEquals("pct of 1 should be 0.6 (3/5)",                   0.6, freq.getPct(Integer.valueOf(1)), TOLERANCE);
        Assert.assertEquals("cumPct at -2 (below minimum) should be 0",       0,   freq.getCumPct(-2),              TOLERANCE);
        Assert.assertEquals("cumPct at 10 (above maximum) should be 1",       1,   freq.getCumPct(10),              TOLERANCE);
    }

    /**
     * Verifies case-insensitive String frequency: "one", "One", and "oNe" all
     * map to the same bucket when using {@link String#CASE_INSENSITIVE_ORDER}.
     */
    private void verifyStringFrequencyCounts_caseInsensitive() {
        Frequency<String> freq = new Frequency<>(String.CASE_INSENSITIVE_ORDER);
        freq.addValue("one");
        freq.addValue("One");
        freq.addValue("oNe");
        freq.addValue("Z");

        Assert.assertEquals("case-insensitive count of \"one\" variants should be 3", 3, freq.getCount("one"));
        // Two distinct buckets: "one" (3 obs) and "Z" (1 obs); "Z" is the last → cumPct = 1
        Assert.assertEquals("cumPct at \"Z\" (case-insensitive) should be 1", 1, freq.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("cumPct at \"z\" (case-insensitive) should be 1", 1, freq.getCumPct("z"), TOLERANCE);
    }

    /**
     * Verifies Character frequency: checks NaN behaviour on an empty table,
     * then count and cumulative percentage after adding 'a','b','c','d'.
     */
    private void verifyCharacterFrequencyCounts() {
        Frequency<Character> freq = new Frequency<>();

        // Empty table: counts are 0; percentages are NaN (undefined with no observations)
        Assert.assertEquals("count of 'a' in empty table should be 0",       0L,         freq.getCount('a'));
        Assert.assertEquals("cumFreq of 'b' in empty table should be 0",     0L,         freq.getCumFreq('b'));
        TestUtils.assertEquals("pct of 'a' in empty table should be NaN",    Double.NaN, freq.getPct('a'),    0.0);
        TestUtils.assertEquals("cumPct of 'b' in empty table should be NaN", Double.NaN, freq.getCumPct('b'), 0.0);

        // Add one observation each of 'a', 'b', 'c', 'd'  → 4 total, sorted a < b < c < d
        freq.addValue('a');
        freq.addValue('b');
        freq.addValue('c');
        freq.addValue('d');

        Assert.assertEquals("count of 'a' should be 1",         1L,   freq.getCount('a'));
        Assert.assertEquals("cumFreq at 'b' should be 2",       2L,   freq.getCumFreq('b'));
        Assert.assertEquals("pct of 'a' should be 0.25 (1/4)",  0.25, freq.getPct('a'),    0.0);
        Assert.assertEquals("cumPct at 'b' should be 0.5",      0.5,  freq.getCumPct('b'), 0.0);
        Assert.assertEquals("cumPct at 'e' (past all) should be 1.0", 1.0, freq.getCumPct('e'), 0.0);
    }
}
