package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} produces the expected Q and T
 * matrices for a known 3x3 symmetric input matrix.
 *
 * <p>The tri-diagonalization decomposes a symmetric matrix A into A = Q * T * Qt,
 * where Q is orthogonal and T is tri-diagonal (non-zero only on the main diagonal
 * and the two adjacent diagonals). This test compares Q and T against pre-computed
 * reference values and confirms that both are cached across repeated accessor calls.</p>
 */
public class TriDiagonalTransformerTest_testMatricesValues3 {

    /** Symmetric 3x3 matrix to be tri-diagonalized. */
    private final double[][] symmetricMatrix3x3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /** Expected orthogonal matrix Q for {@link #symmetricMatrix3x3}. */
    private final double[][] expectedQ = {
        { 1.0,  0.0,  0.0 },
        { 0.0, -0.6,  0.8 },
        { 0.0, -0.8, -0.6 }
    };

    /** Expected main diagonal of the tri-diagonal matrix T. */
    private final double[] expectedMainDiagonal = { 1, 2.64, -0.64 };

    /** Expected secondary (off-main) diagonal of the tri-diagonal matrix T. */
    private final double[] expectedSecondaryDiagonal = { -5, -1.52 };

    @Test
    public void testMatricesValues3() {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetricMatrix3x3));

        // Q must match the known reference matrix.
        RealMatrix q = transformer.getQ();
        RealMatrix qReference = MatrixUtils.createRealMatrix(expectedQ);
        Assert.assertEquals(0, q.subtract(qReference).getNorm(), 1.0e-14);

        // T must match a tri-diagonal matrix built from the expected diagonals.
        RealMatrix t = transformer.getT();
        RealMatrix tReference = MatrixUtils.createRealMatrix(
            buildTriDiagonalMatrix(expectedMainDiagonal, expectedSecondaryDiagonal));
        Assert.assertEquals(0, t.subtract(tReference).getNorm(), 1.0e-14);

        // Repeated accessor calls must return the same cached instances.
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    /**
     * Builds a tri-diagonal matrix from its main and secondary diagonals.
     *
     * @param mainDiagonal      values along the main diagonal
     * @param secondaryDiagonal values along the diagonals immediately above and below the main one
     * @return the assembled square tri-diagonal matrix as a 2D array
     */
    private double[][] buildTriDiagonalMatrix(double[] mainDiagonal, double[] secondaryDiagonal) {
        double[][] tData = new double[mainDiagonal.length][mainDiagonal.length];
        for (int i = 0; i < mainDiagonal.length; ++i) {
            tData[i][i] = mainDiagonal[i];
            if (i > 0) {
                tData[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                tData[i][i + 1] = secondaryDiagonal[i];
            }
        }
        return tData;
    }
}
