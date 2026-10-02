package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math4.legacy.ml.clustering.Cluster;
import org.apache.commons.math4.legacy.ml.clustering.DoublePoint;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CalinskiHarabasz_ESTest_test4 extends CalinskiHarabasz_ESTest_scaffolding {

    /**
     * Verifies the score returned when the clustering consists of a single
     * cluster that contains exactly one point. With only one cluster and one
     * point the Calinski-Harabasz computation degenerates to 1.0.
     */
    @Test(timeout = 4000)
    public void scoreOfSingleClusterWithSinglePointIsOne() throws Throwable {
        CalinskiHarabasz calinskiHarabasz = new CalinskiHarabasz();

        // Build one cluster holding a single point at the origin (all zeros).
        Cluster<DoublePoint> singlePointCluster = new Cluster<DoublePoint>();
        int[] originCoordinates = new int[5];
        DoublePoint originPoint = new DoublePoint(originCoordinates);
        singlePointCluster.addPoint(originPoint);

        List<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        clusters.add(singlePointCluster);

        double score = calinskiHarabasz.score(clusters);

        assertEquals(1.0, score, 0.01);
    }
}
