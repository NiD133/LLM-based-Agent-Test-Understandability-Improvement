package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that TriDiagonalTransformer only reads the upper triangle (diagonal +
 * entries above it) of a symmetric matrix and never accesses elements below the
 * main diagonal.
 *
 * <p>This property matters because symmetric matrices are often stored in
 * upper-triangular form to save space; the transformer must produce the same
 * decomposition Q·T·Q^T = A regardless of whether the lower-triangular entries
 * are filled in.
 */
public class TriDiagonalTransformerTest_testNoAccessBelowDiagonal {

    // A 5×5 symmetric test matrix.
    private static final double[][] SYMMETRIC_5X5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    // A 3×3 symmetric test matrix.
    private static final double[][] SYMMETRIC_3X3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    /**
     * Confirms that the transformer produces a valid decomposition Q·T·Q^T = A
     * even when every element strictly below the main diagonal is replaced with
     * NaN.  A correct implementation reads only the upper triangle; any access
     * to the lower triangle would propagate NaN and cause the norm check to fail.
     */
    @Test
    public void testNoAccessBelowDiagonal() {
        checkNoAccessBelowDiagonal(SYMMETRIC_5X5);
        checkNoAccessBelowDiagonal(SYMMETRIC_3X3);
    }

    /**
     * Poisons the lower-triangular entries of {@code originalData} with NaN,
     * runs the transformer on the poisoned copy, and asserts that the resulting
     * decomposition Q·T·Q^T still matches the original matrix within tolerance.
     */
    private void checkNoAccessBelowDiagonal(double[][] originalData) {
        // Build a copy whose strictly-lower-triangular entries are set to NaN.
        // Any transformer code that reads those entries will produce NaN results,
        // causing the norm check below to fail with a value of NaN (not 0).
        double[][] upperTriangleOnly = buildUpperTriangleCopy(originalData);

        RealMatrix poisonedMatrix = MatrixUtils.createRealMatrix(upperTriangleOnly);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(poisonedMatrix);

        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();

        // Reconstruct A from the decomposition and compare against the original.
        RealMatrix originalMatrix   = MatrixUtils.createRealMatrix(originalData);
        RealMatrix reconstructed    = q.multiply(t).multiply(qT);
        double reconstructionError  = reconstructed.subtract(originalMatrix).getNorm();

        Assert.assertEquals(
            "Q·T·Q^T must equal the original matrix (transformer must not read below diagonal)",
            0, reconstructionError, 4.0e-15);
    }

    /**
     * Returns a deep copy of {@code data} where every element at row {@code i},
     * column {@code j} with {@code j < i} (strictly below the main diagonal) is
     * replaced with {@link Double#NaN}.
     */
    private double[][] buildUpperTriangleCopy(double[][] data) {
        double[][] copy = new double[data.length][];
        for (int row = 0; row < data.length; row++) {
            copy[row] = data[row].clone();
            // Overwrite columns 0 .. row-1 with NaN so any accidental read is detectable.
            Arrays.fill(copy[row], 0, row, Double.NaN);
        }
        return copy;
    }
}
