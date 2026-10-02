package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test0 extends TriDiagonalTransformer_ESTest_scaffolding {

    // Verifies that the tri-diagonal matrix T produced by transforming a 10x10 sparse matrix
    // retains the original column dimension of 10.
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        int matrixSize = 10;
        OpenMapRealMatrix sparseMatrix = new OpenMapRealMatrix(matrixSize, matrixSize);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(sparseMatrix);

        // First call to getT() — result intentionally discarded (mirrors original behaviour)
        transformer.getT();

        RealMatrix triDiagonalMatrix = transformer.getT();
        assertEquals(
            "The tri-diagonal matrix T should have the same number of columns as the original matrix",
            matrixSize,
            triDiagonalMatrix.getColumnDimension()
        );
    }
}
