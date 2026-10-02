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

    private static final int MATRIX_SIZE = 10;
    private static final double DELTA = 0.01;

    /**
     * Verifies that the main diagonal of a TriDiagonalTransformer built from
     * an all-zero 10x10 sparse matrix consists entirely of zeros.
     *
     * A newly constructed OpenMapRealMatrix has all entries initialised to 0.0,
     * so the Householder-reduction step should produce a main diagonal that is
     * also all zeros, with length equal to the matrix dimension.
     */
    @Test(timeout = 4000)
    public void test_mainDiagonalIsAllZerosForZeroMatrix() throws Throwable {
        OpenMapRealMatrix zeroMatrix = new OpenMapRealMatrix(MATRIX_SIZE, MATRIX_SIZE);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(zeroMatrix);

        double[] mainDiagonal = transformer.getMainDiagonalRef();

        double[] expectedAllZeros = new double[MATRIX_SIZE];
        assertArrayEquals(expectedAllZeros, mainDiagonal, DELTA);
        assertEquals(MATRIX_SIZE, mainDiagonal.length);
    }
}
