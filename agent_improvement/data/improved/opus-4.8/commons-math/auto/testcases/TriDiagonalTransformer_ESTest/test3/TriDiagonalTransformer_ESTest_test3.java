package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test3 extends TriDiagonalTransformer_ESTest_scaffolding {

    /**
     * The tridiagonal transformation is only defined for square matrices, so
     * constructing a TriDiagonalTransformer from a non-square matrix (here
     * 4096 rows x 1071 columns) must fail with a RuntimeException.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNonSquareMatrix() throws Throwable {
        int rows = 4096;
        int columns = 1071;
        OpenMapRealMatrix nonSquareMatrix = new OpenMapRealMatrix(rows, columns);

        try {
            new TriDiagonalTransformer(nonSquareMatrix);
            fail("Expected a RuntimeException for a non-square matrix");
        } catch (RuntimeException e) {
            // Thrown by TriDiagonalTransformer because the matrix is not square.
            verifyException("org.apache.commons.math4.legacy.linear.TriDiagonalTransformer", e);
        }
    }
}
