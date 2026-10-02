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
        Double[] doubleArray0 = new Double[6];
        Double double0 = new Double(1.0E-6);
        doubleArray0[0] = double0;
        doubleArray0[1] = double0;
        doubleArray0[2] = double0;
        doubleArray0[3] = doubleArray0[0];
        doubleArray0[4] = double0;
        doubleArray0[5] = doubleArray0[2];
        OpenMapRealVector openMapRealVector0 = new OpenMapRealVector(doubleArray0);
        ArrayRealVector arrayRealVector0 = new ArrayRealVector(openMapRealVector0);
        RealMatrix realMatrix0 = arrayRealVector0.outerProduct(openMapRealVector0);
        TriDiagonalTransformer triDiagonalTransformer0 = new TriDiagonalTransformer(realMatrix0);
        RealMatrix realMatrix1 = triDiagonalTransformer0.getQ();
        RealMatrix realMatrix2 = triDiagonalTransformer0.getQT();
        assertFalse(realMatrix2.equals((Object) realMatrix1));
    }
}
