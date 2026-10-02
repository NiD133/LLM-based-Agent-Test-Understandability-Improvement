package org.apache.commons.math4.legacy.linear;

import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testMatricesValues3 {

    private static final double MATRIX_NORM_TOLERANCE = 1.0e-14;

    private final double[][] testSquare3 = {
        { 1, 3, 4 },
        { 3, 2, 2 },
        { 4, 2, 0 }
    };

    private final double[][] expectedQ = {
        { 1.0,  0.0,  0.0 },
        { 0.0, -0.6,  0.8 },
        { 0.0, -0.8, -0.6 }
    };

    private final double[] expectedMainDiagonal = {
        1,
        2.64,
        -0.64
    };

    private final double[] expectedSecondaryDiagonal = {
        -5,
        -1.52
    };

    private void checkMatricesValues(double[][] matrix,
                                     double[][] qRef,
                                     double[] mainDiagnonal,
                                     double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0,
                            q.subtract(MatrixUtils.createRealMatrix(qRef)).getNorm(),
                            MATRIX_NORM_TOLERANCE);

        RealMatrix t = transformer.getT();
        double[][] expectedTData = createTriDiagonalData(mainDiagnonal, secondaryDiagonal);
        Assert.assertEquals(0,
                            t.subtract(MatrixUtils.createRealMatrix(expectedTData)).getNorm(),
                            MATRIX_NORM_TOLERANCE);

        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    private double[][] createTriDiagonalData(double[] mainDiagnonal,
                                             double[] secondaryDiagonal) {
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
        return tData;
    }

    @Test
    public void testMatricesValues3() {
        checkMatricesValues(testSquare3,
                            expectedQ,
                            expectedMainDiagonal,
                            expectedSecondaryDiagonal);
    }
}
