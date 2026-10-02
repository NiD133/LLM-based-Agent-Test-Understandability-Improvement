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
public class Covariance_ESTest_test04 extends Covariance_ESTest_scaffolding {

    /**
     * The covariance of two all-zero data vectors is zero, because neither
     * series deviates from its mean.
     */
    @Test(timeout = 4000)
    public void covarianceOfTwoZeroVectorsIsZero() throws Throwable {
        // A Covariance instance is required to call the covariance(...) helper;
        // its constructor data does not affect this particular computation.
        double[][] constructorData = new double[13][1];
        Covariance covariance = new Covariance(constructorData, false);

        // Two identical vectors whose entries are all 0.0.
        double[] zeroVector = new double[9];
        double actualCovariance = covariance.covariance(zeroVector, zeroVector);

        double expectedCovariance = 0.0;
        double tolerance = 0.01;
        assertEquals(expectedCovariance, actualCovariance, tolerance);
    }
}
