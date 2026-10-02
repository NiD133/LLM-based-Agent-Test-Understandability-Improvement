package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test02 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that calling clear() on a SummaryStatistics instance whose statistical
     * implementations (sum, max, variance, min, mean) have all been replaced with a
     * shared StorelessGeometricMean resets the observation count to zero.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        SummaryStatistics stats = new SummaryStatistics();

        // Replace all five statistical sub-implementations with a single shared
        // StorelessGeometricMean instance, exercising the custom-impl injection path.
        Statistics.StorelessGeometricMean geometricMean = Statistics.StorelessGeometricMean.create();
        stats.setSumImpl(geometricMean);
        stats.setMaxImpl(geometricMean);
        stats.setVarianceImpl(geometricMean);
        stats.setMinImpl(geometricMean);
        stats.setMeanImpl(geometricMean);

        // After clear(), the count of accumulated observations must be zero
        // regardless of which implementations are registered.
        stats.clear();
        assertEquals(0L, stats.getN());
    }
}
