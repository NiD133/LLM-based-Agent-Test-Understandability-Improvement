package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} reduces a symmetric matrix to a
 * tridiagonal matrix T, i.e. T has non-zero entries only on the main diagonal
 * and the two diagonals immediately adjacent to it.
 */
public class TriDiagonalTransformerTest_testTTriDiagonal {

    /** A 5x5 symmetric matrix used as transformation input. */
    private final double[][] symmetric5x5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    /** A 3x3 symmetric matrix used as transformation input. */
    private final double[][] symmetric3x3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /**
     * Asserts that every entry of {@code matrix} that lies outside the tridiagonal
     * band (i.e. more than one column away from the main diagonal) is zero.
     */
    private void assertTriDiagonal(RealMatrix matrix) {
        final int rows = matrix.getRowDimension();
        final int cols = matrix.getColumnDimension();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                final boolean outsideTriDiagonalBand = row < col - 1 || row > col + 1;
                if (outsideTriDiagonalBand) {
                    Assert.assertEquals(0, matrix.getEntry(row, col), 1.0e-16);
                }
            }
        }
    }

    /** The matrix T produced by transforming {@code data} must be tridiagonal. */
    private RealMatrix tridiagonalize(double[][] data) {
        RealMatrix input = MatrixUtils.createRealMatrix(data);
        return new TriDiagonalTransformer(input).getT();
    }

    @Test
    public void testTTriDiagonal() {
        assertTriDiagonal(tridiagonalize(symmetric5x5));
        assertTriDiagonal(tridiagonalize(symmetric3x3));
    }
}
