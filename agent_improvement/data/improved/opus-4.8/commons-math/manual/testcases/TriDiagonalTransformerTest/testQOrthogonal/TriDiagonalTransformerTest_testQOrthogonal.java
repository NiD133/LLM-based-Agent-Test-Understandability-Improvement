package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that the Q matrix produced by {@link TriDiagonalTransformer} is orthogonal,
 * i.e. that Q<sup>T</sup>Q equals the identity matrix.
 */
public class TriDiagonalTransformerTest_testQOrthogonal {

    /** A 5x5 symmetric test matrix. */
    private final double[][] symmetricMatrix5x5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    /** A 3x3 symmetric test matrix. */
    private final double[][] symmetricMatrix3x3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /** Largest acceptable deviation of Q<sup>T</sup>Q from the identity matrix. */
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;

    @Test
    public void testQOrthogonal() {
        assertQIsOrthogonal(symmetricMatrix5x5);
        assertQIsOrthogonal(symmetricMatrix3x3);
    }

    /**
     * Asserts that the Q matrix of the tri-diagonal transformation of the given
     * matrix is orthogonal.
     */
    private void assertQIsOrthogonal(double[][] inputMatrix) {
        RealMatrix matrix = MatrixUtils.createRealMatrix(inputMatrix);
        RealMatrix q = new TriDiagonalTransformer(matrix).getQ();

        RealMatrix qTransposeTimesQ = q.transpose().multiply(q);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(qTransposeTimesQ.getRowDimension());

        double deviationFromIdentity = qTransposeTimesQ.subtract(identity).getNorm();
        Assert.assertEquals(0, deviationFromIdentity, ORTHOGONALITY_TOLERANCE);
    }
}
