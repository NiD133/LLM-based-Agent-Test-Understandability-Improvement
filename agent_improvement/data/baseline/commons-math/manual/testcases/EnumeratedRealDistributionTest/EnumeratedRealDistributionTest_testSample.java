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

public class EnumeratedRealDistributionTest_testSample {

    /**
     * The distribution object used for testing.
     */
    private final EnumeratedRealDistribution testDistribution;

    /**
     * Tests sampling.
     */
    @Test
    public void testSample() {
        final int n = 1000000;
        final ContinuousDistribution.Sampler sampler = testDistribution.createSampler(RandomSource.XO_RO_SHI_RO_128_PP.create());
        final double[] samples = AbstractRealDistribution.sample(n, sampler);
        Assert.assertEquals(n, samples.length);
        double sum = 0;
        double sumOfSquares = 0;
        for (int i = 0; i < samples.length; i++) {
            sum += samples[i];
            sumOfSquares += samples[i] * samples[i];
        }
        final double mean = testDistribution.getMean();
        Assert.assertEquals("Mean", mean, sum / n, mean * 1e-2);
        final double var = testDistribution.getVariance();
        Assert.assertEquals("Variance", var, sumOfSquares / n - JdkMath.pow(sum / n, 2), var * 1e-2);
    }
}
