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
    public void test2() throws Throwable {
        OpenMapRealMatrix openMapRealMatrix0 = new OpenMapRealMatrix(10, 10);
        TriDiagonalTransformer triDiagonalTransformer0 = new TriDiagonalTransformer(openMapRealMatrix0);
        triDiagonalTransformer0.getQ();
        RealMatrix realMatrix0 = triDiagonalTransformer0.getQ();
        assertEquals(10, realMatrix0.getColumnDimension());
    }
}
