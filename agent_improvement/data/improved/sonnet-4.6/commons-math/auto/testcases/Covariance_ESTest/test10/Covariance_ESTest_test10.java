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

    private static final int MATRIX_DIMENSION = 6;

    @Test(timeout = 4000)
    public void test_getN_returnsMatrixDimension_whenConstructedFromDiagonalMatrix() throws Throwable {
        double[] diagonalValues = new double[MATRIX_DIMENSION];
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix(diagonalValues, false);
        Covariance covariance = new Covariance(diagonalMatrix);
        assertEquals(MATRIX_DIMENSION, covariance.getN());
    }
}
