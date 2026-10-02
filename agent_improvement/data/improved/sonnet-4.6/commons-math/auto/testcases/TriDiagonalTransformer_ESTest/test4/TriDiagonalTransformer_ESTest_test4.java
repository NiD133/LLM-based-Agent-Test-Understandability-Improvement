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

    // Matrix dimension used throughout the test
    private static final int MATRIX_SIZE = 34;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // Create a 34x34 sparse real matrix to serve as input for the tridiagonal transformation
        OpenMapRealMatrix sparseMatrix = new OpenMapRealMatrix(MATRIX_SIZE, MATRIX_SIZE);

        // Apply the tridiagonal transformation (Householder reduction) to the matrix
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(sparseMatrix);

        // Retrieve the internal Householder vectors produced during the transformation
        double[][] householderVectors = transformer.getHouseholderVectorsRef();

        // The number of Householder vectors must equal the number of rows in the original matrix
        assertEquals("Householder vectors array should have one row per matrix row",
                MATRIX_SIZE, householderVectors.length);
    }
}
