package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testModeDoubleNan {

    /**
     * Verifies {@link Frequency#getMode()} when the observations include the
     * special double values NaN, negative infinity and positive infinity.
     *
     * <p>Each of the three values is added the same number of times (three),
     * so all three tie for the highest frequency and must be returned as the
     * mode. The returned list is expected to be sorted in ascending order,
     * with NaN ranked last.</p>
     */
    @Test
    public void testModeDoubleNan() {
        Frequency<Double> frequency = new Frequency<>();

        // Add each special value three times so they all share the top frequency.
        frequency.addValue(Double.NaN);
        frequency.addValue(Double.NaN);
        frequency.addValue(Double.NaN);
        frequency.addValue(Double.NEGATIVE_INFINITY);
        frequency.addValue(Double.NEGATIVE_INFINITY);
        frequency.addValue(Double.NEGATIVE_INFINITY);
        frequency.addValue(Double.POSITIVE_INFINITY);
        frequency.addValue(Double.POSITIVE_INFINITY);
        frequency.addValue(Double.POSITIVE_INFINITY);

        List<Double> mode = frequency.getMode();

        // All three values tie, so the mode contains exactly three entries,
        // ordered ascending with NaN last.
        Assert.assertEquals(3, mode.size());
        Assert.assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals(Double.valueOf(Double.NaN), mode.get(2));
    }
}
