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
     * Verifies that transforming a 10x10 zero matrix yields a main diagonal of
     * ten zero entries with the expected length.
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        // A 10x10 sparse matrix whose every entry is 0.0
        OpenMapRealMatrix zeroMatrix = new OpenMapRealMatrix(10, 10);

        // Apply the tri-diagonal (Householder) transformation
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(zeroMatrix);

        // The main diagonal of the resulting tri-diagonal form
        double[] mainDiagonal = transformer.getMainDiagonalRef();

        // All diagonal entries should be 0.0 for a zero input matrix
        assertArrayEquals(new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, mainDiagonal, 0.01);

        // The diagonal must have exactly one entry per matrix row/column
        assertEquals(10, mainDiagonal.length);
    }
}
