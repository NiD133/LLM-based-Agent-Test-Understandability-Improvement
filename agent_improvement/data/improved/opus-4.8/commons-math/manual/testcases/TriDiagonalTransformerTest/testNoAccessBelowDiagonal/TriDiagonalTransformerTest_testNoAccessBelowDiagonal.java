package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} never reads the strictly
 * lower-triangular part of a symmetric input matrix. It only relies on the
 * diagonal and the upper triangle, so poisoning the lower triangle with NaN
 * must not affect the reconstructed matrix Q * T * Q^T.
 */
public class TriDiagonalTransformerTest_testNoAccessBelowDiagonal {

    /** A 5x5 symmetric matrix used as test input. */
    private final double[][] symmetricMatrix5x5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    /** A 3x3 symmetric matrix used as test input. */
    private final double[][] symmetricMatrix3x3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /** Largest acceptable deviation when comparing reconstructed matrices. */
    private static final double RECONSTRUCTION_TOLERANCE = 4.0e-15;

    @Test
    public void testNoAccessBelowDiagonal() {
        assertLowerTriangleIsNeverRead(symmetricMatrix5x5);
        assertLowerTriangleIsNeverRead(symmetricMatrix3x3);
    }

    /**
     * Builds a copy of {@code originalData} whose strictly lower-triangular
     * entries are replaced with NaN, transforms it, and asserts that the
     * reconstruction Q * T * Q^T still equals the original matrix. If the
     * transformer read any NaN entry, the reconstruction would contain NaN and
     * the assertion would fail.
     */
    private void assertLowerTriangleIsNeverRead(double[][] originalData) {
        double[][] poisonedData = new double[originalData.length][];
        for (int row = 0; row < originalData.length; ++row) {
            poisonedData[row] = originalData[row].clone();
            // Fill the strictly lower-triangular part (columns before the diagonal) with NaN.
            Arrays.fill(poisonedData[row], 0, row, Double.NaN);
        }

        RealMatrix poisonedMatrix = MatrixUtils.createRealMatrix(poisonedData);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(poisonedMatrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();

        RealMatrix originalMatrix = MatrixUtils.createRealMatrix(originalData);
        double reconstructionError = q.multiply(t).multiply(qT).subtract(originalMatrix).getNorm();
        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }
}
