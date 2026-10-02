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
     * Verifies that the secondary diagonal of the tridiagonal form has length
     * (n - 1) for an n-by-n input matrix. Here n = 34, so the secondary
     * diagonal reference array should contain 33 elements.
     */
    @Test(timeout = 4000)
    public void test6() throws Throwable {
        final int matrixSize = 34;
        OpenMapRealMatrix squareMatrix = new OpenMapRealMatrix(matrixSize, matrixSize);

        TriDiagonalTransformer transformer = new TriDiagonalTransformer(squareMatrix);
        double[] secondaryDiagonal = transformer.getSecondaryDiagonalRef();

        assertEquals(matrixSize - 1, secondaryDiagonal.length);
    }
}
