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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // A 13-row by 1-column matrix of all zeros (default double values)
        double[][] singleColumnMatrix = new double[13][1];

        // A zero-filled array used as both x and y in the covariance computation
        double[] allZerosArray = new double[9];

        // biasCorrected=false means population covariance is computed
        Covariance covariance = new Covariance(singleColumnMatrix, false);

        // Covariance of an all-zeros array with itself is 0.0
        double result = covariance.covariance(allZerosArray, allZerosArray);
        assertEquals(0.0, result, 0.01);
    }
}
