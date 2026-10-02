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
     * Transforming an all-zero square matrix should yield an all-zero main diagonal
     * whose length equals the matrix dimension.
     */
    @Test(timeout = 4000)
    public void mainDiagonalOfZeroMatrixIsAllZeros() throws Throwable {
        final int matrixSize = 10;
        OpenMapRealMatrix zeroMatrix = new OpenMapRealMatrix(matrixSize, matrixSize);

        TriDiagonalTransformer transformer = new TriDiagonalTransformer(zeroMatrix);
        double[] mainDiagonal = transformer.getMainDiagonalRef();

        double[] expectedDiagonal = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        assertArrayEquals(expectedDiagonal, mainDiagonal, 0.01);
        assertEquals(matrixSize, mainDiagonal.length);
    }
}
