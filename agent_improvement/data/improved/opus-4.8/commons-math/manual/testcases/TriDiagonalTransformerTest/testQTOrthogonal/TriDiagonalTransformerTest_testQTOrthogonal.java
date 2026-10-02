package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that the transpose of the orthogonal matrix Q produced by
 * {@link TriDiagonalTransformer} is itself orthogonal, i.e. that
 * {@code QT^T * QT} equals the identity matrix.
 */
public class TriDiagonalTransformerTest_testQTOrthogonal {

    /** A 5x5 symmetric matrix used as transformer input. */
    private final double[][] symmetric5x5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    /** A 3x3 symmetric matrix used as transformer input. */
    private final double[][] symmetric3x3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /**
     * Asserts that {@code matrix} is orthogonal: multiplying it by its own
     * transpose must yield the identity matrix (within tolerance).
     */
    private void assertOrthogonal(RealMatrix matrix) {
        RealMatrix shouldBeIdentity = matrix.transpose().multiply(matrix);
        RealMatrix identity =
            MatrixUtils.createRealIdentityMatrix(shouldBeIdentity.getRowDimension());
        double deviationFromIdentity = shouldBeIdentity.subtract(identity).getNorm();
        Assert.assertEquals(0, deviationFromIdentity, 1.0e-15);
    }

    private RealMatrix transformerQTFor(double[][] data) {
        RealMatrix input = MatrixUtils.createRealMatrix(data);
        return new TriDiagonalTransformer(input).getQT();
    }

    @Test
    public void testQTOrthogonal() {
        assertOrthogonal(transformerQTFor(symmetric5x5));
        assertOrthogonal(transformerQTFor(symmetric3x3));
    }
}
