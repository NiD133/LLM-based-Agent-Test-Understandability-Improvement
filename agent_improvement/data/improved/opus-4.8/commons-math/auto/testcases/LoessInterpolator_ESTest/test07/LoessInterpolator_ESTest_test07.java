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
     * Smoothing an empty data set must fail: there are no points to interpolate,
     * so {@code smooth} is expected to throw a RuntimeException ("no data").
     */
    @Test(timeout = 4000)
    public void smoothWithEmptyDataThrowsNoDataException() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();
        double[] emptyData = new double[0];

        try {
            interpolator.smooth(emptyData, emptyData, emptyData);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Thrown by LoessInterpolator because there is no data to smooth.
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
