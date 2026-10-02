package org.apache.commons.math4.legacy.stat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testMergeCollection {

    private static final long VALUE_ONE = 1L;

    private static final long VALUE_TWO = 2L;

    private static final long VALUE_THREE = 3L;

    /**
     * Merging a collection of single-value frequencies into a target frequency
     * should add every distinct value, leaving each value with a count of one.
     */
    @Test
    public void testMergeCollection() {
        // A target frequency that already holds the value 1.
        Frequency<Long> target = new Frequency<>();
        Assert.assertEquals(0, target.getUniqueCount());
        target.addValue(VALUE_ONE);
        Assert.assertEquals(1, target.getUniqueCount());
        Assert.assertEquals(1, target.getCount(VALUE_ONE));
        Assert.assertEquals(0, target.getCount(VALUE_TWO));

        // Two other frequencies, each holding a single distinct value.
        Frequency<Long> frequencyOfTwo = new Frequency<>();
        frequencyOfTwo.addValue(VALUE_TWO);
        Frequency<Long> frequencyOfThree = new Frequency<>();
        frequencyOfThree.addValue(VALUE_THREE);

        List<Frequency<Long>> others = new ArrayList<>();
        others.add(frequencyOfTwo);
        others.add(frequencyOfThree);

        target.merge(others);

        // The target now contains all three values, each counted once.
        Assert.assertEquals(3, target.getUniqueCount());
        Assert.assertEquals(1, target.getCount(VALUE_ONE));
        Assert.assertEquals(1, target.getCount(VALUE_TWO));
        Assert.assertEquals(1, target.getCount(VALUE_THREE));
    }
}
