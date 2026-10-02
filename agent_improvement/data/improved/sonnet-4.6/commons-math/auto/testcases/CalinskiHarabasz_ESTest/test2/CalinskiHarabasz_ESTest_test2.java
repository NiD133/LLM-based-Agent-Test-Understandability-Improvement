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
public class CalinskiHarabasz_ESTest_test2 extends CalinskiHarabasz_ESTest_scaffolding {

    /**
     * CalinskiHarabasz uses higher-is-better scoring, so a candidate score that is
     * lower than the current best score should NOT be considered better.
     */
    @Test(timeout = 4000)
    public void test2_lowerCandidateScoreIsNotBetterThanHigherCurrentBestScore() throws Throwable {
        CalinskiHarabasz evaluator = new CalinskiHarabasz();

        double candidateScore = 1023.5644983;
        double currentBestScore = 2869.5372;

        // A lower candidate score should not be better than a higher current best score
        boolean candidateIsBetter = evaluator.isBetterScore(candidateScore, currentBestScore);

        assertFalse(candidateIsBetter);
    }
}
