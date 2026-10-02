package org.apache.commons.math4.legacy.stat;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testEqualsIgnoresComparator {

    /**
     * Frequency equality is based on counted values, not on the comparator
     * used to order cumulative frequencies and modes. See MATH-1689.
     */
    @Test
    public void testEqualsIgnoresComparator() {
        Frequency<Integer> naturalOrderFrequency = new Frequency<>();
        Frequency<Integer> reverseOrderFrequency = new Frequency<Integer>(Comparator.reverseOrder());

        naturalOrderFrequency.addValue(1);
        naturalOrderFrequency.addValue(2);
        reverseOrderFrequency.addValue(1);
        reverseOrderFrequency.addValue(2);

        Assert.assertEquals(1, naturalOrderFrequency.getCumFreq(1));
        Assert.assertEquals(2, reverseOrderFrequency.getCumFreq(1));
        Assert.assertEquals("[1, 2]", naturalOrderFrequency.getMode().toString());
        Assert.assertEquals("[2, 1]", reverseOrderFrequency.getMode().toString());

        Assert.assertEquals(naturalOrderFrequency, reverseOrderFrequency);
    }
}
