package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} rejects non-square matrices,
 * since tridiagonalization is only defined for square (symmetric) matrices.
 */
public class TriDiagonalTransformerTest_testNonSquare {

    @Test
    public void testNonSquare() {
        // A 3x2 matrix is not square, so constructing the transformer must fail.
        RealMatrix nonSquareMatrix = MatrixUtils.createRealMatrix(new double[3][2]);

        try {
            new TriDiagonalTransformer(nonSquareMatrix);
            Assert.fail("expected NonSquareMatrixException for a non-square matrix");
        } catch (NonSquareMatrixException expected) {
            // expected: the transformer only accepts square matrices
        }
    }
}
