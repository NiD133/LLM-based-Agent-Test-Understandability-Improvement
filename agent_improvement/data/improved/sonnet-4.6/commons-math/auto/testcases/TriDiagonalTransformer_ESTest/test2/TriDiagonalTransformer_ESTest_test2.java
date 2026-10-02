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

    @Test(timeout = 4000)
    public void test_getQ_returnsMatrixWithSameDimensionAsInput() throws Throwable {
        // Create a 10x10 zero sparse matrix and transform it to tridiagonal form
        OpenMapRealMatrix inputMatrix = new OpenMapRealMatrix(10, 10);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(inputMatrix);

        // Call getQ() once to trigger any lazy initialization, then retrieve Q again
        transformer.getQ();
        RealMatrix qMatrix = transformer.getQ();

        // Q must have the same number of columns as the original matrix
        assertEquals(10, qMatrix.getColumnDimension());
    }
}
