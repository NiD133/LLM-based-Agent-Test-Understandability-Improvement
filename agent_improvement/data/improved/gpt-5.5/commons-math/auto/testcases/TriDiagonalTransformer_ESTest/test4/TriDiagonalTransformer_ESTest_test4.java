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

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        final int matrixOrder = 34;
        OpenMapRealMatrix squareMatrix = new OpenMapRealMatrix(matrixOrder, matrixOrder);

        TriDiagonalTransformer transformer = new TriDiagonalTransformer(squareMatrix);
        double[][] householderVectors = transformer.getHouseholderVectorsRef();

        assertEquals("Householder vector matrix should preserve the input matrix order",
                matrixOrder, householderVectors.length);
    }
}
