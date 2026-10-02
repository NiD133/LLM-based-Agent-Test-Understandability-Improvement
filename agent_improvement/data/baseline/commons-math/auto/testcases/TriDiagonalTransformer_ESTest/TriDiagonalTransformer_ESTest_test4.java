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
        OpenMapRealMatrix openMapRealMatrix0 = new OpenMapRealMatrix(34, 34);
        TriDiagonalTransformer triDiagonalTransformer0 = new TriDiagonalTransformer(openMapRealMatrix0);
        double[][] doubleArray0 = triDiagonalTransformer0.getHouseholderVectorsRef();
        assertEquals(34, doubleArray0.length);
    }
}
