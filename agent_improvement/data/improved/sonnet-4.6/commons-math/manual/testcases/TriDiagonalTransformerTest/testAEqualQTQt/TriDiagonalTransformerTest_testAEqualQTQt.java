package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.Assert;

/**
 * Tests that a symmetric matrix A can be reconstructed exactly from its
 * tridiagonal decomposition: A = Q * T * Q^T, where Q is orthogonal and
 * T is tridiagonal.
 */
public class TriDiagonalTransformerTest_testAEqualQTQt {

    // Tolerance for verifying that the reconstruction A = Q*T*Q^T matches A
    private static final double RECONSTRUCTION_TOLERANCE = 4.0e-15;

    // Tolerance for verifying Q^T * Q = I (orthogonality)
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;

    // Tolerance for verifying off-tridiagonal entries are zero
    private static final double TRIDIAGONAL_ZERO_TOLERANCE = 1.0e-16;

    // Tolerance for verifying Q and T against known reference values
    private static final double REFERENCE_VALUE_TOLERANCE = 1.0e-14;

    // 5x5 symmetric matrix used as one of the test inputs
    private double[][] testSquare5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    // 3x3 symmetric matrix used as one of the test inputs
    private double[][] testSquare3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /**
     * Verifies that A = Q * T * Q^T holds within numerical tolerance.
     * This is the core property of the tridiagonal decomposition.
     */
    private void checkAEqualQTQt(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();

        // Reconstruct A from the decomposition and compare to original
        RealMatrix reconstructed = q.multiply(t).multiply(qT);
        double reconstructionError = reconstructed.subtract(matrix).getNorm();
        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }

    /**
     * Verifies that the transformer only reads the upper triangle of the input
     * matrix (i.e., it never accesses entries below the main diagonal).
     *
     * The lower triangle is filled with NaN; if those values were read, the
     * result would be corrupted and the reconstruction check would fail.
     */
    private void checkNoAccessBelowDiagonal(double[][] data) {
        // Replace the lower triangle with NaN to detect any illegal reads
        double[][] upperTriangleOnly = new double[data.length][];
        for (int i = 0; i < data.length; ++i) {
            upperTriangleOnly[i] = data[i].clone();
            Arrays.fill(upperTriangleOnly[i], 0, i, Double.NaN);
        }

        RealMatrix poisonedMatrix = MatrixUtils.createRealMatrix(upperTriangleOnly);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(poisonedMatrix);
        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();

        // Reconstruct against the original (non-poisoned) matrix
        RealMatrix originalMatrix = MatrixUtils.createRealMatrix(data);
        double reconstructionError = q.multiply(t).multiply(qT).subtract(originalMatrix).getNorm();
        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }

    /**
     * Verifies that the given matrix is orthogonal: M^T * M = I.
     */
    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTransposeTimesM = m.transpose().multiply(m);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(mTransposeTimesM.getRowDimension());
        Assert.assertEquals(0, mTransposeTimesM.subtract(identity).getNorm(), ORTHOGONALITY_TOLERANCE);
    }

    /**
     * Verifies that the given matrix is tridiagonal: all entries with
     * |row - col| > 1 must be zero.
     */
    private void checkTriDiagonal(RealMatrix m) {
        final int rows = m.getRowDimension();
        final int cols = m.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                boolean outsideTridiagonalBand = (i < j - 1 || i > j + 1);
                if (outsideTridiagonalBand) {
                    Assert.assertEquals(0, m.getEntry(i, j), TRIDIAGONAL_ZERO_TOLERANCE);
                }
            }
        }
    }

    /**
     * Verifies Q and T against known reference values, and confirms that
     * repeated calls return the same cached matrix instances.
     */
    private void checkMatricesValues(double[][] matrix, double[][] qRef,
                                     double[] mainDiagonal, double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        // Verify Q matches the reference
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0, q.subtract(MatrixUtils.createRealMatrix(qRef)).getNorm(),
                            REFERENCE_VALUE_TOLERANCE);

        // Build the expected tridiagonal matrix T from main and secondary diagonals
        int size = mainDiagonal.length;
        double[][] tData = new double[size][size];
        for (int i = 0; i < size; ++i) {
            tData[i][i] = mainDiagonal[i];
            if (i > 0) {
                tData[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                tData[i][i + 1] = secondaryDiagonal[i];
            }
        }

        // Verify T matches the reference
        RealMatrix t = transformer.getT();
        Assert.assertEquals(0, t.subtract(MatrixUtils.createRealMatrix(tData)).getNorm(),
                            REFERENCE_VALUE_TOLERANCE);

        // Verify that Q and T are cached (same instance returned on second call)
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    /**
     * Tests that the tridiagonal decomposition satisfies A = Q * T * Q^T
     * for both a 3x3 and a 5x5 symmetric matrix.
     */
    @Test
    public void testAEqualQTQt() {
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare5));
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare3));
    }
}
