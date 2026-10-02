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

    /**
     * Scoring a single empty cluster does not provide enough data points for the
     * Calinski-Harabasz index, so {@code score} is expected to throw a
     * RuntimeException reporting "insufficient data".
     */
    @Test(timeout = 4000)
    public void scoreWithSingleEmptyClusterThrowsInsufficientData() throws Throwable {
        CalinskiHarabasz calinskiHarabasz = new CalinskiHarabasz();

        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        Cluster<DoublePoint> emptyCluster = new Cluster<DoublePoint>();
        clusters.push(emptyCluster);

        try {
            calinskiHarabasz.score(clusters);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // CalinskiHarabasz.score rejects input with too few data points
            // ("insufficient data").
            verifyException("org.apache.commons.math4.legacy.ml.clustering.evaluation.CalinskiHarabasz", e);
        }
    }
}
