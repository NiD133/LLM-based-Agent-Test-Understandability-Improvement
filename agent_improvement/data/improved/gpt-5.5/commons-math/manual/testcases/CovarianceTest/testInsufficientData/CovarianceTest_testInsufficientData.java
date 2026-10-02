package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Assert;
import org.junit.Test;

public class CovarianceTest_testInsufficientData {

    private static final double[] SINGLE_VALUE_SAMPLE = new double[] { 1 };
    private static final double[] OTHER_SINGLE_VALUE_SAMPLE = new double[] { 2 };

    /**
     * A covariance calculation requires at least two paired observations, and
     * a covariance matrix requires at least one column of data.
     */
    @Test
    public void testInsufficientData() {
        assertCovarianceRejectsSingleValueSamples();
        assertCovarianceMatrixRejectsRowsWithNoColumns();
    }

    private void assertCovarianceRejectsSingleValueSamples() {
        try {
            new Covariance().covariance(SINGLE_VALUE_SAMPLE, OTHER_SINGLE_VALUE_SAMPLE, false);
            Assert.fail("Expecting MathIllegalArgumentException");
        } catch (MathIllegalArgumentException ex) {
            // Expected
        }
    }

    private void assertCovarianceMatrixRejectsRowsWithNoColumns() {
        try {
            new Covariance(new double[][] { {}, {} });
            Assert.fail("Expecting NotStrictlyPositiveException");
        } catch (NotStrictlyPositiveException ex) {
            // Expected
        }
    }
}
