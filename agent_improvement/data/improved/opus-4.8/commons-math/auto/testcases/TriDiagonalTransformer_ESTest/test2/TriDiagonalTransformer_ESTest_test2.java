package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test2 extends TriDiagonalTransformer_ESTest_scaffolding {

    private static final int MATRIX_SIZE = 10;

    /**
     * Verifies that the orthogonal matrix Q produced by the tridiagonal
     * transformation of a 10x10 (all-zero) matrix is itself 10x10,
     * i.e. its column dimension matches the size of the input matrix.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // A freshly created sparse matrix is all zeros (already tridiagonal).
        OpenMapRealMatrix inputMatrix = new OpenMapRealMatrix(MATRIX_SIZE, MATRIX_SIZE);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(inputMatrix);

        // Trigger the (lazy) computation of Q, then retrieve the cached result.
        transformer.getQ();
        RealMatrix orthogonalMatrixQ = transformer.getQ();

        // Q has the same dimension as the input matrix.
        assertEquals(MATRIX_SIZE, orthogonalMatrixQ.getColumnDimension());
    }
}
