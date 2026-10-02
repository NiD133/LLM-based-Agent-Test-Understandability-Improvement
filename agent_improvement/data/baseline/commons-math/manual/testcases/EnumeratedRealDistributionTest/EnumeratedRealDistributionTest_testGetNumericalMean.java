package org.apache.commons.math4.legacy.distribution;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.statistics.distribution.ContinuousDistribution;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.MathArithmeticException;
import org.apache.commons.math4.legacy.exception.NotANumberException;
import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.apache.commons.math4.legacy.exception.NotPositiveException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.core.Pair;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.Assert;
import org.junit.Test;

public class EnumeratedRealDistributionTest_testGetNumericalMean {

    /**
     * The distribution object used for testing.
     */
    private final EnumeratedRealDistribution testDistribution;

    /**
     * Tests if the distribution returns proper mean value.
     */
    @Test
    public void testGetNumericalMean() {
        Assert.assertEquals(3.4, testDistribution.getMean(), 1e-10);
    }
}
