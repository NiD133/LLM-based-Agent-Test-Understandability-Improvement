package org.apache.commons.math4.legacy.linear;

import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testMatricesValues3 {

    private static final double MATRIX_ASSERTION_TOLERANCE = 1.0e-14;

    private static final double[][] THREE_BY_THREE_SYMMETRIC_MATRIX = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    private static final double[][] EXPECTED_Q_MATRIX = {
        { 1.0, 0.0, 0.0 },
        { 0.0, -0.6, 0.8 },
        { 0.0, -0.8, -0.6 }
    };

    private static final double[] EXPECTED_MAIN_DIAGONAL = { 1, 2.64, -0.64 };

    private static final double[] EXPECTED_SECONDARY_DIAGONAL = { -5, -1.52 };

    @Test
    public void testMatricesValues3() {
        checkMatricesValues(
            THREE_BY_THREE_SYMMETRIC_MATRIX,
            EXPECTED_Q_MATRIX,
            EXPECTED_MAIN_DIAGONAL,
            EXPECTED_SECONDARY_DIAGONAL);
    }

    private void checkMatricesValues(double[][] matrix,
                                     double[][] expectedQData,
                                     double[] expectedMainDiagonal,
                                     double[] expectedSecondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        RealMatrix q = transformer.getQ();
        RealMatrix expectedQ = MatrixUtils.createRealMatrix(expectedQData);
        Assert.assertEquals(0, q.subtract(expectedQ).getNorm(), MATRIX_ASSERTION_TOLERANCE);

        RealMatrix t = transformer.getT();
        RealMatrix expectedT = createTriDiagonalMatrix(expectedMainDiagonal, expectedSecondaryDiagonal);
        Assert.assertEquals(0, t.subtract(expectedT).getNorm(), MATRIX_ASSERTION_TOLERANCE);

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
}
