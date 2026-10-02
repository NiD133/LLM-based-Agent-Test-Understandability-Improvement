package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies {@link Frequency#getMode()} with special {@code Float} values
 * (NaN and the infinities).
 */
public class FrequencyTest_testModeFloatNan {

    /**
     * Each of the three special float values is added the same number of times,
     * so they all tie for the highest frequency and are all returned as modes.
     * The mode list is expected to be sorted ascending, which for floats places
     * NEGATIVE_INFINITY first, POSITIVE_INFINITY next and NaN last.
     */
    @Test
    public void testModeFloatNan() {
        final int occurrencesPerValue = 3;

        Frequency<Float> frequency = new Frequency<>();
        for (int i = 0; i < occurrencesPerValue; i++) {
            frequency.addValue(Float.NaN);
            frequency.addValue(Float.NEGATIVE_INFINITY);
            frequency.addValue(Float.POSITIVE_INFINITY);
        }

        List<Float> mode = frequency.getMode();

        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Float.valueOf(Float.NaN), mode.get(2));
    }
}
