package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test3 extends TriDiagonalTransformer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        OpenMapRealMatrix nonSquareMatrix = new OpenMapRealMatrix(4096, 1071);
        TriDiagonalTransformer transformer = null;

        try {
            transformer = new TriDiagonalTransformer(nonSquareMatrix);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // non square (4,096x1,071) matrix
            verifyException("org.apache.commons.math4.legacy.linear.TriDiagonalTransformer", e);
        }
    }
}
