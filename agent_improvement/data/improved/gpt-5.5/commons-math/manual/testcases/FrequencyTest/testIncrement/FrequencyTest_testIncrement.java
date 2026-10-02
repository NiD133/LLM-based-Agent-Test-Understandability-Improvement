package org.apache.commons.math4.legacy.stat;

import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testIncrement {

    private static final Long VALUE = 1L;
    private static final long INITIAL_COUNT = 0L;
    private static final long FIRST_INCREMENT = 1L;
    private static final long SECOND_INCREMENT = 4L;
    private static final long NEGATING_INCREMENT = -5L;

    @Test
    public void testIncrement() {
        Frequency<Long> frequency = new Frequency<>();

        Assert.assertEquals(0, frequency.getUniqueCount());

        frequency.incrementValue(VALUE, FIRST_INCREMENT);
        Assert.assertEquals(1, frequency.getCount(VALUE));

        frequency.incrementValue(VALUE, SECOND_INCREMENT);
        Assert.assertEquals(5, frequency.getCount(VALUE));

        frequency.incrementValue(VALUE, NEGATING_INCREMENT);
        Assert.assertEquals(INITIAL_COUNT, frequency.getCount(VALUE));
    }
}
