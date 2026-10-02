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

    // A 10x10 zero matrix produces an all-zero main diagonal after tri-diagonal transformation.
    @Test(timeout = 4000)
    public void test_mainDiagonalOfZeroMatrixIsAllZeros() throws Throwable {
        int size = 10;
        OpenMapRealMatrix zeroMatrix = new OpenMapRealMatrix(size, size);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(zeroMatrix);

        double[] mainDiagonal = transformer.getMainDiagonalRef();

        double[] expectedAllZeros = new double[size];
        assertArrayEquals(expectedAllZeros, mainDiagonal, 0.01);
        assertEquals(size, mainDiagonal.length);
    }
}
