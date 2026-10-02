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

public class EnumeratedRealDistributionTest_testIssue942 {

    /**
     * The distribution object used for testing.
     */
    private final EnumeratedRealDistribution testDistribution;

    @Test
    public void testIssue942() {
        List<Pair<Object, Double>> list = new ArrayList<>();
        list.add(new Pair<Object, Double>(new Object() {
        }, Double.valueOf(0)));
        list.add(new Pair<Object, Double>(new Object() {
        }, Double.valueOf(1)));
        final UniformRandomProvider rng = RandomSource.WELL_512_A.create();
        Assert.assertEquals(1, new EnumeratedDistribution<>(list).createSampler(rng).sample(1).length);
    }
}
