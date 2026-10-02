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
public class CalinskiHarabasz_ESTest_test4 extends CalinskiHarabasz_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        CalinskiHarabasz scorer = new CalinskiHarabasz();
        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        Cluster<DoublePoint> singlePointCluster = new Cluster<DoublePoint>();
        int[] pointCoordinates = new int[5];
        DoublePoint point = new DoublePoint(pointCoordinates);

        singlePointCluster.addPoint(point);
        clusters.add(singlePointCluster);

        double score = scorer.score(clusters);

        assertEquals(1.0, score, 0.01);
    }
}
