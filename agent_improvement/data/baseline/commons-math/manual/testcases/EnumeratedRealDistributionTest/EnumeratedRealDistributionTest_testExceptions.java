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

public class EnumeratedRealDistributionTest_testExceptions {

    /**
     * The distribution object used for testing.
     */
    private final EnumeratedRealDistribution testDistribution;

    /**
     * Tests if the {@link EnumeratedRealDistribution} constructor throws
     * exceptions for invalid data.
     */
    @Test
    public void testExceptions() {
        EnumeratedRealDistribution invalid = null;
        try {
            invalid = new EnumeratedRealDistribution(new double[] { 1.0, 2.0 }, new double[] { 0.0 });
            Assert.fail("Expected DimensionMismatchException");
        } catch (DimensionMismatchException e) {
        }
        try {
            invalid = new EnumeratedRealDistribution(new double[] { 1.0, 2.0 }, new double[] { 0.0, -1.0 });
            Assert.fail("Expected NotPositiveException");
        } catch (NotPositiveException e) {
        }
        try {
            invalid = new EnumeratedRealDistribution(new double[] { 1.0, 2.0 }, new double[] { 0.0, 0.0 });
            Assert.fail("Expected MathArithmeticException");
        } catch (MathArithmeticException e) {
        }
        try {
            invalid = new EnumeratedRealDistribution(new double[] { 1.0, 2.0 }, new double[] { 0.0, Double.NaN });
            Assert.fail("Expected NotANumberException");
        } catch (NotANumberException e) {
        }
        try {
            invalid = new EnumeratedRealDistribution(new double[] { 1.0, 2.0 }, new double[] { 0.0, Double.POSITIVE_INFINITY });
            Assert.fail("Expected NotFiniteNumberException");
        } catch (NotFiniteNumberException e) {
        }
        Assert.assertNull("Expected non-initialized DiscreteRealDistribution", invalid);
    }
}
