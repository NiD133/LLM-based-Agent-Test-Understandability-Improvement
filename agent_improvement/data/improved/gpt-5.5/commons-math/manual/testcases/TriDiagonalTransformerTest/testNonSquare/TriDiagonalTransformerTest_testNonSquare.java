package org.apache.commons.math4.legacy.linear;

import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest_testNonSquare {

    private static final int NON_SQUARE_ROWS = 3;
    private static final int NON_SQUARE_COLUMNS = 2;

    @Test
    public void testNonSquare() {
        try {
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(new double[NON_SQUARE_ROWS][NON_SQUARE_COLUMNS]));
            Assert.fail("an exception should have been thrown");
        } catch (NonSquareMatrixException expected) {
            // expected behavior
        }
    }
}
