package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test5 extends TriDiagonalTransformer_ESTest_scaffolding {

    /**
     * Tridiagonalizing an all-zero 10x10 matrix should yield a main diagonal
     * of ten zeros (the matrix is already trivially tridiagonal).
     */
    @Test(timeout = 4000)
    public void mainDiagonalOfZeroMatrixIsAllZeros() throws Throwable {
        int dimension = 10;
        OpenMapRealMatrix zeroMatrix = new OpenMapRealMatrix(dimension, dimension);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(zeroMatrix);

        double[] mainDiagonal = transformer.getMainDiagonalRef();

        double[] expectedDiagonal = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        double tolerance = 0.01;
        assertArrayEquals(expectedDiagonal, mainDiagonal, tolerance);
        assertEquals(dimension, mainDiagonal.length);
    }
}
