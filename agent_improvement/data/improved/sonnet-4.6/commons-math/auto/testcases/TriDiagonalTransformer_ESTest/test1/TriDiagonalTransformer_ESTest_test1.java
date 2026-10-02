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

    // A small epsilon value used to populate the vector entries
    private static final double SMALL_VALUE = 1.0E-6;

    /**
     * Verifies that the orthogonal matrix Q and its transpose QT produced by
     * TriDiagonalTransformer are distinct objects (i.e., Q != QT).
     *
     * The input is a rank-1 matrix constructed as the outer product of a
     * 6-element vector whose entries all equal SMALL_VALUE.
     */
    @Test(timeout = 4000)
    public void test_QAndQTransposeAreDistinctMatrices() throws Throwable {
        // Build a 6-element vector with all entries set to SMALL_VALUE
        Double smallValue = new Double(SMALL_VALUE);
        Double[] vectorEntries = new Double[6];
        vectorEntries[0] = smallValue;
        vectorEntries[1] = smallValue;
        vectorEntries[2] = smallValue;
        vectorEntries[3] = vectorEntries[0];
        vectorEntries[4] = smallValue;
        vectorEntries[5] = vectorEntries[2];

        OpenMapRealVector sparseVector = new OpenMapRealVector(vectorEntries);
        ArrayRealVector denseVector = new ArrayRealVector(sparseVector);

        // Create a symmetric rank-1 matrix via outer product: M = v * vT
        RealMatrix rankOneMatrix = denseVector.outerProduct(sparseVector);

        // Apply the tri-diagonal transformation to the symmetric matrix
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(rankOneMatrix);

        // Retrieve the orthogonal factor Q and its transpose QT
        RealMatrix q = transformer.getQ();
        RealMatrix qt = transformer.getQT();

        // Q and QT must not be equal as objects (they represent different transformations)
        assertFalse(qt.equals((Object) q));
    }
}
