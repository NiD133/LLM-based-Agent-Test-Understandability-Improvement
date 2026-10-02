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
import org.junit.Assert;
import org.junit.Test;

public class TriDiagonalTransformerTest {

    private static final double MATRIX_RECONSTRUCTION_TOLERANCE = 4.0e-15;
    private static final double ORTHOGONALITY_TOLERANCE = 1.0e-15;
    private static final double TRI_DIAGONAL_TOLERANCE = 1.0e-16;
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

    @Test
    public void testNonSquare() {
        try {
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(new double[3][2]));
            Assert.fail("an exception should have been thrown");
        } catch (NonSquareMatrixException ime) {
            // expected behavior
        }
    }

    @Test
    public void testAEqualQTQt() {
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare5));
        checkAEqualQTQt(MatrixUtils.createRealMatrix(testSquare3));
    }

    private void checkAEqualQTQt(RealMatrix matrix) {
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);
        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();

        double norm = q.multiply(t).multiply(qT).subtract(matrix).getNorm();
        Assert.assertEquals(0, norm, MATRIX_RECONSTRUCTION_TOLERANCE);
    }

    @Test
    public void testNoAccessBelowDiagonal() {
        checkNoAccessBelowDiagonal(testSquare5);
        checkNoAccessBelowDiagonal(testSquare3);
    }

    private void checkNoAccessBelowDiagonal(double[][] data) {
        double[][] dataWithNaNsBelowDiagonal = copyWithNaNsBelowDiagonal(data);
        RealMatrix matrix = MatrixUtils.createRealMatrix(dataWithNaNsBelowDiagonal);
        TriDiagonalTransformer transformer = new TriDiagonalTransformer(matrix);

        RealMatrix q = transformer.getQ();
        RealMatrix qT = transformer.getQT();
        RealMatrix t = transformer.getT();
        double norm = q.multiply(t).multiply(qT).subtract(MatrixUtils.createRealMatrix(data)).getNorm();
        Assert.assertEquals(0, norm, MATRIX_RECONSTRUCTION_TOLERANCE);
    }

    private double[][] copyWithNaNsBelowDiagonal(double[][] data) {
        double[][] copiedData = new double[data.length][];
        for (int i = 0; i < data.length; ++i) {
            copiedData[i] = data[i].clone();
            Arrays.fill(copiedData[i], 0, i, Double.NaN);
        }
        return copiedData;
    }

    @Test
    public void testQOrthogonal() {
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getQ());
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getQ());
    }

    @Test
    public void testQTOrthogonal() {
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getQT());
        checkOrthogonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getQT());
    }

    private void checkOrthogonal(RealMatrix matrix) {
        RealMatrix matrixTimesTranspose = matrix.transpose().multiply(matrix);
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(matrixTimesTranspose.getRowDimension());
        Assert.assertEquals(0, matrixTimesTranspose.subtract(identity).getNorm(), ORTHOGONALITY_TOLERANCE);
    }

    @Test
    public void testTTriDiagonal() {
        checkTriDiagonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare5)).getT());
        checkTriDiagonal(new TriDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare3)).getT());
    }

    private void checkTriDiagonal(RealMatrix matrix) {
        final int rows = matrix.getRowDimension();
        final int columns = matrix.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (i < j - 1 || i > j + 1) {
                    Assert.assertEquals(0, matrix.getEntry(i, j), TRI_DIAGONAL_TOLERANCE);
                }
            }
        }
    }

    @Test
    public void testMatricesValues5() {
        checkMatricesValues(testSquare5,
                            new double[][] {
                                { 1.0, 0.0, 0.0, 0.0, 0.0 },
                                { 0.0, -0.5163977794943222, 0.016748280772542083, 0.839800693771262, 0.16669620021405473 },
                                { 0.0, -0.7745966692414833, -0.4354553000860955, -0.44989322880603355, -0.08930153582895772 },
                                { 0.0, -0.2581988897471611, 0.6364346693566014, -0.30263204032131164, 0.6608313651342882 },
                                { 0.0, -0.2581988897471611, 0.6364346693566009, -0.027289660803112598, -0.7263191580755246 }
                            },
                            new double[] { 1, 4.4, 1.433099579242636, -0.89537362758743, 2.062274048344794 },
                            new double[] { -JdkMath.sqrt(15), -3.0832882879592476, 0.6082710842351517, 1.1786086405912128 });
    }

    @Test
    public void testMatricesValues3() {
        checkMatricesValues(testSquare3,
                            new double[][] {
                                { 1.0, 0.0, 0.0 },
                                { 0.0, -0.6, 0.8 },
                                { 0.0, -0.8, -0.6 },
                            },
                            new double[] { 1, 2.64, -0.64 },
                            new double[] { -5, -1.52 });
    }

    private void checkMatricesValues(double[][] matrixData, double[][] expectedQData,
                                     double[] expectedMainDiagonal,
                                     double[] expectedSecondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrixData));

        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0,
                            q.subtract(MatrixUtils.createRealMatrix(expectedQData)).getNorm(),
                            REFERENCE_VALUE_TOLERANCE);

        RealMatrix t = transformer.getT();
        Assert.assertEquals(0,
                            t.subtract(createTriDiagonalMatrix(expectedMainDiagonal, expectedSecondaryDiagonal)).getNorm(),
                            REFERENCE_VALUE_TOLERANCE);

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
