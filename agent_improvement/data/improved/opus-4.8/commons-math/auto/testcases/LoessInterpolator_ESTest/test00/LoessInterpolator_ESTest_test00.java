package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test00 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * smooth() requires the x (abscissa) and y (ordinate) arrays to have the
     * same length. Here the x array has 3 elements while the y array has 6, so
     * the interpolator must reject the call with a dimension-mismatch exception
     * reporting "3 != 6".
     */
    @Test(timeout = 4000)
    public void smoothRejectsMismatchedArrayLengths() throws Throwable {
        double[] xValuesWithThreeElements = new double[3];
        double[] yValuesWithSixElements = new double[6];
        LoessInterpolator interpolator = new LoessInterpolator();

        try {
            interpolator.smooth(xValuesWithThreeElements, yValuesWithSixElements);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Thrown by LoessInterpolator because the array lengths differ: 3 != 6
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
