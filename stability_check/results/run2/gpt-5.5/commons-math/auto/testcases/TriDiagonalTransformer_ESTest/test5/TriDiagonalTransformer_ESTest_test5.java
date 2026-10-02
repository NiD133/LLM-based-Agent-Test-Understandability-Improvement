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

    private static final int MATRIX_ORDER = 10;
    private static final double DIAGONAL_ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        OpenMapRealMatrix zeroMatrix = new OpenMapRealMatrix(MATRIX_ORDER, MATRIX_ORDER);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(zeroMatrix);

        double[] mainDiagonal = transformer.getMainDiagonalRef();

        assertArrayEquals(new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, mainDiagonal, DIAGONAL_ASSERTION_TOLERANCE);
        assertEquals(MATRIX_ORDER, mainDiagonal.length);
    }
}
