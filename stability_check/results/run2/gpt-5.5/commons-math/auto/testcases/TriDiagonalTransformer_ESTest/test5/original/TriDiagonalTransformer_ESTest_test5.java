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

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        OpenMapRealMatrix openMapRealMatrix0 = new OpenMapRealMatrix(10, 10);
        TriDiagonalTransformer triDiagonalTransformer0 = new TriDiagonalTransformer(openMapRealMatrix0);
        double[] doubleArray0 = triDiagonalTransformer0.getMainDiagonalRef();
        assertArrayEquals(new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, doubleArray0, 0.01);
        assertEquals(10, doubleArray0.length);
    }
}
