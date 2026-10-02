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
public class Covariance_ESTest_test10 extends Covariance_ESTest_scaffolding {

    /**
     * Building a Covariance from a 6x6 matrix should report 6 observations,
     * since getN() returns the number of rows in the source matrix.
     */
    @Test(timeout = 4000)
    public void getNReturnsRowCountOfSourceMatrix() throws Throwable {
        final int expectedObservationCount = 6;

        // A DiagonalMatrix built from 6 diagonal entries is a 6x6 matrix.
        double[] diagonalEntries = new double[expectedObservationCount];
        DiagonalMatrix sourceMatrix = new DiagonalMatrix(diagonalEntries, false);

        Covariance covariance = new Covariance(sourceMatrix);

        assertEquals(expectedObservationCount, covariance.getN());
    }
}
