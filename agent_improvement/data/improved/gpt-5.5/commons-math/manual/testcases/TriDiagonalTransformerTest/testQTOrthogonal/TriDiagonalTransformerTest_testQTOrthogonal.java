package org.apache.commons.math4.legacy.linear;

import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testQTOrthogonal {

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

    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTm = m.transpose().multiply(m);
        RealMatrix id = MatrixUtils.createRealIdentityMatrix(mTm.getRowDimension());

        Assert.assertEquals(0, mTm.subtract(id).getNorm(), 1.0e-15);
    }

    @Test
    public void testQTOrthogonal() {
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getQT());
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getQT());
    }
}
