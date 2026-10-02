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
 * Tests for {@link TriDiagonalTransformer}, which decomposes a symmetric
 * matrix A into A = Q &times; T &times; Q<sup>T</sup>, where Q is orthogonal
 * and T is symmetric and tridiagonal.
 */
public class TriDiagonalTransformerTest {

    /** Tolerance for reconstructing the original matrix from Q, T and Q^T. */
    private static final double RECONSTRUCTION_TOLERANCE = 4.0e-15;

    /** Tolerance for checking that Q (or Q^T) is orthogonal. */
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;

    /** Tolerance for checking that off-tridiagonal entries of T are zero. */
    private static final double OFF_TRIDIAGONAL_TOLERANCE = 1.0e-16;

    /** Tolerance for comparing Q and T against known reference values. */
    private static final double REFERENCE_TOLERANCE = 1.0e-14;

    /** A 5 x 5 symmetric test matrix. */
    private final double[][] symmetric5x5 = {
            { 1, 2, 3, 1, 1 },
            { 2, 1, 1, 3, 1 },
            { 3, 1, 1, 1, 2 },
            { 1, 3, 1, 2, 1 },
            { 1, 1, 2, 1, 3 }
    };

    /** A 3 x 3 symmetric test matrix. */
    private final double[][] symmetric3x3 = {
            { 1, 3, 4 },
            { 3, 2, 2 },
            { 4, 2, 0 }
    };

    @Test
    public void testNonSquareMatrixIsRejected() {
        final RealMatrix nonSquare = MatrixUtils.createRealMatrix(new double[3][2]);
        try {
            new TriDiagonalTransformer(nonSquare);
            Assert.fail("expected a NonSquareMatrixException for a 3x2 matrix");
        } catch (NonSquareMatrixException expected) {
            // expected: the transformer only accepts square matrices
        }
    }

    @Test
    public void testOriginalMatrixEqualsQTimesTTimesQt() {
        assertReconstructsOriginal(MatrixUtils.createRealMatrix(symmetric5x5));
        assertReconstructsOriginal(MatrixUtils.createRealMatrix(symmetric3x3));
    }

    /** Asserts that Q &times; T &times; Q^T reproduces the original matrix. */
    private void assertReconstructsOriginal(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();

        RealMatrix reconstructed = q.multiply(t).multiply(qT);
        double error = reconstructed.subtract(matrix).getNorm();
        Assert.assertEquals(0, error, RECONSTRUCTION_TOLERANCE);
    }

    @Test
    public void testEntriesBelowDiagonalAreNotAccessed() {
        assertLowerTriangleIsIgnored(symmetric5x5);
        assertLowerTriangleIsIgnored(symmetric3x3);
    }

    /**
     * Verifies that the transformer never reads entries strictly below the
     * diagonal: poisoning them with NaN must not affect the decomposition,
     * which should still reconstruct the original (clean) matrix.
     */
    private void assertLowerTriangleIsIgnored(double[][] data) {
        double[][] poisonedData = new double[data.length][];
        for (int row = 0; row < data.length; ++row) {
            poisonedData[row] = data[row].clone();
            // Replace every entry below the diagonal in this row with NaN.
            Arrays.fill(poisonedData[row], 0, row, Double.NaN);
        }

        RealMatrix poisonedMatrix = MatrixUtils.createRealMatrix(poisonedData);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(poisonedMatrix);
        RealMatrix q  = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t  = transformer.getT();

        RealMatrix reconstructed = q.multiply(t).multiply(qT);
        RealMatrix original = MatrixUtils.createRealMatrix(data);
        double error = reconstructed.subtract(original).getNorm();
        Assert.assertEquals(0, error, RECONSTRUCTION_TOLERANCE);
    }

    @Test
    public void testQIsOrthogonal() {
        assertOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetric5x5)).getQ());
        assertOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetric3x3)).getQ());
    }

    @Test
    public void testQtIsOrthogonal() {
        assertOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetric5x5)).getQT());
        assertOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetric3x3)).getQT());
    }

    /** Asserts that the given matrix is orthogonal, i.e. m^T &times; m = I. */
    private void assertOrthogonal(RealMatrix m) {
        RealMatrix mTransposeTimesM = m.transpose().multiply(m);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(mTransposeTimesM.getRowDimension());
        double error = mTransposeTimesM.subtract(identity).getNorm();
        Assert.assertEquals(0, error, ORTHOGONALITY_TOLERANCE);
    }

    @Test
    public void testTIsTriDiagonal() {
        assertTriDiagonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetric5x5)).getT());
        assertTriDiagonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(symmetric3x3)).getT());
    }

    /**
     * Asserts that every entry outside the main diagonal and its two adjacent
     * diagonals is zero, as required for a tridiagonal matrix.
     */
    private void assertTriDiagonal(RealMatrix m) {
        final int rows = m.getRowDimension();
        final int cols = m.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                boolean offTriDiagonal = i < j - 1 || i > j + 1;
                if (offTriDiagonal) {
                    Assert.assertEquals(0, m.getEntry(i, j), OFF_TRIDIAGONAL_TOLERANCE);
                }
            }
        }
    }

    @Test
    public void testMatricesValues5x5() {
        double[][] expectedQ = {
                { 1.0,  0.0,                 0.0,                  0.0,                   0.0 },
                { 0.0, -0.5163977794943222,  0.016748280772542083, 0.839800693771262,     0.16669620021405473 },
                { 0.0, -0.7745966692414833, -0.4354553000860955,  -0.44989322880603355,  -0.08930153582895772 },
                { 0.0, -0.2581988897471611,  0.6364346693566014,  -0.30263204032131164,   0.6608313651342882 },
                { 0.0, -0.2581988897471611,  0.6364346693566009,  -0.027289660803112598, -0.7263191580755246 }
        };
        double[] expectedMainDiagonal =
                { 1, 4.4, 1.433099579242636, -0.89537362758743, 2.062274048344794 };
        double[] expectedSecondaryDiagonal =
                { -JdkMath.sqrt(15), -3.0832882879592476, 0.6082710842351517, 1.1786086405912128 };

        assertMatricesMatchReference(symmetric5x5, expectedQ,
                                     expectedMainDiagonal, expectedSecondaryDiagonal);
    }

    @Test
    public void testMatricesValues3x3() {
        double[][] expectedQ = {
                {  1.0,  0.0,  0.0 },
                {  0.0, -0.6,  0.8 },
                {  0.0, -0.8, -0.6 },
        };
        double[] expectedMainDiagonal = { 1, 2.64, -0.64 };
        double[] expectedSecondaryDiagonal = { -5, -1.52 };

        assertMatricesMatchReference(symmetric3x3, expectedQ,
                                     expectedMainDiagonal, expectedSecondaryDiagonal);
    }

    /**
     * Checks Q and T against precomputed reference values, and verifies that
     * repeated getter calls return the same cached instances.
     *
     * @param matrix             input matrix to transform
     * @param expectedQ          expected orthogonal matrix Q
     * @param mainDiagonal       expected main diagonal of T
     * @param secondaryDiagonal  expected sub-/super-diagonal of T
     */
    private void assertMatricesMatchReference(double[][] matrix, double[][] expectedQ,
                                              double[] mainDiagonal,
                                              double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        // Q must match the known reference matrix.
        RealMatrix q = transformer.getQ();
        double qError = q.subtract(MatrixUtils.createRealMatrix(expectedQ)).getNorm();
        Assert.assertEquals(0, qError, REFERENCE_TOLERANCE);

        // Rebuild the expected tridiagonal T from its two diagonals.
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
        double tError = t.subtract(MatrixUtils.createRealMatrix(expectedT)).getNorm();
        Assert.assertEquals(0, tError, REFERENCE_TOLERANCE);

        // The getters must return the same cached instance on subsequent calls.
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }
}
