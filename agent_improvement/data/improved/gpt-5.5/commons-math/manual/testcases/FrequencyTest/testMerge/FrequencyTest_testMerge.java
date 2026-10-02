package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testMerge {

    private static final long ONE_LONG = 1L;
    private static final long TWO_LONG = 2L;
    private static final long THREE_LONG = 3L;

    @Test
    public void testMerge() {
        Frequency<Long> frequencyToMergeInto = createFrequencyContainingOnesAndTwos();
        Frequency<Long> frequencyToMerge = createFrequencyContainingOnesAndThrees();

        frequencyToMergeInto.merge(frequencyToMerge);

        Assert.assertEquals(3, frequencyToMergeInto.getUniqueCount());
        Assert.assertEquals(3, frequencyToMergeInto.getCount(ONE_LONG));
        Assert.assertEquals(2, frequencyToMergeInto.getCount(TWO_LONG));
        Assert.assertEquals(2, frequencyToMergeInto.getCount(THREE_LONG));
    }

    private Frequency<Long> createFrequencyContainingOnesAndTwos() {
        Frequency<Long> frequency = new Frequency<>();
        Assert.assertEquals(0, frequency.getUniqueCount());

        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);
        frequency.addValue(ONE_LONG);
        frequency.addValue(TWO_LONG);

        Assert.assertEquals(2, frequency.getUniqueCount());
        Assert.assertEquals(2, frequency.getCount(ONE_LONG));
        Assert.assertEquals(2, frequency.getCount(TWO_LONG));
        return frequency;
    }

    private Frequency<Long> createFrequencyContainingOnesAndThrees() {
        Frequency<Long> frequency = new Frequency<>();

        frequency.addValue(ONE_LONG);
        frequency.addValue(THREE_LONG);
        frequency.addValue(THREE_LONG);

        Assert.assertEquals(2, frequency.getUniqueCount());
        Assert.assertEquals(1, frequency.getCount(ONE_LONG));
        Assert.assertEquals(2, frequency.getCount(THREE_LONG));
        return frequency;
    }
}
