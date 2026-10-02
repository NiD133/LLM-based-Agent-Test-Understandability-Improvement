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
public class Covariance_ESTest_test01 extends Covariance_ESTest_scaffolding {

    /**
     * Verifies that getN() reports the number of observations (rows) supplied
     * to the Covariance constructor, regardless of the bias-correction flag.
     */
    @Test(timeout = 4000)
    public void getN_returnsNumberOfObservationRows() throws Throwable {
        // Data laid out as 6 observations (rows), each with 7 variables (columns).
        int observationCount = 6;
        int variableCount = 7;
        double[][] observations = new double[observationCount][variableCount];

        boolean biasCorrected = false;
        Covariance covariance = new Covariance(observations, biasCorrected);

        assertEquals(observationCount, covariance.getN());
    }
}
