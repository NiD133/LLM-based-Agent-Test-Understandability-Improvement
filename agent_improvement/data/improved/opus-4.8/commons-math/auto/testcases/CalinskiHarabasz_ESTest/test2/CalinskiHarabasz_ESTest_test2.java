package org.apache.commons.math4.legacy.ml.clustering.evaluation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CalinskiHarabasz_ESTest_test2 extends CalinskiHarabasz_ESTest_scaffolding {

    /**
     * For the Calinski-Harabasz index a higher score indicates a better clustering.
     * Here the candidate score (1023.56) is lower than the reference score (2869.54),
     * so isBetterScore should report that it is NOT an improvement.
     */
    @Test(timeout = 4000)
    public void isBetterScoreReturnsFalseWhenCandidateScoreIsLower() throws Throwable {
        CalinskiHarabasz calinskiHarabasz = new CalinskiHarabasz();
        double lowerCandidateScore = 1023.5644983;
        double higherReferenceScore = 2869.5372;

        boolean isBetter = calinskiHarabasz.isBetterScore(lowerCandidateScore, higherReferenceScore);

        assertFalse(isBetter);
    }
}
