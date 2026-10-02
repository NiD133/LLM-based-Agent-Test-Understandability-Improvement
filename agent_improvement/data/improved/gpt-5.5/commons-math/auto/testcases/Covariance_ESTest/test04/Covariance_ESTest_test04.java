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
        final int rowCount = 13;
        final int columnCount = 1;
        double[][] inputData = new double[rowCount][columnCount];

        final int vectorLength = 9;
        double[] zeroValues = new double[vectorLength];

        final boolean biasCorrected = false;
        Covariance covariance = new Covariance(inputData, biasCorrected);

        double selfCovariance = covariance.covariance(zeroValues, zeroValues);

        assertEquals(0.0, selfCovariance, 0.01);
    }
}
