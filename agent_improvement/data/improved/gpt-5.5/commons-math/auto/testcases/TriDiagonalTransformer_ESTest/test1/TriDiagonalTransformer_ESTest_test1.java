package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TriDiagonalTransformer_ESTest_test1 extends TriDiagonalTransformer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Double[] vectorEntries = new Double[6];
        Double smallEntry = new Double(1.0E-6);
        vectorEntries[0] = smallEntry;
        vectorEntries[1] = smallEntry;
        vectorEntries[2] = smallEntry;
        vectorEntries[3] = vectorEntries[0];
        vectorEntries[4] = smallEntry;
        vectorEntries[5] = vectorEntries[2];

        OpenMapRealVector sparseVector = new OpenMapRealVector(vectorEntries);
        ArrayRealVector denseVector = new ArrayRealVector(sparseVector);
        RealMatrix symmetricMatrix = denseVector.outerProduct(sparseVector);

        TriDiagonalTransformer transformer = new TriDiagonalTransformer(symmetricMatrix);
        RealMatrix qMatrix = transformer.getQ();
        RealMatrix qTransposeMatrix = transformer.getQT();

        assertFalse(qTransposeMatrix.equals((Object) qMatrix));
    }
}
