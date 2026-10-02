/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.Assert;

/**
 * Tests for {@link TriDiagonalTransformer}, which decomposes a symmetric matrix A into
 * A = Q * T * Q^T, where Q is orthogonal and T is tridiagonal.
 */
public class TriDiagonalTransformerTest {

    // Numeric tolerance for floating-point comparisons in matrix norm checks
    private static final double DECOMPOSITION_TOLERANCE = 4.0e-15;
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;
    private static final double REFERENCE_VALUES_TOLERANCE = 1.0e-14;
    private static final double TRIDIAGONAL_ZERO_TOLERANCE = 1.0e-16;

    /**
     * A 5x5 symmetric matrix used as a larger test case.
     */
    private double[][] testSquare5 = {
            { 1, 2, 3, 1, 1 },
            { 2, 1, 1, 3, 1 },
            { 3, 1, 1, 1, 2 },
            { 1, 3, 1, 2, 1 },
            { 1, 1, 2, 1, 3 }
    };

    /**
     * A 3x3 symmetric matrix used as a smaller test case.
     */
    private double[][] testSquare3 = {
            { 1, 3, 4 },
            { 3, 2, 2 },
            { 4, 2, 0 }
    };

    /**
     * Verifies that constructing a transformer from a non-square matrix throws
     * {@link NonSquareMatrixException}.
     */
    @Test
    public void testNonSquare() {
        try {
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(new double[3][2]));
            Assert.fail("an exception should have been thrown");
        } catch (NonSquareMatrixException ime) {
            // expected behavior
        }
    }

    /**
     * Verifies the fundamental decomposition property A = Q * T * Q^T for both test matrices.
     */
    @Test
    public void testAEqualQTQt() {
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare5));
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare3));
    }

    /**
     * Confirms that Q * T * Q^T reconstructs the original matrix A within numerical tolerance.
     */
    private void checkAEqualQTQt(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();
        double reconstructionError = q.multiply(t).multiply(qT).subtract(matrix).getNorm();
        Assert.assertEquals(0, reconstructionError, DECOMPOSITION_TOLERANCE);
    }

    /**
     * Verifies that the transformer only reads the upper-triangular part of the symmetric matrix
     * (i.e., entries below the diagonal are never accessed).
     */
    @Test
    public void testNoAccessBelowDiagonal() {
        checkNoAccessBelowDiagonal(testSquare5);
        checkNoAccessBelowDiagonal(testSquare3);
    }

    /**
     * Fills entries below the diagonal with NaN, then checks that A = Q * T * Q^T still holds.
     * A correct implementation only reads the upper triangle of a symmetric matrix.
     */
    private void checkNoAccessBelowDiagonal(double[][] data) {
        double[][] modifiedData = new double[data.length][];
        for (int i = 0; i < data.length; ++i) {
            modifiedData[i] = data[i].clone();
            Arrays.fill(modifiedData[i], 0, i, Double.NaN);
        }
        RealMatrix matrixWithNaNsBelowDiagonal = MatrixUtils.createRealMatrix(modifiedData);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrixWithNaNsBelowDiagonal);
        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();
        double reconstructionError = q.multiply(t).multiply(qT).subtract(MatrixUtils.createRealMatrix(data)).getNorm();
        Assert.assertEquals(0, reconstructionError, DECOMPOSITION_TOLERANCE);
    }

    /**
     * Verifies that the Q matrix from the decomposition is orthogonal (Q^T * Q = I).
     */
    @Test
    public void testQOrthogonal() {
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getQ());
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getQ());
    }

    /**
     * Verifies that the Q^T matrix from the decomposition is orthogonal (Q * Q^T = I).
     */
    @Test
    public void testQTOrthogonal() {
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getQT());
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getQT());
    }

    /**
     * Asserts that m^T * m equals the identity matrix, confirming orthogonality.
     */
    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTm = m.transpose().multiply(m);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(mTm.getRowDimension());
        Assert.assertEquals(0, mTm.subtract(identity).getNorm(), ORTHOGONALITY_TOLERANCE);
    }

    /**
     * Verifies that the T matrix produced by the decomposition is truly tridiagonal
     * (all entries outside the main diagonal and its two adjacent diagonals must be zero).
     */
    @Test
    public void testTTriDiagonal() {
        checkTriDiagonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getT());
        checkTriDiagonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getT());
    }

    /**
     * Asserts that all off-tridiagonal entries in {@code m} are zero.
     * An entry at (i, j) is off-tridiagonal when |i - j| > 1.
     */
    private void checkTriDiagonal(RealMatrix m) {
        final int rows = m.getRowDimension();
        final int cols = m.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (i < j - 1 || i > j + 1) {
                    Assert.assertEquals(0, m.getEntry(i, j), TRIDIAGONAL_ZERO_TOLERANCE);
                }
            }
        }
    }

    /**
     * Verifies decomposition of the 5x5 test matrix against known reference values
     * for Q, the main diagonal of T, and the secondary diagonal of T.
     */
    @Test
    public void testMatricesValues5() {
        checkMatricesValues(testSquare5,
                            new double[][] {
                                { 1.0,  0.0,                 0.0,                  0.0,                   0.0 },
                                { 0.0, -0.5163977794943222,  0.016748280772542083, 0.839800693771262,     0.16669620021405473 },
                                { 0.0, -0.7745966692414833, -0.4354553000860955,  -0.44989322880603355,  -0.08930153582895772 },
                                { 0.0, -0.2581988897471611,  0.6364346693566014,  -0.30263204032131164,   0.6608313651342882 },
                                { 0.0, -0.2581988897471611,  0.6364346693566009,  -0.027289660803112598, -0.7263191580755246 }
                            },
                            new double[] { 1, 4.4, 1.433099579242636, -0.89537362758743, 2.062274048344794 },
                            new double[] { -JdkMath.sqrt(15), -3.0832882879592476, 0.6082710842351517, 1.1786086405912128 });
    }

    /**
     * Verifies decomposition of the 3x3 test matrix against known reference values
     * for Q, the main diagonal of T, and the secondary diagonal of T.
     */
    @Test
    public void testMatricesValues3() {
        checkMatricesValues(testSquare3,
                            new double[][] {
                                {  1.0,  0.0,  0.0 },
                                {  0.0, -0.6,  0.8 },
                                {  0.0, -0.8, -0.6 },
                            },
                            new double[] { 1, 2.64, -0.64 },
                            new double[] { -5, -1.52 });
    }

    /**
     * Checks that the transformer produces the expected Q matrix and T matrix
     * (given via its main diagonal and secondary diagonal), and that Q and T
     * are cached (the same instance is returned on repeated calls).
     *
     * @param matrix          raw input matrix data
     * @param qRef            expected Q matrix entries
     * @param mainDiagonal    expected main diagonal of T
     * @param secondaryDiagonal expected secondary diagonal of T (length = n - 1)
     */
    private void checkMatricesValues(double[][] matrix, double[][] qRef,
                                     double[] mainDiagonal,
                                     double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        // Verify Q matches the known reference matrix
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0, q.subtract(MatrixUtils.createRealMatrix(qRef)).getNorm(), REFERENCE_VALUES_TOLERANCE);

        // Build the expected T from its main and secondary diagonals, then verify
        RealMatrix t = transformer.getT();
        double[][] tData = new double[mainDiagonal.length][mainDiagonal.length];
        for (int i = 0; i < mainDiagonal.length; ++i) {
            tData[i][i] = mainDiagonal[i];
            if (i > 0) {
                tData[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                tData[i][i + 1] = secondaryDiagonal[i];
            }
        }
        Assert.assertEquals(0, t.subtract(MatrixUtils.createRealMatrix(tData)).getNorm(), REFERENCE_VALUES_TOLERANCE);

        // Verify that Q and T are cached: repeated calls must return the same instances
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }
}
