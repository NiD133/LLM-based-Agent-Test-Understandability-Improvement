package org.apache.commons.math4.legacy.stat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testMergeCollection {

    @Test
    public void testMergeCollection() {
        // Arrange: create a primary frequency table with one entry (value=1)
        Frequency<Long> primary = new Frequency<>();
        primary.addValue(1L);

        // Verify initial state before merge
        Assert.assertEquals(0, new Frequency<Long>().getUniqueCount());
        Assert.assertEquals(1, primary.getUniqueCount());
        Assert.assertEquals(1, primary.getCount(1L));
        Assert.assertEquals(0, primary.getCount(2L));

        // Arrange: create two additional frequency tables to merge in
        Frequency<Long> withTwo = new Frequency<Long>();
        withTwo.addValue(2L);

        Frequency<Long> withThree = new Frequency<Long>();
        withThree.addValue(3L);

        List<Frequency<Long>> toMerge = new ArrayList<>();
        toMerge.add(withTwo);
        toMerge.add(withThree);

        // Act: merge the collection into the primary frequency table
        primary.merge(toMerge);

        // Assert: primary now contains entries for all three distinct values
        Assert.assertEquals(3, primary.getUniqueCount());
        Assert.assertEquals(1, primary.getCount(1L));
        Assert.assertEquals(1, primary.getCount(2L));
        Assert.assertEquals(1, primary.getCount(3L));
    }
}
