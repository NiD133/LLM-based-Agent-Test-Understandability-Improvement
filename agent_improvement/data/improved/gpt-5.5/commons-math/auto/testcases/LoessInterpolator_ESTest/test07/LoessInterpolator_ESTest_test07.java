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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();
        double[] emptyData = new double[0];

        try {
            interpolator.smooth(emptyData, emptyData, emptyData);
            fail("Expected RuntimeException for empty data");
        } catch (RuntimeException e) {
            //
            // no data
            //
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
