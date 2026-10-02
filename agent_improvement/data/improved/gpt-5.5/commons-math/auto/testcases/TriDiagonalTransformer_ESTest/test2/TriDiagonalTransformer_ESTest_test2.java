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
        OpenMapRealMatrix tenByTenMatrix = new OpenMapRealMatrix(10, 10);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(tenByTenMatrix);

        transformer.getQ();
        RealMatrix qMatrix = transformer.getQ();

        assertEquals("Q matrix should keep the original column dimension", 10, qMatrix.getColumnDimension());
    }
}
