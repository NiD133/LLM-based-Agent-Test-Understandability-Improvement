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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final double firstSample = 2536.6;
        final double secondSample = -4145.465349642632;
        final long expectedSampleCount = 2L;
        final double expectedStandardDeviation = 4724.933721063963;
        final double assertionTolerance = 0.01;

        SummaryStatistics statistics = new SummaryStatistics();
        statistics.addValue(firstSample);
        statistics.addValue(secondSample);

        double standardDeviation = statistics.getStandardDeviation();

        assertEquals(expectedSampleCount, statistics.getN());
        assertEquals(expectedStandardDeviation, standardDeviation, assertionTolerance);
    }
}
