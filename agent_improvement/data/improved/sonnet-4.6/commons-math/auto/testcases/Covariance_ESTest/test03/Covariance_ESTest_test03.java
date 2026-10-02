package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.legacy.linear.DiagonalMatrix;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test03 extends Covariance_ESTest_scaffolding {

    /**
     * Verifies that computing covariance between two arrays of different lengths
     * throws a RuntimeException. The covariance operation requires both input arrays
     * to have the same length; here array1 has 2 elements and array2 has 1 element.
     */
    @Test(timeout = 4000)
    public void test03_covarianceThrowsExceptionForMismatchedArrayLengths() throws Throwable {
        Covariance covariance = new Covariance();
        double[] arrayOfLength1 = new double[1];
        double[] arrayOfLength2 = new double[2];

        try {
            covariance.covariance(arrayOfLength2, arrayOfLength1);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Expected: "2 != 1" because the two arrays have different lengths
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
