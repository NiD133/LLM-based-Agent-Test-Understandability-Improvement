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

    /**
     * Verifies that for a tridiagonal transformation, the orthogonal matrix Q
     * and its transpose Q^T are not reported as equal by RealMatrix.equals.
     */
    @Test(timeout = 4000)
    public void getQAndGetQTReturnDistinctMatrices() throws Throwable {
        // Build a length-6 vector whose entries are all the same small value.
        final double sharedValue = 1.0E-6;
        Double[] vectorEntries = new Double[6];
        for (int i = 0; i < vectorEntries.length; i++) {
            vectorEntries[i] = sharedValue;
        }

        // Form a symmetric matrix as the outer product of the vector with itself.
        OpenMapRealVector sparseVector = new OpenMapRealVector(vectorEntries);
        ArrayRealVector denseVector = new ArrayRealVector(sparseVector);
        RealMatrix symmetricMatrix = denseVector.outerProduct(sparseVector);

        // Reduce the matrix to tridiagonal form and extract Q and Q^T.
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(symmetricMatrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qTransposed = transformer.getQT();

        assertFalse(qTransposed.equals((Object) q));
    }
}
