package org.apache.commons.math4.legacy.stat.descriptive;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SummaryStatistics_ESTest_test00 extends SummaryStatistics_ESTest_scaffolding {

    /**
     * The variance implementation may only be swapped out while the
     * statistics object is still empty. Once at least one value has been
     * added, {@link SummaryStatistics#setVarianceImpl} must reject the change
     * by throwing a RuntimeException.
     */
    @Test(timeout = 4000)
    public void setVarianceImplAfterAddingValueThrows() throws Throwable {
        SummaryStatistics summaryStatistics = new SummaryStatistics();
        Statistics.StorelessMax replacementVarianceImpl = Statistics.StorelessMax.create();

        // Adding a value "locks" the statistic configuration.
        summaryStatistics.addValue(0.0);

        try {
            summaryStatistics.setVarianceImpl(replacementVarianceImpl);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Message: "1 values have been added before statistic is configured"
            verifyException("org.apache.commons.math4.legacy.stat.descriptive.SummaryStatistics", e);
        }
    }
}
