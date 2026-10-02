package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;

/**
 * Tests that {@link TriDiagonalTransformer} rejects non-square input matrices.
 *
 * <p>The Householder tridiagonalization algorithm is only defined for symmetric
 * (hence square) matrices.  Passing a non-square matrix is a programming error
 * and should be caught immediately with a descriptive exception.</p>
 */
public class TriDiagonalTransformerTest_testNonSquare {

    /**
     * Verifies that constructing a {@link TriDiagonalTransformer} with a
     * non-square matrix throws {@link NonSquareMatrixException}.
     *
     * <p>A 3×2 matrix is the smallest non-square case that exercises the
     * dimension check without any other complicating structure.</p>
     */
    @Test
    public void testNonSquare() {
        // A 3-row, 2-column matrix is non-square and must be rejected.
        double[][] nonSquareData = new double[3][2];
        RealMatrix nonSquareMatrix = MatrixUtils.createRealMatrix(nonSquareData);

        try {
            new TriDiagonalTransformer(nonSquareMatrix);
            Assert.fail("Expected NonSquareMatrixException was not thrown");
        } catch (NonSquareMatrixException expected) {
            // Construction must fail: the transformer requires a symmetric
            // (square) matrix, so reaching this catch block is the correct behaviour.
        }
    }
}
