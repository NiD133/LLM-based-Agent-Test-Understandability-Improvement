package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

public class GaussianTest_testPreconditions {

    /**
     * Gaussian requires a strictly positive standard deviation (sigma > 0).
     * Passing a negative sigma must throw NotStrictlyPositiveException.
     */
    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorRejectsNonPositiveSigma() {
        new Gaussian(1, 2, -1);
    }
}
