package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import org.apache.commons.math4.legacy.ml.clustering.Cluster;
import org.apache.commons.math4.legacy.ml.clustering.DoublePoint;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CalinskiHarabasz_ESTest_test3 extends CalinskiHarabasz_ESTest_scaffolding {

    /**
     * The Calinski-Harabasz score is defined as a ratio of between-cluster
     * dispersion to within-cluster dispersion. With only a single cluster the
     * between-cluster dispersion and the (k - 1) degrees-of-freedom term are
     * both zero, so the score evaluates to NaN.
     */
    @Test(timeout = 4000)
    public void scoreOfSingleClusterIsNaN() throws Throwable {
        CalinskiHarabasz calinskiHarabasz = new CalinskiHarabasz();

        // Build one cluster holding two points in 5-dimensional space.
        // The shared backing array is mutated between the two DoublePoint
        // constructions, so the first point is the origin (all zeros) and the
        // second point differs only in its last coordinate.
        int[] coordinates = new int[5];

        Cluster<DoublePoint> cluster = new Cluster<DoublePoint>();
        DoublePoint originPoint = new DoublePoint(coordinates);
        cluster.addPoint(originPoint);

        coordinates[4] = 71;
        DoublePoint offsetPoint = new DoublePoint(coordinates);
        cluster.addPoint(offsetPoint);

        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        clusters.add(cluster);

        double score = calinskiHarabasz.score(clusters);

        assertEquals(Double.NaN, score, 0.01);
    }
}
