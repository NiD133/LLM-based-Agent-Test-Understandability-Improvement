package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test0 extends TriDiagonalTransformer_ESTest_scaffolding {

    /**
     * Verifies that the tridiagonal matrix T produced from a 10x10 input matrix
     * preserves the input's column dimension, and that requesting T more than
     * once is supported (the result is cached after the first call).
     */
    @Test(timeout = 4000)
    public void tridiagonalMatrixHasSameColumnDimensionAsInput() throws Throwable {
        int dimension = 10;
        OpenMapRealMatrix inputMatrix = new OpenMapRealMatrix(dimension, dimension);

        TriDiagonalTransformer transformer = new TriDiagonalTransformer(inputMatrix);

        // First call performs the decomposition; the second returns the cached result.
        transformer.getT();
        RealMatrix tridiagonalMatrix = transformer.getT();

        assertEquals(dimension, tridiagonalMatrix.getColumnDimension());
    }
}
