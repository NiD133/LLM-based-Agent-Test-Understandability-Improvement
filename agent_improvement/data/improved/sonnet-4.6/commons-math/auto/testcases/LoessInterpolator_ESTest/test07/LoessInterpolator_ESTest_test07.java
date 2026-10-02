package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test07 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Verifies that smooth() throws a RuntimeException when all input arrays are empty,
     * because LOESS interpolation requires at least one data point.
     */
    @Test(timeout = 4000)
    public void test07_smoothWithEmptyData_throwsRuntimeException() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();
        double[] emptyData = new double[0];

        try {
            interpolator.smooth(emptyData, emptyData, emptyData);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
