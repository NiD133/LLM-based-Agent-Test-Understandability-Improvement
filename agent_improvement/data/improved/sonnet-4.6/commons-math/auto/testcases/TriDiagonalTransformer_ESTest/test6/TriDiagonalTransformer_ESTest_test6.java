package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test6 extends TriDiagonalTransformer_ESTest_scaffolding {

    /**
     * A square matrix of size N, when transformed to tridiagonal form,
     * produces a secondary diagonal of length N-1.
     * This test verifies that a 34x34 matrix yields a secondary diagonal with 33 elements.
     */
    @Test(timeout = 4000)
    public void test_secondaryDiagonalLength_isOneLessThanMatrixDimension() throws Throwable {
        int matrixSize = 34;
        OpenMapRealMatrix squareMatrix = new OpenMapRealMatrix(matrixSize, matrixSize);

        TriDiagonalTransformer transformer = new TriDiagonalTransformer(squareMatrix);
        double[] secondaryDiagonal = transformer.getSecondaryDiagonalRef();

        int expectedSecondaryDiagonalLength = matrixSize - 1;
        assertEquals(expectedSecondaryDiagonalLength, secondaryDiagonal.length);
    }
}
