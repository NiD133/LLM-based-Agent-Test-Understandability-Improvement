package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testMatricesValues5 {

    private static final double MATRIX_NORM_TOLERANCE = 4.0e-15;
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;
    private static final double TRIDIAGONAL_ZERO_TOLERANCE = 1.0e-16;
    private static final double REFERENCE_VALUE_TOLERANCE = 1.0e-14;

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

    private static final double[][] EXPECTED_Q_FOR_SQUARE5 = {
        { 1.0, 0.0, 0.0, 0.0, 0.0 },
        { 0.0, -0.5163977794943222, 0.016748280772542083, 0.839800693771262, 0.16669620021405473 },
        { 0.0, -0.7745966692414833, -0.4354553000860955, -0.44989322880603355, -0.08930153582895772 },
        { 0.0, -0.2581988897471611, 0.6364346693566014, -0.30263204032131164, 0.6608313651342882 },
        { 0.0, -0.2581988897471611, 0.6364346693566009, -0.027289660803112598, -0.7263191580755246 }
    };

    private static final double[] EXPECTED_MAIN_DIAGONAL_FOR_SQUARE5 = {
        1,
        4.4,
        1.433099579242636,
        -0.89537362758743,
        2.062274048344794
    };

    private static final double[] EXPECTED_SECONDARY_DIAGONAL_FOR_SQUARE5 = {
        -JdkMath.sqrt(15),
        -3.0832882879592476,
        0.6082710842351517,
        1.1786086405912128
    };

    private void checkAEqualQTQt(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();
        double norm = q.multiply(t).multiply(qT).subtract(matrix).getNorm();
        Assert.assertEquals(0, norm, MATRIX_NORM_TOLERANCE);
    }

    private void checkNoAccessBelowDiagonal(double[][] data) {
        double[][] modifiedData = new double[data.length][];
        for (int row = 0; row < data.length; ++row) {
            modifiedData[row] = data[row].clone();
            Arrays.fill(modifiedData[row], 0, row, Double.NaN);
        }
        RealMatrix matrix = MatrixUtils.createRealMatrix(modifiedData);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();
        double norm = q.multiply(t).multiply(qT).subtract(MatrixUtils.createRealMatrix(data)).getNorm();
        Assert.assertEquals(0, norm, MATRIX_NORM_TOLERANCE);
    }

    private void checkOrthogonal(RealMatrix matrix) {
        RealMatrix productWithTranspose = matrix.transpose().multiply(matrix);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(productWithTranspose.getRowDimension());
        Assert.assertEquals(0, productWithTranspose.subtract(identity).getNorm(), ORTHOGONALITY_TOLERANCE);
    }

    private void checkTriDiagonal(RealMatrix matrix) {
        final int rows = matrix.getRowDimension();
        final int columns = matrix.getColumnDimension();
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (row < column - 1 || row > column + 1) {
                    Assert.assertEquals(0, matrix.getEntry(row, column), TRIDIAGONAL_ZERO_TOLERANCE);
                }
            }
        }
    }

    private void checkMatricesValues(double[][] matrix,
                                     double[][] expectedQ,
                                     double[] expectedMainDiagonal,
                                     double[] expectedSecondaryDiagonal) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0,
                            q.subtract(MatrixUtils.createRealMatrix(expectedQ)).getNorm(),
                            REFERENCE_VALUE_TOLERANCE);

        RealMatrix t = transformer.getT();
        RealMatrix expectedT = createTridiagonalMatrix(expectedMainDiagonal, expectedSecondaryDiagonal);
        Assert.assertEquals(0,
                            t.subtract(expectedT).getNorm(),
                            REFERENCE_VALUE_TOLERANCE);

        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    private RealMatrix createTridiagonalMatrix(double[] mainDiagonal, double[] secondaryDiagonal) {
        double[][] tridiagonalData = new double[mainDiagonal.length][mainDiagonal.length];
        for (int index = 0; index < mainDiagonal.length; ++index) {
            tridiagonalData[index][index] = mainDiagonal[index];
            if (index > 0) {
                tridiagonalData[index][index - 1] = secondaryDiagonal[index - 1];
            }
            if (index < secondaryDiagonal.length) {
                tridiagonalData[index][index + 1] = secondaryDiagonal[index];
            }
        }
        return MatrixUtils.createRealMatrix(tridiagonalData);
    }

    @Test
    public void testMatricesValues5() {
        checkMatricesValues(testSquare5,
                            EXPECTED_Q_FOR_SQUARE5,
                            EXPECTED_MAIN_DIAGONAL_FOR_SQUARE5,
                            EXPECTED_SECONDARY_DIAGONAL_FOR_SQUARE5);
    }
}
