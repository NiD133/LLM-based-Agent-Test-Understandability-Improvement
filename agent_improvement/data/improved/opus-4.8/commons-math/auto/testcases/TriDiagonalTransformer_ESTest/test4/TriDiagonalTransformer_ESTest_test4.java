package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test4 extends TriDiagonalTransformer_ESTest_scaffolding {

    /**
     * Verifies that the Householder vectors matrix produced by the transformer has one
     * row per row of the input matrix. A 34x34 matrix should therefore yield 34 rows.
     */
    @Test(timeout = 4000)
    public void householderVectorsHaveOneRowPerMatrixRow() throws Throwable {
        final int matrixSize = 34;
        OpenMapRealMatrix squareMatrix = new OpenMapRealMatrix(matrixSize, matrixSize);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(squareMatrix);

        double[][] householderVectors = transformer.getHouseholderVectorsRef();

        assertEquals(matrixSize, householderVectors.length);
    }
}
