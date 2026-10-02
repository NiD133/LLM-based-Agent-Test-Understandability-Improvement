package org.apache.commons.math4.legacy.stat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testMergeCollection {

    private static final long ONE_LONG = 1L;
    private static final long TWO_LONG = 2L;
    private static final long THREE_LONG = 3L;

    @Test
    public void testMergeCollection() {
        Frequency<Long> mergedFrequency = new Frequency<>();

        Assert.assertEquals(0, mergedFrequency.getUniqueCount());

        mergedFrequency.addValue(ONE_LONG);

        Assert.assertEquals(1, mergedFrequency.getUniqueCount());
        Assert.assertEquals(1, mergedFrequency.getCount(ONE_LONG));
        Assert.assertEquals(0, mergedFrequency.getCount(TWO_LONG));

        Frequency<Long> frequencyWithTwo = new Frequency<Long>();
        frequencyWithTwo.addValue(TWO_LONG);

        Frequency<Long> frequencyWithThree = new Frequency<Long>();
        frequencyWithThree.addValue(THREE_LONG);

        List<Frequency<Long>> frequenciesToMerge = new ArrayList<>();
        frequenciesToMerge.add(frequencyWithTwo);
        frequenciesToMerge.add(frequencyWithThree);

        mergedFrequency.merge(frequenciesToMerge);

        Assert.assertEquals(3, mergedFrequency.getUniqueCount());
        Assert.assertEquals(1, mergedFrequency.getCount(ONE_LONG));
        Assert.assertEquals(1, mergedFrequency.getCount(TWO_LONG));
        Assert.assertEquals(1, mergedFrequency.getCount(THREE_LONG));
    }
}
