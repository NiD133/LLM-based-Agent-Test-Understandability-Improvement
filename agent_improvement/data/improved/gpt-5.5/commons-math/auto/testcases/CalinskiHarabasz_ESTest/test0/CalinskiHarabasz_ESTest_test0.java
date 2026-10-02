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
public class CalinskiHarabasz_ESTest_test0 extends CalinskiHarabasz_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        CalinskiHarabasz scorer = new CalinskiHarabasz();
        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        Cluster<DoublePoint> emptyCluster = new Cluster<DoublePoint>();
        clusters.push(emptyCluster);

        try {
            scorer.score(clusters);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException exception) {
            // Scoring a single empty cluster has insufficient data.
            verifyException("org.apache.commons.math4.legacy.ml.clustering.evaluation.CalinskiHarabasz", exception);
        }
    }
}
