package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testNoAccessBelowDiagonal {

    private static final double RECONSTRUCTION_TOLERANCE = 4.0e-15;
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;
    private static final double TRIDIAGONAL_TOLERANCE = 1.0e-16;
    private static final double REFERENCE_VALUE_TOLERANCE = 1.0e-14;

    private double[][] testSquare5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

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
        double reconstructionError = q.multiply(t).multiply(qT).subtract(matrix).getNorm();
        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }

    private void checkNoAccessBelowDiagonal(double[][] data) {
        double[][] matrixDataWithNaNsBelowDiagonal = new double[data.length][];
        for (int row = 0; row < data.length; ++row) {
            matrixDataWithNaNsBelowDiagonal[row] = data[row].clone();
            Arrays.fill(matrixDataWithNaNsBelowDiagonal[row], 0, row, Double.NaN);
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixDataWithNaNsBelowDiagonal);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();
        RealMatrix expectedMatrix = MatrixUtils.createRealMatrix(data);
        double reconstructionError = q.multiply(t).multiply(qT).subtract(expectedMatrix).getNorm();
        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }

    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTm = m.transpose().multiply(m);
        RealMatrix id = MatrixUtils.createRealIdentityMatrix(mTm.getRowDimension());
        Assert.assertEquals(0, mTm.subtract(id).getNorm(), ORTHOGONALITY_TOLERANCE);
    }

    private void checkTriDiagonal(RealMatrix m) {
        final int rows = m.getRowDimension();
        final int cols = m.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (i < j - 1 || i > j + 1) {
                    Assert.assertEquals(0, m.getEntry(i, j), TRIDIAGONAL_TOLERANCE);
                }
            }
        }
    }

    private void checkMatricesValues(double[][] matrix, double[][] qRef, double[] mainDiagnonal, double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0, q.subtract(MatrixUtils.createRealMatrix(qRef)).getNorm(), REFERENCE_VALUE_TOLERANCE);

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
        Assert.assertEquals(0, t.subtract(MatrixUtils.createRealMatrix(tData)).getNorm(), REFERENCE_VALUE_TOLERANCE);

        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    @Test
    public void testNoAccessBelowDiagonal() {
        checkNoAccessBelowDiagonal(testSquare5);
        checkNoAccessBelowDiagonal(testSquare3);
    }
}
