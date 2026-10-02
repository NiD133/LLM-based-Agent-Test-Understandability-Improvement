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

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        CalinskiHarabasz calinskiHarabasz = new CalinskiHarabasz();
        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        Cluster<DoublePoint> cluster = new Cluster<DoublePoint>();

        int[] pointCoordinates = new int[5];
        DoublePoint firstPoint = new DoublePoint(pointCoordinates);
        cluster.addPoint(firstPoint);

        pointCoordinates[4] = 71;
        DoublePoint secondPoint = new DoublePoint(pointCoordinates);
        cluster.addPoint(secondPoint);

        clusters.add(cluster);
        double score = calinskiHarabasz.score(clusters);

        assertEquals(Double.NaN, score, 0.01);
    }
}
