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

    private static final double BETTER_SCORE = 1.0;
    private static final double WORSE_SCORE = 0.0;

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        CalinskiHarabasz evaluator = new CalinskiHarabasz();

        boolean higherScoreIsBetter = evaluator.isBetterScore(BETTER_SCORE, WORSE_SCORE);

        assertTrue(higherScoreIsBetter);
    }
}
