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
package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;


public class CovarianceTest {

    // Tolerance values for floating-point comparisons
    private static final double LONGLEY_TOLERANCE      = 10E-9;
    private static final double SWISS_TOLERANCE        = 10E-13;
    private static final double CONSISTENCY_TOLERANCE  = 10E-14;

    // Longley dataset: 16 observations × 7 variables
    // Columns: Employment, GNP_Deflator, GNP, Armed_Forces, Population, Year_Index, Year
    // Source: J. Longley (1967), JASA vol. 62, pp. 819-841 / NIST Longley.dat
    protected final double[] longleyData = new double[] {
            60323, 83.0, 234289, 2356, 1590, 107608, 1947,
            61122, 88.5, 259426, 2325, 1456, 108632, 1948,
            60171, 88.2, 258054, 3682, 1616, 109773, 1949,
            61187, 89.5, 284599, 3351, 1650, 110929, 1950,
            63221, 96.2, 328975, 2099, 3099, 112075, 1951,
            63639, 98.1, 346999, 1932, 3594, 113270, 1952,
            64989, 99.0, 365385, 1870, 3547, 115094, 1953,
            63761, 100.0, 363112, 3578, 3350, 116219, 1954,
            66019, 101.2, 397469, 2904, 3048, 117388, 1955,
            67857, 104.6, 419180, 2822, 2857, 118734, 1956,
            68169, 108.4, 442769, 2936, 2798, 120445, 1957,
            66513, 110.8, 444546, 4681, 2637, 121950, 1958,
            68655, 112.6, 482704, 3813, 2552, 123366, 1959,
            69564, 114.2, 502601, 3931, 2514, 125368, 1960,
            69331, 115.7, 518173, 4806, 2572, 127852, 1961,
            70551, 116.9, 554894, 4007, 2827, 130081, 1962
        };

    // Swiss fertility dataset: 47 observations × 5 variables
    // Columns: Fertility, Agriculture, Examination, Education, Catholic
    // Source: R datasets package
    protected final double[] swissData = new double[] {
            80.2, 17.0, 15, 12,  9.96,
            83.1, 45.1,  6,  9, 84.84,
            92.5, 39.7,  5,  5, 93.40,
            85.8, 36.5, 12,  7, 33.77,
            76.9, 43.5, 17, 15,  5.16,
            76.1, 35.3,  9,  7, 90.57,
            83.8, 70.2, 16,  7, 92.85,
            92.4, 67.8, 14,  8, 97.16,
            82.4, 53.3, 12,  7, 97.67,
            82.9, 45.2, 16, 13, 91.38,
            87.1, 64.5, 14,  6, 98.61,
            64.1, 62.0, 21, 12,  8.52,
            66.9, 67.5, 14,  7,  2.27,
            68.9, 60.7, 19, 12,  4.43,
            61.7, 69.3, 22,  5,  2.82,
            68.3, 72.6, 18,  2, 24.20,
            71.7, 34.0, 17,  8,  3.30,
            55.7, 19.4, 26, 28, 12.11,
            54.3, 15.2, 31, 20,  2.15,
            65.1, 73.0, 19,  9,  2.84,
            65.5, 59.8, 22, 10,  5.23,
            65.0, 55.1, 14,  3,  4.52,
            56.6, 50.9, 22, 12, 15.14,
            57.4, 54.1, 20,  6,  4.20,
            72.5, 71.2, 12,  1,  2.40,
            74.2, 58.1, 14,  8,  5.23,
            72.0, 63.5,  6,  3,  2.56,
            60.5, 60.8, 16, 10,  7.72,
            58.3, 26.8, 25, 19, 18.46,
            65.4, 49.5, 15,  8,  6.10,
            75.5, 85.9,  3,  2, 99.71,
            69.3, 84.9,  7,  6, 99.68,
            77.3, 89.7,  5,  2, 100.00,
            70.5, 78.2, 12,  6, 98.96,
            79.4, 64.9,  7,  3, 98.22,
            65.0, 75.9,  9,  9, 99.06,
            92.2, 84.6,  3,  3, 99.46,
            79.3, 63.1, 13, 13, 96.83,
            70.4, 38.4, 26, 12,  5.62,
            65.7,  7.7, 29, 11, 13.79,
            72.7, 16.7, 22, 13, 11.22,
            64.4, 17.6, 35, 32, 16.92,
            77.6, 37.6, 15,  7,  4.97,
            67.6, 18.7, 25,  7,  8.65,
            35.0,  1.2, 37, 53, 42.34,
            44.7, 46.6, 16, 29, 50.43,
            42.8, 27.7, 22, 29, 58.33
        };


    /**
     * Verifies the 7×7 covariance matrix for the Longley dataset against values
     * computed in R using {@code cov(longley)}.
     *
     * <p>Data source: J. Longley (1967) "An Appraisal of Least Squares Programs
     * for the Electronic Computer from the Point of View of the User",
     * JASA vol. 62, pp. 819-841 / NIST Longley.dat.
     */
    @Test
    public void testLongley() {
        RealMatrix longleyMatrix = createRealMatrix(longleyData, 16, 7);
        RealMatrix actualCovariance = new Covariance(longleyMatrix).getCovarianceMatrix();

        // Expected 7×7 covariance matrix values from R: cov(longley)
        double[] expectedCovarianceValues = new double[] {
            12333921.73333333246,  3.679666000000000e+04,  343330206.333333313,
             1649102.666666666744, 1117681.066666666651,   23461965.733333334,   16240.93333333333248,
            36796.66000000000,     1.164576250000000e+02,    1063604.115416667,
             6258.666250000000,    3490.253750000000,         73503.000000000,       50.92333333333334,
            343330206.33333331347, 1.063604115416667e+06, 9879353659.329166412,
            56124369.854166664183, 30880428.345833335072,   685240944.600000024,  470977.90000000002328,
             1649102.66666666674,  6.258666250000000e+03,   56124369.854166664,
              873223.429166666698, -115378.762499999997,     4462741.533333333,    2973.03333333333330,
             1117681.06666666665,  3.490253750000000e+03,   30880428.345833335,
             -115378.762499999997,  484304.095833333326,     1764098.133333333,    1382.43333333333339,
            23461965.73333333433,  7.350300000000000e+04,  685240944.600000024,
             4462741.533333333209, 1764098.133333333302,   48387348.933333330,    32917.40000000000146,
            16240.93333333333,     5.092333333333334e+01,   470977.900000000,
             2973.033333333333,    1382.433333333333,        32917.40000000,           22.66666666666667
        };
        RealMatrix expectedCovariance = createRealMatrix(expectedCovarianceValues, 7, 7);

        TestUtils.assertEquals("covariance matrix", expectedCovariance, actualCovariance, LONGLEY_TOLERANCE);
    }

    /**
     * Verifies the 5×5 covariance matrix for the Swiss fertility dataset against
     * values computed in R using {@code cov(swiss)}.
     *
     * <p>Data source: R {@code datasets} package.
     */
    @Test
    public void testSwissFertility() {
        RealMatrix swissMatrix = createRealMatrix(swissData, 47, 5);
        RealMatrix actualCovariance = new Covariance(swissMatrix).getCovarianceMatrix();

        // Expected 5×5 covariance matrix values from R: cov(swiss)
        double[] expectedCovarianceValues = new double[] {
             156.0424976873265,   100.1691489361702,   -64.36692876965772,  -79.7295097132285,   241.5632030527289,
             100.169148936170251, 515.7994172062905,  -124.39283071230344, -139.6574005550416,   379.9043755781684,
             -64.3669287696577,  -124.3928307123034,   63.64662349676226,   53.5758556891767,  -190.5606105457909,
             -79.7295097132285,  -139.6574005550416,   53.57585568917669,   92.4560592044403,   -61.6988297872340,
             241.5632030527289,   379.9043755781684,  -190.56061054579092, -61.6988297872340,  1739.2945371877890
        };
        RealMatrix expectedCovariance = createRealMatrix(expectedCovarianceValues, 5, 5);

        TestUtils.assertEquals("covariance matrix", expectedCovariance, actualCovariance, SWISS_TOLERANCE);
    }

    /**
     * Verifies that the covariance between a constant series and any other series is zero,
     * because a constant has no variance.
     */
    @Test
    public void testConstant() {
        double[] constantSeries = new double[] {1, 1, 1, 1};
        double[] varyingSeries  = new double[] {1, 2, 3, 4};
        Assert.assertEquals(0d, new Covariance().covariance(constantSeries, varyingSeries, true), Double.MIN_VALUE);
        Assert.assertEquals(0d, new Covariance().covariance(constantSeries, constantSeries, true), Double.MIN_VALUE);
    }

    /**
     * Verifies that a dataset with only one column produces a 1×1 covariance matrix
     * whose sole entry equals the (population) variance of that column.
     *
     * <p>With {@code biasCorrected=false}, two observations [1, 2] have variance
     * ((1-1.5)²+(2-1.5)²)/2 = 0.25.
     */
    @Test
    public void testOneColumn() {
        // Two observations, one variable, no bias correction
        RealMatrix covarianceMatrix = new Covariance(new double[][] {{1}, {2}}, false).getCovarianceMatrix();

        Assert.assertEquals(1, covarianceMatrix.getRowDimension());
        Assert.assertEquals(1, covarianceMatrix.getColumnDimension());
        Assert.assertEquals(0.25, covarianceMatrix.getEntry(0, 0), 1.0e-15);
    }

    /**
     * Verifies that passing arrays too small for the requested computation throws the
     * appropriate exceptions:
     * <ul>
     *   <li>A single-element array pair requires at least 2 observations for bias-uncorrected
     *       covariance → {@link MathIllegalArgumentException}.</li>
     *   <li>An empty column array → {@link NotStrictlyPositiveException}.</li>
     * </ul>
     */
    @Test
    public void testInsufficientData() {
        double[] singleObservationX = new double[] {1};
        double[] singleObservationY = new double[] {2};
        try {
            new Covariance().covariance(singleObservationX, singleObservationY, false);
            Assert.fail("Expecting MathIllegalArgumentException");
        } catch (MathIllegalArgumentException ex) {
            // Expected: need at least 2 observations
        }
        try {
            new Covariance(new double[][] {{}, {}});
            Assert.fail("Expecting NotStrictlyPositiveException");
        } catch (NotStrictlyPositiveException ex) {
            // Expected: column length must be > 0
        }
    }

    /**
     * Verifies internal consistency of {@link Covariance}:
     * <ol>
     *   <li>Diagonal entries of the covariance matrix equal the column variances.</li>
     *   <li>The matrix is symmetric (C[i,j] == C[j,i]).</li>
     *   <li>Off-diagonal entries match pairwise {@code covariance(col_i, col_j)}.</li>
     *   <li>When all columns are identical, every matrix entry equals the column variance.</li>
     *   <li>Bias-correction defaults: {@code computeCovarianceMatrix(data)} and
     *       {@code computeCovarianceMatrix(data, true)} produce the same result.</li>
     *   <li>One-arg {@code covariance(x, y)} equals {@code covariance(x, y, true)}.</li>
     * </ol>
     */
    @Test
    public void testConsistency() {
        final RealMatrix swissMatrix = createRealMatrix(swissData, 47, 5);
        final RealMatrix covarianceMatrix = new Covariance(swissMatrix).getCovarianceMatrix();

        // 1. Diagonal entries must equal column variances
        for (int col = 0; col < 5; col++) {
            double columnVariance = org.apache.commons.statistics.descriptive.Variance.of(swissMatrix.getColumn(col)).getAsDouble();
            Assert.assertEquals(columnVariance, covarianceMatrix.getEntry(col, col), CONSISTENCY_TOLERANCE);
        }

        // 2 & 3. Symmetry and agreement with pairwise covariance computation
        Assert.assertEquals(covarianceMatrix.getEntry(2, 3),
                new Covariance().covariance(swissMatrix.getColumn(2), swissMatrix.getColumn(3), true), CONSISTENCY_TOLERANCE);
        Assert.assertEquals(covarianceMatrix.getEntry(2, 3), covarianceMatrix.getEntry(3, 2), Double.MIN_VALUE);

        // 4. All-identical columns: every entry in the covariance matrix equals the shared column variance
        RealMatrix repeatedColumns = new Array2DRowRealMatrix(47, 3);
        for (int col = 0; col < 3; col++) {
            repeatedColumns.setColumnMatrix(col, swissMatrix.getColumnMatrix(0));
        }
        RealMatrix repeatedCovarianceMatrix = new Covariance(repeatedColumns).getCovarianceMatrix();
        double sharedColumnVariance = org.apache.commons.statistics.descriptive.Variance.of(swissMatrix.getColumn(0)).getAsDouble();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Assert.assertEquals(sharedColumnVariance, repeatedCovarianceMatrix.getEntry(row, col), CONSISTENCY_TOLERANCE);
            }
        }

        // 5. Bias-correction default: computeCovarianceMatrix(data) == computeCovarianceMatrix(data, true)
        double[][] rawData = swissMatrix.getData();
        TestUtils.assertEquals("Covariances",
                covarianceMatrix, new Covariance().computeCovarianceMatrix(rawData), Double.MIN_VALUE);
        TestUtils.assertEquals("Covariances",
                covarianceMatrix, new Covariance().computeCovarianceMatrix(rawData, true), Double.MIN_VALUE);

        // 6. One-arg covariance(x, y) defaults to bias-corrected (biasCorrected=true)
        double[] firstObservation  = rawData[0];
        double[] secondObservation = rawData[1];
        Assert.assertEquals(new Covariance().covariance(firstObservation, secondObservation),
                new Covariance().covariance(firstObservation, secondObservation, true), Double.MIN_VALUE);
    }

    /**
     * Reshapes a flat row-major {@code data} array into an {@code nRows × nCols} matrix.
     */
    protected RealMatrix createRealMatrix(double[] data, int nRows, int nCols) {
        double[][] matrixData = new double[nRows][nCols];
        int ptr = 0;
        for (int i = 0; i < nRows; i++) {
            System.arraycopy(data, ptr, matrixData[i], 0, nCols);
            ptr += nCols;
        }
        return new Array2DRowRealMatrix(matrixData);
    }
}
