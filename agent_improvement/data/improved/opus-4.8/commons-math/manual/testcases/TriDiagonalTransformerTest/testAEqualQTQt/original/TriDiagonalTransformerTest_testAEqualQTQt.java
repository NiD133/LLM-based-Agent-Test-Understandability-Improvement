package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.Assert;

public class TriDiagonalTransformerTest_testAEqualQTQt {

    private double[][] testSquare5 = { { 1, 2, 3, 1, 1 }, { 2, 1, 1, 3, 1 }, { 3, 1, 1, 1, 2 }, { 1, 3, 1, 2, 1 }, { 1, 1, 2, 1, 3 } };

    private double[][] testSquare3 = { { 1, 3, 4 }, { 3, 2, 2 }, { 4, 2, 0 } };

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

    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTm = m.transpose().multiply(m);
        RealMatrix id = MatrixUtils.createRealIdentityMatrix(mTm.getRowDimension());
        Assert.assertEquals(0, mTm.subtract(id).getNorm(), 1.0e-15);
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

    @Test
    public void testAEqualQTQt() {
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare5));
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare3));
    }
}
