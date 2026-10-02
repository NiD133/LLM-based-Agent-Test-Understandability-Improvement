package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test03 extends Covariance_ESTest_scaffolding {

    /**
     * covariance(double[], double[]) requires both input arrays to have the same
     * length. Here the arrays differ in length (2 vs. 1), so the computation must
     * fail with a RuntimeException reporting the dimension mismatch ("2 != 1").
     */
    @Test(timeout = 4000)
    public void covarianceWithMismatchedArrayLengthsThrows() throws Throwable {
        Covariance covariance = new Covariance();
        double[] arrayOfLengthOne = new double[1];
        double[] arrayOfLengthTwo = new double[2];

        try {
            covariance.covariance(arrayOfLengthTwo, arrayOfLengthOne);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Dimension mismatch: array lengths 2 != 1
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
