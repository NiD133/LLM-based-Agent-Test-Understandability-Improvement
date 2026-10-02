package org.apache.commons.math4.legacy.linear;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Test;
import org.junit.Assert;

/**
 * Verifies that {@link TriDiagonalTransformer} reduces a known 5x5 symmetric
 * matrix to the expected tri-diagonal form, by comparing the produced Q and T
 * matrices against pre-computed reference values.
 */
public class TriDiagonalTransformerTest_testMatricesValues5 {

    /** Maximum allowed deviation when comparing matrices against their references. */
    private static final double COMPARISON_TOLERANCE = 1.0e-14;

    /** The 5x5 symmetric matrix to be transformed. */
    private final double[][] symmetricMatrix5x5 = {
        { 1, 2, 3, 1, 1 },
        { 2, 1, 1, 3, 1 },
        { 3, 1, 1, 1, 2 },
        { 1, 3, 1, 2, 1 },
        { 1, 1, 2, 1, 3 }
    };

    @Test
    public void testMatricesValues5() {
        // Reference orthogonal matrix Q expected from the transformation.
        final double[][] expectedQ = {
            { 1.0,  0.0,                   0.0,                  0.0,                  0.0 },
            { 0.0, -0.5163977794943222,    0.016748280772542083, 0.839800693771262,    0.16669620021405473 },
            { 0.0, -0.7745966692414833,   -0.4354553000860955,  -0.44989322880603355, -0.08930153582895772 },
            { 0.0, -0.2581988897471611,    0.6364346693566014,  -0.30263204032131164,  0.6608313651342882 },
            { 0.0, -0.2581988897471611,    0.6364346693566009,  -0.027289660803112598, -0.7263191580755246 }
        };

        // Reference main diagonal of the tri-diagonal matrix T.
        final double[] expectedMainDiagonal = {
            1, 4.4, 1.433099579242636, -0.89537362758743, 2.062274048344794
        };

        // Reference sub/super diagonal of the tri-diagonal matrix T.
        final double[] expectedSecondaryDiagonal = {
            -JdkMath.sqrt(15), -3.0832882879592476, 0.6082710842351517, 1.1786086405912128
        };

        checkMatricesValues(symmetricMatrix5x5, expectedQ, expectedMainDiagonal, expectedSecondaryDiagonal);
    }

    /**
     * Transforms {@code matrix} and asserts that the resulting Q and T matrices
     * match the supplied references. Also verifies that repeated getter calls
     * return the same cached instances.
     *
     * @param matrix             the symmetric matrix to transform
     * @param expectedQ          the expected orthogonal matrix Q
     * @param mainDiagonal       the expected main diagonal of T
     * @param secondaryDiagonal  the expected sub/super diagonal of T
     */
    private void checkMatricesValues(double[][] matrix, double[][] expectedQ,
                                     double[] mainDiagonal, double[] secondaryDiagonal) {
        TriDiagonalTransformer transformer =
            new TriDiagonalTransformer(MatrixUtils.createRealMatrix(matrix));

        // Q must match the reference orthogonal matrix.
        RealMatrix q = transformer.getQ();
        Assert.assertEquals(0,
            q.subtract(MatrixUtils.createRealMatrix(expectedQ)).getNorm(),
            COMPARISON_TOLERANCE);

        // Rebuild the expected tri-diagonal matrix T from its two diagonals.
        RealMatrix t = transformer.getT();
        double[][] expectedTData = buildTriDiagonalData(mainDiagonal, secondaryDiagonal);
        Assert.assertEquals(0,
            t.subtract(MatrixUtils.createRealMatrix(expectedTData)).getNorm(),
            COMPARISON_TOLERANCE);

        // Subsequent getter calls must return the same cached instances.
        Assert.assertSame(q, transformer.getQ());
        Assert.assertSame(t, transformer.getT());
    }

    /**
     * Builds the dense representation of a tri-diagonal matrix from its main
     * diagonal and its (shared) sub/super diagonal.
     */
    private double[][] buildTriDiagonalData(double[] mainDiagonal, double[] secondaryDiagonal) {
        int size = mainDiagonal.length;
        double[][] data = new double[size][size];
        for (int i = 0; i < size; ++i) {
            data[i][i] = mainDiagonal[i];
            if (i > 0) {
                data[i][i - 1] = secondaryDiagonal[i - 1];
            }
            if (i < secondaryDiagonal.length) {
                data[i][i + 1] = secondaryDiagonal[i];
            }
        }
        return data;
    }
}
