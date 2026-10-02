package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testMatricesValues3 {

    private final double[][] testSquare5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    private final double[][] testSquare3 = {
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

    private void checkOrthogonal(RealMatrix matrix) {
        RealMatrix product = matrix.transpose().multiply(matrix);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(product.getRowDimension());

        Assert.assertEquals(0, product.subtract(identity).getNorm(), 1.0e-15);
    }

    private void checkTriDiagonal(RealMatrix matrix) {
        final int rows = matrix.getRowDimension();
        final int columns = matrix.getColumnDimension();

        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (row < column - 1 || row > column + 1) {
                    Assert.assertEquals(0, matrix.getEntry(row, column), 1.0e-16);
                }
            }
        }
    }

    private void checkMatricesValues(double[][] matrix,
                                     double[][] expectedQData,
                                     double[] mainDiagonal,
                                     double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        RealMatrix q = transformer.getQ();
        RealMatrix expectedQ = MatrixUtils.createRealMatrix(expectedQData);
        Assert.assertEquals(0, q.subtract(expectedQ).getNorm(), 1.0e-14);

        RealMatrix t = transformer.getT();
        RealMatrix expectedT = createTriDiagonalMatrix(mainDiagonal, secondaryDiagonal);
        Assert.assertEquals(0, t.subtract(expectedT).getNorm(), 1.0e-14);

        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    private RealMatrix createTriDiagonalMatrix(double[] mainDiagonal, double[] secondaryDiagonal) {
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

        return MatrixUtils.createRealMatrix(data);
    }

    @Test
    public void testMatricesValues3() {
        checkMatricesValues(
            testSquare3,
            new double[][] {
                { 1.0, 0.0, 0.0 },
                { 0.0, -0.6, 0.8 },
                { 0.0, -0.8, -0.6 }
            },
            new double[] { 1, 2.64, -0.64 },
            new double[] { -5, -1.52 });
    }
}
