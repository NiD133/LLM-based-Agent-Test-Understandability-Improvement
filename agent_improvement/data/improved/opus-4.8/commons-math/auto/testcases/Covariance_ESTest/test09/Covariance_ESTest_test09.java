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
public class Covariance_ESTest_test09 extends Covariance_ESTest_scaffolding {

    /**
     * Verifies that the number of observations reported by getN() equals the
     * number of rows in the data matrix used to build the Covariance instance.
     */
    @Test(timeout = 4000)
    public void getN_returnsNumberOfObservationRows() throws Throwable {
        final int numberOfObservations = 6;
        final int numberOfVariables = 1;

        // Each row is one observation; each column is one variable.
        double[][] observations = new double[numberOfObservations][numberOfVariables];
        Covariance covariance = new Covariance(observations);

        assertEquals(numberOfObservations, covariance.getN());
    }
}
