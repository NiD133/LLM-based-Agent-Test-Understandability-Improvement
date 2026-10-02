package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

public class GaussianTest_testPreconditions {

    @Test(expected = NotStrictlyPositiveException.class)
    public void rejectsNegativeSigma() {
        final double norm = 1;
        final double mean = 2;
        final double negativeSigma = -1;

        new Gaussian(norm, mean, negativeSigma);
    }
}
