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
public class CalinskiHarabasz_ESTest_test1 extends CalinskiHarabasz_ESTest_scaffolding {

    /**
     * Verifies that isBetterScore returns true when the candidate score (1.0) is
     * higher than the current best score (0.0), which is the expected behaviour for
     * the Calinski-Harabasz index where a higher value indicates a better clustering.
     */
    @Test(timeout = 4000)
    public void test_isBetterScore_returnsTrueWhenCandidateScoreIsHigherThanCurrentBest() throws Throwable {
        CalinskiHarabasz evaluator = new CalinskiHarabasz();
        double candidateScore = 1.0;
        double currentBestScore = 0.0;

        boolean result = evaluator.isBetterScore(candidateScore, currentBestScore);

        assertTrue("A higher Calinski-Harabasz score should be considered a better score", result);
    }
}
