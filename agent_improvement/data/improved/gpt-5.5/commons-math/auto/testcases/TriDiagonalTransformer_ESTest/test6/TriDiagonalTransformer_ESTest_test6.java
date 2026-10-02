package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test6 extends TriDiagonalTransformer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        OpenMapRealMatrix squareMatrix = new OpenMapRealMatrix(34, 34);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(squareMatrix);

        double[] secondaryDiagonal = transformer.getSecondaryDiagonalRef();

        assertEquals(33, secondaryDiagonal.length);
    }
}
