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

    // Calinski-Harabasz score is undefined (NaN) when there is only one cluster,
    // because the between-cluster variance cannot be computed without at least two clusters.
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        CalinskiHarabasz evaluator = new CalinskiHarabasz();

        // Build a single cluster containing two distinct 5-dimensional points.
        int[] coords = new int[5];
        DoublePoint originPoint = new DoublePoint(coords);   // [0, 0, 0, 0,  0]
        coords[4] = 71;
        DoublePoint offsetPoint = new DoublePoint(coords);   // [0, 0, 0, 0, 71]

        Cluster<DoublePoint> singleCluster = new Cluster<DoublePoint>();
        singleCluster.addPoint(originPoint);
        singleCluster.addPoint(offsetPoint);

        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        clusters.add(singleCluster);

        // With only one cluster the score is NaN.
        double score = evaluator.score(clusters);
        assertEquals(Double.NaN, score, 0.01);
    }
}
