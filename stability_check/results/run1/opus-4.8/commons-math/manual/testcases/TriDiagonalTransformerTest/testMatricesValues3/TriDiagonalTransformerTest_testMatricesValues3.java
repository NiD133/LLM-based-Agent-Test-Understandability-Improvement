package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} produces the expected tridiagonal
 * decomposition {@code A = Q * T * Q^T} for a specific 3x3 symmetric matrix.
 *
 * <p>The transformer is checked against pre-computed reference values for both
 * the orthogonal matrix {@code Q} and the tridiagonal matrix {@code T}, and it
 * is also confirmed that {@code Q} and {@code T} are cached (the same instance
 * is returned on repeated calls).</p>
 */
public class TriDiagonalTransformerTest_testMatricesValues3 {

    /** Tolerance used when comparing matrix norms against the expected value of zero. */
    private static final double NORM_TOLERANCE = 1.0e-14;

    /** The symmetric 3x3 matrix to decompose. */
    private final double[][] inputMatrix = { { 1, 3, 4 }, { 3, 2, 2 }, { 4, 2, 0 } };

    /** Expected orthogonal matrix Q of the decomposition. */
    private final double[][] expectedQ = {
        { 1.0,  0.0,  0.0 },
        { 0.0, -0.6,  0.8 },
        { 0.0, -0.8, -0.6 }
    };

    /** Expected main diagonal of the tridiagonal matrix T. */
    private final double[] expectedMainDiagonal = { 1, 2.64, -0.64 };

    /** Expected sub-/super-diagonal of the tridiagonal matrix T. */
    private final double[] expectedSecondaryDiagonal = { -5, -1.52 };

    @Test
    public void testMatricesValues3() {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(inputMatrix));

        // Q must match the known reference orthogonal matrix.
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0,
            q.subtract(MatrixUtils.createRealMatrix(expectedQ)).getNorm(),
            NORM_TOLERANCE);

        // T must match the tridiagonal matrix built from the expected diagonals.
        RealMatrix t = transformer.getT();
        RealMatrix expectedT =
            MatrixUtils.createRealMatrix(buildTridiagonal(expectedMainDiagonal, expectedSecondaryDiagonal));
        Assert.assertEquals(0,
            t.subtract(expectedT).getNorm(),
            NORM_TOLERANCE);

        // Q and T should be cached: a second call returns the same instances.
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    /**
     * Builds a square tridiagonal matrix from its main diagonal and its
     * secondary diagonal (which fills both the sub- and super-diagonals).
     */
    private double[][] buildTridiagonal(double[] mainDiagonal, double[] secondaryDiagonal) {
        double[][] data = new double[mainDiagonal.length][mainDiagonal.length];
        for (int i = 0; i < mainDiagonal.length; ++i) {
            data[i][i] = mainDiagonal[i];
            if (i > 0) {
                data[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                data[i][i + 1] = secondaryDiagonal[i];
            }
        }
        return data;
    }
}
