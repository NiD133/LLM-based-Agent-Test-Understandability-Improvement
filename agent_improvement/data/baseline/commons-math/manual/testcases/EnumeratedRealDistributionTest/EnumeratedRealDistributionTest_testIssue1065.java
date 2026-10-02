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

public class EnumeratedRealDistributionTest_testIssue1065 {

    /**
     * The distribution object used for testing.
     */
    private final EnumeratedRealDistribution testDistribution;

    @Test
    public void testIssue1065() {
        // Test Distribution for inverseCumulativeProbability
        //
        //         ^
        //         |
        // 1.000   +--------------------------------o===============
        //         |                               3|
        //         |                                |
        //         |                             1o=
        // 0.750   +-------------------------> o==  .
        //         |                          3|  . .
        //         |                   0       |  . .
        // 0.5625  +---------------> o==o======   . .
        //         |                 |  .      .  . .
        //         |                 |  .      .  . .
        //         |                5|  .      .  . .
        //         |                 |  .      .  . .
        //         |             o===   .      .  . .
        //         |             |   .  .      .  . .
        //         |            4|   .  .      .  . .
        //         |             |   .  .      .  . .
        // 0.000   +=============----+--+------+--+-+--------------->
        //                      14  18 21     28 31 33
        //
        // sum  = 4+5+0+3+1+3 = 16
        EnumeratedRealDistribution distribution = new EnumeratedRealDistribution(new double[] { 14.0, 18.0, 21.0, 28.0, 31.0, 33.0 }, new double[] { 4.0 / 16.0, 5.0 / 16.0, 0.0 / 16.0, 3.0 / 16.0, 1.0 / 16.0, 3.0 / 16.0 });
        assertEquals(14.0, distribution.inverseCumulativeProbability(0.0000), 0.0);
        assertEquals(14.0, distribution.inverseCumulativeProbability(0.2500), 0.0);
        assertEquals(33.0, distribution.inverseCumulativeProbability(1.0000), 0.0);
        assertEquals(18.0, distribution.inverseCumulativeProbability(0.5000), 0.0);
        assertEquals(18.0, distribution.inverseCumulativeProbability(0.5624), 0.0);
        assertEquals(28.0, distribution.inverseCumulativeProbability(0.5626), 0.0);
        assertEquals(31.0, distribution.inverseCumulativeProbability(0.7600), 0.0);
        assertEquals(18.0, distribution.inverseCumulativeProbability(0.5625), 0.0);
        assertEquals(28.0, distribution.inverseCumulativeProbability(0.7500), 0.0);
    }
}
