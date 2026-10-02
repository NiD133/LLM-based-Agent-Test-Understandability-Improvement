package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.Assert;

/**
 * Tests that the orthogonal matrix Q produced by TriDiagonalTransformer
 * satisfies Q^T * Q = I (i.e. Q is truly orthogonal).
 */
public class TriDiagonalTransformerTest_testQOrthogonal {

    // A 5x5 symmetric matrix used as a representative larger test case
    private double[][] testSquare5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    // A 3x3 symmetric matrix used as a minimal test case
    private double[][] testSquare3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    private void checkAEqualQTQt(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();
        double norm = q.multiply(t).multiply(qT).subtract(matrix).getNorm();
        Assert.assertEquals(0, norm, 4.0e-15);
    }

    private void checkNoAccessBelowDiagonal(double[][] data) {
        double[][] modifiedData = new double[data.length][];
        for (int i = 0; i < data.length; ++i) {
            modifiedData[i] = data[i].clone();
            Arrays.fill(modifiedData[i], 0, i, Double.NaN);
        }
        RealMatrix matrix = MatrixUtils.createRealMatrix(modifiedData);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();
        double norm = q.multiply(t).multiply(qT).subtract(MatrixUtils.createRealMatrix(data)).getNorm();
        Assert.assertEquals(0, norm, 4.0e-15);
    }

    /**
     * Asserts that m is orthogonal by checking that m^T * m equals the identity matrix.
     * Orthogonality tolerance is 1e-15.
     */
    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTransposeTimesM = m.transpose().multiply(m);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(mTransposeTimesM.getRowDimension());
        Assert.assertEquals(0, mTransposeTimesM.subtract(identity).getNorm(), 1.0e-15);
    }

    private void checkTriDiagonal(RealMatrix m) {
        final int rows = m.getRowDimension();
        final int cols = m.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (i < j - 1 || i > j + 1) {
                    Assert.assertEquals(0, m.getEntry(i, j), 1.0e-16);
                }
            }
        }
    }

    private void checkMatricesValues(double[][] matrix, double[][] qRef, double[] mainDiagnonal, double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));
        // check values against known references
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0, q.subtract(MatrixUtils.createRealMatrix(qRef)).getNorm(), 1.0e-14);
        RealMatrix t = transformer.getT();
        double[][] tData = new double[mainDiagnonal.length][mainDiagnonal.length];
        for (int i = 0; i < mainDiagnonal.length; ++i) {
            tData[i][i] = mainDiagnonal[i];
            if (i > 0) {
                tData[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                tData[i][i + 1] = secondaryDiagonal[i];
            }
        }
        Assert.assertEquals(0, t.subtract(MatrixUtils.createRealMatrix(tData)).getNorm(), 1.0e-14);
        // check the same cached instance is returned the second time
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    /**
     * Verifies that the Q matrix from TriDiagonalTransformer is orthogonal
     * for both the 5x5 and 3x3 symmetric input matrices.
     */
    @Test
    public void testQOrthogonal() {
        RealMatrix matrix5x5 = MatrixUtils.createRealMatrix(testSquare5);
        RealMatrix q5x5 = new TriDiagonalTransformer(matrix5x5).getQ();
        checkOrthogonal(q5x5);

        RealMatrix matrix3x3 = MatrixUtils.createRealMatrix(testSquare3);
        RealMatrix q3x3 = new TriDiagonalTransformer(matrix3x3).getQ();
        checkOrthogonal(q3x3);
    }
}
