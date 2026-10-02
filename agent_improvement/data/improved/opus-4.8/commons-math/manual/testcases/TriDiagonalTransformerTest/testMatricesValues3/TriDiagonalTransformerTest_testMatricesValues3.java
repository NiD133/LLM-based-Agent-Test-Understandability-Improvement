package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} produces the expected Q and T
 * matrices for a known 3x3 symmetric input, and that Q and T are cached.
 *
 * <p>A tri-diagonal decomposition factors a symmetric matrix A as A = Q * T * Q^T,
 * where Q is orthogonal and T is tri-diagonal (non-zero only on the main diagonal
 * and the two adjacent diagonals).
 */
public class TriDiagonalTransformerTest_testMatricesValues3 {

    /** Tolerance used when comparing matrix norms against the expected references. */
    private static final double NORM_TOLERANCE = 1.0e-14;

    /** A symmetric 3x3 matrix to be tri-diagonalized. */
    private static final double[][] SYMMETRIC_3X3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /** Expected orthogonal matrix Q for {@link #SYMMETRIC_3X3}. */
    private static final double[][] EXPECTED_Q = {
        { 1.0,  0.0,  0.0 },
        { 0.0, -0.6,  0.8 },
        { 0.0, -0.8, -0.6 }
    };

    /** Expected main diagonal of the tri-diagonal matrix T. */
    private static final double[] EXPECTED_MAIN_DIAGONAL = { 1, 2.64, -0.64 };

    /** Expected secondary (sub/super) diagonal of the tri-diagonal matrix T. */
    private static final double[] EXPECTED_SECONDARY_DIAGONAL = { -5, -1.52 };

    @Test
    public void testMatricesValues3() {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(SYMMETRIC_3X3));

        // Q must match the known reference orthogonal matrix.
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0,
            q.subtract(MatrixUtils.createRealMatrix(EXPECTED_Q)).getNorm(),
            NORM_TOLERANCE);

        // T must match the tri-diagonal matrix built from the expected diagonals.
        RealMatrix t = transformer.getT();
        Assert.assertEquals(0,
            t.subtract(MatrixUtils.createRealMatrix(buildExpectedT())).getNorm(),
            NORM_TOLERANCE);

        // Q and T are computed lazily and cached: a second call returns the same instance.
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    /**
     * Assembles the expected tri-diagonal matrix T from its main and secondary
     * diagonals: the secondary diagonal values are placed symmetrically just
     * above and below the main diagonal.
     */
    private static double[][] buildExpectedT() {
        int size = EXPECTED_MAIN_DIAGONAL.length;
        double[][] tData = new double[size][size];
        for (int i = 0; i < size; ++i) {
            tData[i][i] = EXPECTED_MAIN_DIAGONAL[i];
            if (i > 0) {
                tData[i][i - 1] = EXPECTED_SECONDARY_DIAGONAL[i - 1];
            }
            if (i < EXPECTED_SECONDARY_DIAGONAL.length) {
                tData[i][i + 1] = EXPECTED_SECONDARY_DIAGONAL[i];
            }
        }
        return tData;
    }
}
