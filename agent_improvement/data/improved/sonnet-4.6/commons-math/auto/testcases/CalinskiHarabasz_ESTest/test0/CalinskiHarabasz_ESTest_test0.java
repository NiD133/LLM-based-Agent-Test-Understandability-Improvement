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
     * Verifies that scoring a single empty cluster throws a RuntimeException
     * with the message "insufficient data". The Calinski-Harabasz index requires
     * at least two clusters with points to compute a meaningful score.
     */
    @Test(timeout = 4000)
    public void test0_scoringSingleEmptyClusterThrowsInsufficientDataException() throws Throwable {
        CalinskiHarabasz evaluator = new CalinskiHarabasz();

        LinkedList<Cluster<DoublePoint>> clusters = new LinkedList<Cluster<DoublePoint>>();
        Cluster<DoublePoint> emptyCluster = new Cluster<DoublePoint>();
        clusters.push(emptyCluster);

        // Scoring with a single empty cluster should fail: the index is undefined
        // without at least two non-empty clusters.
        try {
            evaluator.score(clusters);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // insufficient data
            //
            verifyException("org.apache.commons.math4.legacy.ml.clustering.evaluation.CalinskiHarabasz", e);
        }
    }
}
