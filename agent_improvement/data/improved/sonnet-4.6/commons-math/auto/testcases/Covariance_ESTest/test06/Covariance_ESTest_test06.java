package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test06 extends Covariance_ESTest_scaffolding {

    /**
     * Verifies that computing the covariance matrix from an empty (zero-column) matrix
     * throws a RuntimeException. An Array2DRowRealMatrix created with the no-arg constructor
     * has zero rows and zero columns; AbstractRealMatrix rejects column dimensions that are
     * not positive (the error message reads "0 is smaller than, or equal to, the minimum (0)").
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Build a Covariance instance from a 13-row, 1-column data set (bias-corrected = false).
        double[][] dataWith13RowsAnd1Column = new double[13][1];
        Covariance covariance = new Covariance(dataWith13RowsAnd1Column, false);

        // An empty matrix (no rows, no columns) is not a valid input for covariance computation.
        Array2DRowRealMatrix emptyMatrix = new Array2DRowRealMatrix();

        try {
            covariance.computeCovarianceMatrix(emptyMatrix);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // AbstractRealMatrix enforces that column count must exceed 0.
            verifyException("org.apache.commons.math4.legacy.linear.AbstractRealMatrix", e);
        }
    }
}
