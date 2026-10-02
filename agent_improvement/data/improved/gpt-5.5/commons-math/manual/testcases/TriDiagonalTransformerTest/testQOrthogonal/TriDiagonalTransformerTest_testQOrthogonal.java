package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testQOrthogonal {

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

        double norm = q.multiply(t).multiply(qT)
            .subtract(MatrixUtils.createRealMatrix(data))
            .getNorm();
        Assert.assertEquals(0, norm, 4.0e-15);
    }

    private void checkOrthogonal(RealMatrix matrix) {
        RealMatrix matrixTransposedTimesMatrix = matrix.transpose().multiply(matrix);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(
            matrixTransposedTimesMatrix.getRowDimension());

        Assert.assertEquals(0, matrixTransposedTimesMatrix.subtract(identity).getNorm(), 1.0e-15);
    }

    private void checkTriDiagonal(RealMatrix matrix) {
        final int rows = matrix.getRowDimension();
        final int cols = matrix.getColumnDimension();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (i < j - 1 || i > j + 1) {
                    Assert.assertEquals(0, matrix.getEntry(i, j), 1.0e-16);
                }
            }
        }
    }

    private void checkMatricesValues(double[][] matrix,
                                     double[][] qRef,
                                     double[] mainDiagonal,
                                     double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0, q.subtract(MatrixUtils.createRealMatrix(qRef)).getNorm(), 1.0e-14);

        RealMatrix t = transformer.getT();
        double[][] expectedT = new double[mainDiagonal.length][mainDiagonal.length];
        for (int i = 0; i < mainDiagonal.length; ++i) {
            expectedT[i][i] = mainDiagonal[i];
            if (i > 0) {
                expectedT[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                expectedT[i][i + 1] = secondaryDiagonal[i];
            }
        }
        Assert.assertEquals(0, t.subtract(MatrixUtils.createRealMatrix(expectedT)).getNorm(), 1.0e-14);

        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    @Test
    public void testQOrthogonal() {
        RealMatrix square5Q =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getQ();
        RealMatrix square3Q =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getQ();

        checkOrthogonal(square5Q);
        checkOrthogonal(square3Q);
    }
}
