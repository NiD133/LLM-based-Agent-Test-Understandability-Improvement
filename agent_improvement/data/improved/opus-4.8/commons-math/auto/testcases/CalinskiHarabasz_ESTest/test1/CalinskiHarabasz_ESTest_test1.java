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
     * For the Calinski-Harabasz index a higher value indicates a better clustering,
     * so {@code isBetterScore} must report that a larger score beats a smaller one.
     */
    @Test(timeout = 4000)
    public void isBetterScore_returnsTrue_whenScoreIsHigherThanReference() throws Throwable {
        CalinskiHarabasz calinskiHarabasz = new CalinskiHarabasz();

        double higherScore = 1.0;
        double lowerReferenceScore = 0.0;
        boolean higherIsBetter = calinskiHarabasz.isBetterScore(higherScore, lowerReferenceScore);

        assertTrue(higherIsBetter);
    }
}
