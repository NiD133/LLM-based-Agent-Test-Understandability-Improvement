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

    private static final int ROW_COUNT = 6;
    private static final int COLUMN_COUNT = 7;
    private static final boolean BIAS_CORRECTED = false;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        double[][] data = new double[ROW_COUNT][COLUMN_COUNT];

        Covariance covariance = new Covariance(data, BIAS_CORRECTED);

        assertEquals(ROW_COUNT, covariance.getN());
    }
}
