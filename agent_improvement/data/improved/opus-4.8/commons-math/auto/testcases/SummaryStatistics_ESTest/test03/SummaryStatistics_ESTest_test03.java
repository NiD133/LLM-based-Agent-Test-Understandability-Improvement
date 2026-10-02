package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test03 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * Verifies that the sample standard deviation is computed correctly after
     * two values have been added to the statistics accumulator.
     */
    @Test(timeout = 4000)
    public void standardDeviationOfTwoValuesIsComputedCorrectly() throws Throwable {
        SummaryStatistics statistics = new SummaryStatistics();
        statistics.addValue(2536.6);
        statistics.addValue(-4145.465349642632);

        double standardDeviation = statistics.getStandardDeviation();

        long expectedSampleCount = 2L;
        double expectedStandardDeviation = 4724.933721063963;
        double tolerance = 0.01;
        assertEquals(expectedSampleCount, statistics.getN());
        assertEquals(expectedStandardDeviation, standardDeviation, tolerance);
    }
}
