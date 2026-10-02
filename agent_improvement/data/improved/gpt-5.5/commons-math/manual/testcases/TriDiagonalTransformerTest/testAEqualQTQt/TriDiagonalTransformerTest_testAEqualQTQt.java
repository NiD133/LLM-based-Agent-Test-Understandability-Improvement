package org.apache.commons.math4.legacy.linear;

import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testAEqualQTQt {

    private static final double RECONSTRUCTION_TOLERANCE = 4.0e-15;

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

        RealMatrix reconstructedMatrix = q.multiply(t).multiply(qT);
        double reconstructionError = reconstructedMatrix.subtract(matrix).getNorm();

        Assert.assertEquals(0, reconstructionError, RECONSTRUCTION_TOLERANCE);
    }

    @Test
    public void testAEqualQTQt() {
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare5));
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare3));
    }
}
