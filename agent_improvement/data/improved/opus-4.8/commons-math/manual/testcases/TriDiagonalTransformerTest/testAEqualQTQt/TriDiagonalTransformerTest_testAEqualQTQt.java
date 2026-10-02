package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that the tridiagonal decomposition reconstructs the original matrix,
 * i.e. that {@code A == Q * T * Q^T}.
 */
public class TriDiagonalTransformerTest_testAEqualQTQt {

    /** A 5x5 symmetric matrix used as decomposition input. */
    private final double[][] symmetricMatrix5x5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    /** A 3x3 symmetric matrix used as decomposition input. */
    private final double[][] symmetricMatrix3x3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /** Largest acceptable reconstruction error for {@code Q * T * Q^T - A}. */
    private static final double RECONSTRUCTION_TOLERANCE = 4.0e-15;

    @Test
    public void testAEqualQTQt() {
        assertReconstructsOriginal(MatrixUtils.createRealMatrix(symmetricMatrix5x5));
        assertReconstructsOriginal(MatrixUtils.createRealMatrix(symmetricMatrix3x3));
    }

    /**
     * Decomposes {@code matrix} and asserts that multiplying the factors back
     * together ({@code Q * T * Q^T}) reproduces the original matrix.
     */
    private void assertReconstructsOriginal(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();

        RealMatrix reconstructed = q.multiply(t).multiply(qT);
        double reconstructionError = reconstructed.subtract(matrix).getNorm();

        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }
}
