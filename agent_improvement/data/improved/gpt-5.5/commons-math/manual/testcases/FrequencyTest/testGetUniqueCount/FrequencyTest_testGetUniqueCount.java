package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testGetUniqueCount {

    private static final long FIRST_VALUE = 1L;
    private static final long SECOND_VALUE = 2L;

    @Test
    public void testGetUniqueCount() {
        Frequency<Long> frequency = new Frequency<>();

        Assert.assertEquals(0, frequency.getUniqueCount());

        frequency.addValue(FIRST_VALUE);
        Assert.assertEquals(1, frequency.getUniqueCount());

        frequency.addValue(FIRST_VALUE);
        Assert.assertEquals(1, frequency.getUniqueCount());

        frequency.addValue(SECOND_VALUE);
        Assert.assertEquals(2, frequency.getUniqueCount());
    }
}
