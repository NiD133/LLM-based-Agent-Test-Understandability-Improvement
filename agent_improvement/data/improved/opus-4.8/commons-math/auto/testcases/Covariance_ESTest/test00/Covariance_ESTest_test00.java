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
public class Covariance_ESTest_test00 extends Covariance_ESTest_scaffolding {

    /**
     * Computing a covariance matrix requires more than one observation, so a data
     * set with only a single row (one observation) and a single column must be
     * rejected. Constructing a Covariance from such a 1x1 matrix is expected to
     * throw a RuntimeException reporting the insufficient data.
     */
    @Test(timeout = 4000)
    public void constructorWithSingleObservationThrowsInsufficientDataException() throws Throwable {
        // Data matrix with a single observation (1 row) and a single variable (1 column).
        double[][] singleObservationData = new double[1][1];

        try {
            new Covariance(singleObservationData);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Expected: "insufficient data: only 1 rows and 1 columns."
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
